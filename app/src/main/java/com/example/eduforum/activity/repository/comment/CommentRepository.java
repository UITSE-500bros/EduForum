package com.example.eduforum.activity.repository.comment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostingObject;
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentRepository {
    private static CommentRepository instance;
    private final FirebaseFirestore db;
    private final FirebaseStorage firebaseStorage;

    public CommentRepository() {
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static synchronized CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    /**
     * Create a new comment.
     *
     * @param parent     the parent post or comment
     * @param newComment the new comment, including the creator.
     * @param callback   the callback to handle the result.
     *                   <br></br><p>- The <strong>commentID</strong> will be set in the <strong>newComment</strong> object passed in the callback after the comment is created.</p>
     *                   <br></br><p>- The parent totalComment/totalReplies will be recalculated immediately after a new comment is created.</p>
     *                   <br></br><p>- For a reply, the <strong>replyCommentID</strong> will be set in the <strong>newComment</strong> object passed in the callback.</p>
     *                   <br></br><p>- Comment's <strong>lastModified, timeCreated</strong> is handled automatically by Cloud Function.</p>
     *                   <br></br><strong>*Notes:</strong>
     *                   <br></br>- <strong>lastModified and timeCreated</strong> may need to be generated from client side (ViewModel, Activity) to display onto the UI (Ex: Now). After re-fetching comments data, lastModified and timeCreated will be provided from this repository.
     *                   <br></br>- <strong>totalReply, totalComment</strong> from parent post or comment will only be updated if re-fetching data from Firestore. UI logic should handle the increment of totalComment/Replies programmatically.
     */
    //TODO: Uploading images - THY
    public void createComment(PostingObject parent, Comment newComment, CommentCallback callback) {
        CollectionReference commentRef = db.collection("Community")
                .document(parent.getCommunityID())
                .collection("Post")
                .document(parent.getPostID())
                .collection("Comment");
        if (parent instanceof Comment) { // is a reply
            Comment parentComment = (Comment) parent;
            newComment.setReplyCommentID(parentComment.getCommentID());
        }
        newComment.setCommunityID(parent.getCommunityID());
        newComment.setPostID(parent.getPostID());
        commentRef.add(newComment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        newComment.setCommentID(documentReference.getId());
                        callback.onCreateSuccess(newComment);
                        Log.d(FlagsList.DEBUG_COMMENT_FLAG, "Comment written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                        Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Error adding document", e);
                    }
                });

    }

    /**
     * Fetch top level comments of a post. This method should be called when user opens a post.
     * @param post the post to fetch comments from.
     * @param callback the callback to display the top-level comments - containing all metadata of the comment from database.
     */
    // TODO: download and cache images - THY
    public void loadTopLevelComments(Post post, CommentCallback callback) {
        Query commentQuery = db.collection("Community")
                .document(post.getCommunityID())
                .collection("Post")
                .document(post.getPostID())
                .collection("Comment")
                .whereEqualTo("replyCommentID", null);
        commentQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Comment> comments = queryDocumentSnapshots.toObjects(Comment.class);
            callback.onInitialLoadSuccess(comments);
        }).addOnFailureListener(e -> {
            callback.onFailure(e.getMessage());
            Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Error getting documents.", e);
        });
    }

    /**
     * Fetch replies of a comment. This method should be called when user clicks on a comment to view its replies.
     * @param comment the comment to fetch replies from.
     * @param callback the callback to display the replies.
     */
    public void loadReplies(Comment comment, CommentCallback callback) {
        Query commentQuery = db.collection("Community")
                .document(comment.getCommunityID())
                .collection("Post")
                .document(comment.getPostID())
                .collection("Comment")
                .whereEqualTo("replyCommentID", comment.getCommentID());
        commentQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Comment> comments = queryDocumentSnapshots.toObjects(Comment.class);
            callback.onLoadRepliesSuccess(comments);
        }).addOnFailureListener(e -> {
            callback.onFailure(e.getMessage());
            Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Error getting documents.", e);
        });
    }

    /**
     * Delete a comment.
     * @param comment the comment to delete.
     * @param callback the callback to handle the result.
     */
    public void deleteComment(Comment comment, CommentCallback callback) {
        db.collection("Community")
                .document(comment.getCommunityID())
                .collection("Post")
                .document(comment.getPostID())
                .collection("Comment")
                .document(comment.getCommentID())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    callback.onDeleteSuccess();
                    Log.d(FlagsList.DEBUG_COMMENT_FLAG, "Comment successfully deleted!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                    Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Error deleting document", e);
                });
    }

    /**
     * Update a comment.
     * @param comment the comment to update.
     * @param callback the callback to handle the result.
     *                 <br></br><p>- Comment's <strong>lastModified</strong> is handled automatically by Cloud Function.</p>
     *
     */
    public void updateComment(Comment comment, CommentCallback callback) {
        db.collection("Community")
                .document(comment.getCommunityID())
                .collection("Post")
                .document(comment.getPostID())
                .collection("Comment")
                .document(comment.getCommentID())
                .set(comment)
                .addOnSuccessListener(aVoid -> {
                    callback.onUpdateSuccess(comment);
                    Log.d(FlagsList.DEBUG_COMMENT_FLAG, "Comment successfully updated!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                    Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Error updating document", e);
                });
    }

    /**
     * Update vote count of a comment/replies.
     * @param comment the comment to update vote count.
     * @param userID the user who votes.
     * @param voteType the vote type: 1 for upvote, -1 for down-vote.
     *                 <br></br><br></br><strong>*Notes:</strong>
     *                 <br></br>- If the user has already voted, passing the corresponding voteType will cancel the vote.
     */
    public void updateVoteCount(Comment comment, String userID, int voteType) {
        DocumentReference commentRef = db.collection("Community")
                .document(comment.getCommunityID())
                .collection("Post")
                .document(comment.getPostID())
                .collection("Comment")
                .document(comment.getCommentID());
        DocumentReference voteRef = commentRef.collection("Vote").document(userID);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot commentSnapshot = transaction.get(commentRef);
                DocumentSnapshot voteSnapshot = transaction.get(voteRef);

                if (!commentSnapshot.exists()) {
                    throw new FirebaseFirestoreException("Comment does not exist", FirebaseFirestoreException.Code.NOT_FOUND);
                }

                if (!voteSnapshot.exists()) {
                    if (voteType == 1) {
                        transaction.update(commentRef, "totalUpVote", FieldValue.increment(1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(1));
                    } else if (voteType == -1) {
                        transaction.update(commentRef, "totalDownVote", FieldValue.increment(1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(-1));
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
                        transaction.update(commentRef, "totalUpVote", FieldValue.increment(-1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(-1));
                        transaction.update(voteRef, "voteType", 0);
                    } else if (voteType == -1) {
                        transaction.update(commentRef, "totalUpVote", FieldValue.increment(-1));
                        transaction.update(commentRef, "totalDownVote", FieldValue.increment(1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(-2));
                        transaction.update(voteRef, "voteType", voteType);
                    }
                } else {
                    if (voteType == 1) {
                        transaction.update(commentRef, "totalUpVote", FieldValue.increment(1));
                        transaction.update(commentRef, "totalDownVote", FieldValue.increment(-1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(2));
                        transaction.update(voteRef, "voteType", voteType);
                    } else if (voteType == -1) {
                        transaction.update(commentRef, "totalDownVote", FieldValue.increment(-1));
                        transaction.update(commentRef, "voteDifference", FieldValue.increment(1));
                        transaction.update(voteRef, "voteType", 0);
                    }
                }

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(FlagsList.DEBUG_COMMENT_FLAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(FlagsList.DEBUG_COMMENT_FLAG, "Transaction failure.", e);
            }

        });

    }

    /**
     * Get vote status of a comment.
     * @param comment the comment to get vote status.
     * @param userID the user (current user using the app) to get vote status.
     * @param callback the callback containing the user's current vote status of the comment. (0: no vote, 1: upvote, -1: downvote)
     */
    public void getVoteStatus(Comment comment, String userID, CommentCallback callback) {
        DocumentReference voteRef = db.collection("Community")
                .document(comment.getCommunityID())
                .collection("Post")
                .document(comment.getPostID())
                .collection("Comment")
                .document(comment.getCommentID())
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
