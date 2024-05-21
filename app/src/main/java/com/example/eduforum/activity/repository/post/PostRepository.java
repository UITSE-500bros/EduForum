package com.example.eduforum.activity.repository.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.net.Uri;
import android.util.Log;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.model.subscription_manage.Subscription;
import com.example.eduforum.activity.repository.post.dto.AddPostDTO;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PostRepository {
    private static PostRepository instance;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;

    public PostRepository() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static synchronized PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }


    public void addPost(Post post, IPostCallback callback) {
        AddPostDTO addPostDTO = new AddPostDTO(post);

        db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .add(addPostDTO)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        post.setPostID(documentReference.getId());
                        uploadPostImages(post, callback);
                        Log.d(FlagsList.DEBUG_POST_FLAG, "New post written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onAddPostFailure(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error adding post", e);
                    }
                });
    }

    private void uploadPostImages(Post post, IPostCallback callback) {
        StorageReference postRef = storage.getReference("Post/" + post.getPostID() + "/images");

        List<Uri> filesUri = post.getImage();
        if (filesUri == null || filesUri.isEmpty()) {
            callback.onAddPostSuccess(post);
            return;
        }
        int sequenceNumber = 0;

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        List<Task<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();
        List<String> imagePaths = new ArrayList<>();
        for (Uri fileUri : filesUri) {
            String uniqueFileName = String.format(Locale.US, "%04d-%d", sequenceNumber++, System.currentTimeMillis());

            StorageReference fileRef = postRef.child(uniqueFileName);
            imagePaths.add(fileRef.getPath()+"/"+uniqueFileName);

            UploadTask uploadTask = fileRef.putFile(fileUri, metadata);
            uploadTasks.add(uploadTask);
        }

        Tasks.whenAllSuccess(uploadTasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                post.setDownloadImage(imagePaths);
                updateDownloadImage(post, callback);
                Log.d(FlagsList.DEBUG_POST_FLAG, "All images uploaded successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(FlagsList.DEBUG_POST_FLAG, "Error uploading images", e);
                callback.onAddPostFailure(e.toString());
            }
        });
    }

    private void updateDownloadImage(Post post, IPostCallback callback) {
        db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID())
                .update("downloadImage", post.getDownloadImage())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FlagsList.DEBUG_POST_FLAG, "downloadImage path updated successfully!");
                        callback.onAddPostSuccess(post);
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error updating downloadImage path", e);
                    }
                });
    }


    public void editPost(Post post, IPostCallback callback) {
        db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID())
                .set(post, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onEditPostSuccess();
                        Log.d(FlagsList.DEBUG_POST_FLAG, "Post successfully edited!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onEditPostFailure(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error adding document", e);
                    }
                });
    }

    private void resetNewPost(String communityID, String userID) {
        db.collection("NewPost")
                .whereEqualTo("userID", userID)
                .whereEqualTo("communityID", communityID)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch batch = db.batch();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            batch.update(document.getReference(), "totalNewPost", 0);
                        }
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(FlagsList.DEBUG_POST_FLAG, "New post count reset successfully!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(FlagsList.DEBUG_POST_FLAG, "Error resetting new post count", e);
                            }
                        });
                    }
                });
    }

    public void getPosts(String communityID, String userID, IPostCallback callback) {
        // set total new post to 0
        resetNewPost(communityID, userID);

        db.collection("Community")
                .document(communityID)
                .collection("Post")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Post> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Post post = documentSnapshot.toObject(Post.class);
                            post.setPostID(documentSnapshot.getId());
                            posts.add(post);
                            Log.d(FlagsList.DEBUG_POST_FLAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                        }

                        callback.onGetPostSuccess(posts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onGetPostFailure(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error fetching post,", e);
                    }
                });
    }

    //TODO: check if the user is the owner of the post, render a button can delete that post @Duong Thuan Tri
    // TODO: delete all comment subcollection when user delete a post
    public void deletePost(Post post, IPostCallback callback) {
        db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onDeletePostSuccess();
                        Log.d(FlagsList.DEBUG_POST_FLAG, "Post successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onDeletePostError(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error deleting document", e);
                    }
                });
    }

    /**
     * Search post by keyword
     *
     * @param communityID community ID
     * @param keyword    search keyword
     * @param callback override onQueryPostSuccess to get the list of posts
     */
    public void searchPost(String communityID, String keyword, IPostCallback callback) {

        db.collection("Community")
                .document(communityID)
                .collection("Post")
                .where(Filter.or(
                        Filter.and(
                                Filter.greaterThanOrEqualTo("title", keyword),
                                Filter.lessThanOrEqualTo("title", keyword + "\uf8ff")
                        ),
                        Filter.and(
                                Filter.greaterThanOrEqualTo("content", keyword),
                                Filter.lessThanOrEqualTo("content", keyword + "\uf8ff")
                        )
                ))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Post> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Post post = documentSnapshot.toObject(Post.class);
                            post.setPostID(documentSnapshot.getId());
                            posts.add(post);
                            Log.d(FlagsList.DEBUG_POST_FLAG, "Searched post: " + documentSnapshot.getId() + " => " + documentSnapshot.getData());
                        }

                        callback.onQueryPostSuccess(posts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onQueryPostError(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error fetching post,", e);
                    }
                });
    }

    public void queryPost(String communityID, String userID, @Nullable List<Category> categories, @Nullable PostQuery condition, IPostCallback callback) {
        // set total new post to 0
        resetNewPost(communityID, userID);
        if (categories == null && condition == null) {
            callback.onQueryPostError("NO_CONDITION");
            return;
        }

        List<Post> queryPostResults = new ArrayList<>();

        CollectionReference postRef = db.collection("Community").document(communityID).collection("Post");
        Query postQuery = postRef;
        if (condition != null) {
            if (condition == PostQuery.MOST_COMMENTED) {
                postQuery = postRef.orderBy("totalComment", Query.Direction.DESCENDING);
            }
            if (condition == PostQuery.MOST_VOTED) {
                postQuery = postRef.orderBy("voteDifference", Query.Direction.DESCENDING);
            }
            if (condition == PostQuery.NEWEST) {
                postQuery = postRef.orderBy("timeCreated", Query.Direction.DESCENDING);
            }
            if (condition == PostQuery.OLDEST) {
                postQuery = postRef.orderBy("timeCreated", Query.Direction.ASCENDING);
            }
        }

        if (categories != null) {
            List<PostCategory> categoryIDs = new ArrayList<>();
            for (Category category : categories) {
                PostCategory newCategory = new PostCategory(category);
                categoryIDs.add(newCategory);
            }

            List<List<PostCategory>> batches = new ArrayList<>();
            int batchSize = 10;
            for (int i = 0; i < categoryIDs.size(); i += batchSize) {
                int end = Math.min(categoryIDs.size(), i + batchSize);
                batches.add(new ArrayList<>(categoryIDs.subList(i, end)));
            }

            List<Task<QuerySnapshot>> tasks = new ArrayList<>();
            for (List<PostCategory> batch : batches) {
                Task<QuerySnapshot> task = postQuery.whereArrayContainsAny("category", batch).get();
                tasks.add(task);
            }

            Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                for (Object result : results) {
                    QuerySnapshot snapshot = (QuerySnapshot) result;
                    for (QueryDocumentSnapshot document : snapshot) {
                        queryPostResults.add(document.toObject(Post.class));
                        Log.d(FlagsList.DEBUG_POST_FLAG, document.getId() + " => " + document.getData());
                    }
                }
                callback.onQueryPostSuccess(queryPostResults);
            }).addOnFailureListener(e -> {
                callback.onQueryPostError(e.toString());
                Log.w(FlagsList.DEBUG_POST_FLAG, "Error getting documents", e);
            });

        } else {
            postQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            queryPostResults.add(document.toObject(Post.class));
                            Log.d(FlagsList.DEBUG_POST_FLAG, document.getId() + " => " + document.getData());
                        }
                        callback.onQueryPostSuccess(queryPostResults);
                    } else {
                        callback.onQueryPostError(Objects.requireNonNull(task.getException()).getMessage());
                        Log.d(FlagsList.DEBUG_POST_FLAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }

    }

    public void bookmarkPost(Post post, String userID, String communityName, IPostCallback callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("userID", userID);
        Map<String, Object> communityObject = new HashMap<>();
        communityObject.put("communityID", post.getCommunityID());
        communityObject.put("name", communityName);
        data.put("community", communityObject);
        data.put("creator", post.getCreator());
        // put the whole post object just for testing purpose, this need to be optimized in the future
        data.put("post", post);

        db.collection("Bookmark")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onBookmarkSuccess();
                        Log.d(FlagsList.DEBUG_POST_FLAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onBookmarkError(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error adding document", e);
                    }
                });

    }

    /**
     * Subscribe to a post
     *
     * @param communityID community ID
     * @param postID      post ID
     * @param userID      user ID
     * @param callback    override onSubscriptionSuccess to get the list of posts
     */
    public void subscribePost(String communityID, String postID, String userID, IPostCallback callback) {
        db.collection("PostSubscription")
                .add(new Subscription(communityID, postID, userID))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSubscriptionSuccess();
                        Log.d(FlagsList.DEBUG_POST_FLAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSubscriptionError(e.toString());
                        Log.w(FlagsList.DEBUG_POST_FLAG, "Error adding document", e);
                    }
                });
    }

    //TODO: transaction for upVote, downVote
    public void updateVoteCount(Post post, String userID, int voteType) {
        DocumentReference postRef = db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID());
        DocumentReference voteRef = postRef.collection("Vote").document(userID);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot postSnapshot = transaction.get(postRef);
                DocumentSnapshot voteSnapshot = transaction.get(voteRef);

                if (!postSnapshot.exists()) {
                    throw new FirebaseFirestoreException("Post does not exist", FirebaseFirestoreException.Code.NOT_FOUND);
                }

                if (!voteSnapshot.exists()) {
                    if (voteType == 1) {
                        transaction.update(postRef, "totalUpVote", FieldValue.increment(1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(1));
                    } else if (voteType == -1) {
                        transaction.update(postRef, "totalDownVote", FieldValue.increment(1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(-1));
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("userID", userID);
                    data.put("voteType", voteType);
                    transaction.set(voteRef, data);
                    return null;
                }

                long oldVoteType = voteSnapshot.getLong("voteType");
                if (oldVoteType == 1) {
                    if (voteType == 1) {
                        transaction.update(postRef, "totalUpVote", FieldValue.increment(-1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(-1));
                        transaction.update(voteRef, "voteType", 0);
                    } else if (voteType == -1) {
                        transaction.update(postRef, "totalUpVote", FieldValue.increment(-1));
                        transaction.update(postRef, "totalDownVote", FieldValue.increment(1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(-2));
                        transaction.update(voteRef, "voteType", voteType);
                    }
                } else if (oldVoteType == -1){
                    if (voteType == 1) {
                        transaction.update(postRef, "totalUpVote", FieldValue.increment(1));
                        transaction.update(postRef, "totalDownVote", FieldValue.increment(-1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(2));
                        transaction.update(voteRef, "voteType", voteType);
                    } else if (voteType == -1) {
                        transaction.update(postRef, "totalDownVote", FieldValue.increment(-1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(1));
                        transaction.update(voteRef, "voteType", 0);
                    }
                } else {
                    if (voteType == 1) {
                        transaction.update(postRef, "totalUpVote", FieldValue.increment(1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(1));
                        transaction.update(voteRef, "voteType", voteType);
                    } else if (voteType == -1) {
                        transaction.update(postRef, "totalDownVote", FieldValue.increment(1));
                        transaction.update(postRef, "voteDifference", FieldValue.increment(-1));
                        transaction.update(voteRef, "voteType", voteType);
                    }
                }

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(FlagsList.DEBUG_POST_FLAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(FlagsList.DEBUG_POST_FLAG, "Transaction failure.", e);
            }

        });


        //how to call this function in FE @Duong Thuan Tri
        /*
            upvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRepository.updateVoteCount(communityID, postID, 1);
                }
            });

            downvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRepository.updateVoteCount(communityID, postID, -1);
                }
            });
        */
    }

    public void getVoteStatus(Post post, String userID, IPostCallback callback) {
        DocumentReference voteRef = db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID())
                .collection("Vote")
                .document(userID);

        voteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    callback.onGetVoteStatusSuccess(documentSnapshot.getLong("voteType").intValue());
                } else {
                    callback.onGetVoteStatusSuccess(0);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onGetVoteStatusSuccess(0);
            }
        });
    }

}
