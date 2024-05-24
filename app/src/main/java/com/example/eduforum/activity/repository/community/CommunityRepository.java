package com.example.eduforum.activity.repository.community;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.community.dto.CreateCommunityDTO;
import com.example.eduforum.activity.repository.community.dto.JoinRequestDTO;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CommunityRepository {
    private static CommunityRepository instance;
    protected FirebaseFirestore db;
    protected FirebaseFunctions mFunctions;
    protected FirebaseStorage storage;
    List<String> communitiesID;
    List<Community> communities;

    private ListenerRegistration registration;

    private final FirebaseAuth currentUser;

    public static synchronized CommunityRepository getInstance() {
        if (instance == null) {
            instance = new CommunityRepository();
        }
        return instance;
    }

    public CommunityRepository() {
        db = FirebaseFirestore.getInstance();
        communitiesID = new ArrayList<>();
        communities = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
        storage = FirebaseStorage.getInstance();

    }

    // TODO: anh trí nhớ gọi removeListener() khi không cần theo dõi nữa
    public void removeListener() {
        if (registration != null) registration.remove();
    }

    public void createCommunity(Community community, ICommunityCallBack callBack) {
        // Create a new document and get its ID
        DocumentReference newCommunityRef = db.collection("Community").document();
        community.setCommunityId(newCommunityRef.getId());
        // upload the community picture to storage,
        // then retrieve the download URL and set to community.profilePicture
        uploadCommunityPicture(community, new IUpload() {
            @Override
            public void onUploadSuccess(String path) {
                community.setProfilePicture(path);
                List<String> admin = new ArrayList<>();
                admin.add(currentUser.getUid());
                community.setAdminList(admin);

                CreateCommunityDTO createCommunityDTO = new CreateCommunityDTO(community);
                Map<String, Object> data = createCommunityDTO.convertToDataObject();
                // call the createCommunity cloud function
                mFunctions.getHttpsCallable("createCommunity")
                        .call(data)
                        .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                            @Override
                            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                                // The function executed successfully, parse the result
                                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                                if (result.containsKey("error")) {
                                    callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE);
                                    Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "Create community failed, DTO validation failed!");
                                } else {
                                    // map the result to community object
                                    community.setUserList((List<String>) result.get("userList"));
                                    community.setTotalPost((Integer) result.get("totalPost"));
                                    community.setTimeCreated((Timestamp) result.get("timeCreated"));
                                    community.setInviteCode((String) result.get("inviteCode"));
                                    callBack.onCreateCommunitySuccess(newCommunityRef.getId());
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // The function execution failed
                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "create community failed with: ", e);
                            }
                        });

            }

            @Override
            public void onUploadFailed(String message) {
                callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE);
            }
        });

//        List<String> admin = new ArrayList<>();
//        admin.add(currentUser.getUid());
//        community.setAdminList(admin);
//
//        List<String> userList = new ArrayList<>();
//        userList.add(currentUser.getUid());
//        community.setUserList(userList);
//
//        community.setTotalPost(0);

        // Create a new document and get its ID
//        DocumentReference newCommunityRef = db.collection("Community").document();
//        community.setCommunityId(newCommunityRef.getId());
//
//        newCommunityRef
//                .set(community)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot written with ID: " + newCommunityRef.getId());
//                        callBack.onCreateCommunitySuccess(newCommunityRef.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error adding document", e);
//                        callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE);
//                    }
//                });
    }

    private void uploadCommunityPicture(Community community, IUpload callBack) {
        // upload the community picture to storage, then retrieve the download URL and set to community.profilePicture
        StorageReference communityRef = storage.getReference("Community/" + community.getCommunityId() + "/images");

        Uri fileUri = community.getProfileImage();
        if (fileUri == null) {
//            callBack.onUploadFailed("No image to upload");
            // for now, assume the uri is not null
            callBack.onUploadSuccess("default");
            return;

        }
        StorageReference userImgRef = communityRef.child(UUID.randomUUID().toString());

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        UploadTask uploadTask = userImgRef.putFile(fileUri, metadata);

        // Register observers to listen for when the upload is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                callBack.onUploadFailed(exception.getMessage());
                Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Image upload failed: ", exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "Image uploaded successfully!");
                callBack.onUploadSuccess(userImgRef.getPath());
            }
        });
    }

    /**
     * - Turn on/off notification for a community.
     * <br></br>
     * - Please ensure that the user is a member of the community before calling this method.
     * <br></br>
     * - Please ensure that the previous notification status is different from the new status.
     *
     * @param communityID the ID of the community
     * @param isOn        true if turn on, false if turn off
     */
    public void toggleNotification(String communityID, boolean isOn) {

        DocumentReference ref = db.collection("Community")
                .document(communityID)
                .collection("Subscription")
                .document("subscription");
        if (isOn) {
            ref.update("userList", FieldValue.arrayUnion(currentUser.getUid()));
        } else {
            ref.update("userList", FieldValue.arrayRemove(currentUser.getUid()));
        }
    }

    /**
     * Get the user's notification status of a community
     * Call this method to check if the user has turned on notification for a community
     *
     * @param communityID the ID of the community
     * @param callBack    the callback to be called when the operation is done, providing the notification status (true if on, false if off)
     */
    public void getNotificationStatus(String communityID, INotificationStatus callBack) {
        db.collection("Community")
                .document(communityID)
                .collection("Subscription")
                .document("subscription")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                try {
                                    List<String> userList = (List<String>) document.get("userList");
                                    if (userList == null) {
                                        userList = new ArrayList<>();
                                        callBack.onNotificationStatusSuccess(false);
                                    } else {
                                        callBack.onNotificationStatusSuccess(userList.contains(currentUser.getUid()));
                                    }
                                } catch (Exception e) {
                                    Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "get notification status failed with: ", task.getException());
                                }
                            } else {
                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "get notification status failed, no document exist! ");

                            }
                        } else {
                            Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "Notification status get failed with ", task.getException());
                        }
                    }
                });
    }


    public void deleteCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).delete();
    }

    public void updateCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).set(community);
    }

    /**
     * Get all communities that the user is not a member of
     *
     * @param userID   the user's ID
     * @param callback the callback to be called when the operation is done, providing a list of communities
     */
    public void exploreCommunity(String userID, IExploreCallback callback) {
        db.collection("Community")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Community> communities = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Community community = document.toObject(Community.class);
                                if (!community.getUserList().contains(userID)) {
                                    communities.add(community);
                                }
                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, document.getId() + " => " + document.getData());
                            }
                            callback.onGetCommunitySuccess(communities);
                        } else {
                            Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "Error getting documents: ", task.getException());
                            callback.onGetCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_GET_COMMUNITY);
                        }
                    }
                });
    }

    /**
     * Send a request to join a community
     *
     * @param user     the user who wants to join the community
     * @param callback the callback to be called when the operation is done
     */
    public void requestJoinCommunity(String communityID, User user, IRequestCallback callback) {

        JoinRequestDTO joinRequestDTO = new JoinRequestDTO(user);

        db.collection("Community")
                .document(communityID)
                .collection("MemberApproval")
                .add(joinRequestDTO)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess("Request sent successfully!");
                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "MemberApproval written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("Failed to send request!");
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error writing MemberApproval", e);
                    }
                });

    }

    /**
     * Get all users who have requested to join a community
     *
     * @param communityID the ID of the community
     * @param callback    the callback to be called when the operation is done, providing a list of users
     */
    public void getMemberApproval(String communityID, IGetMemberApproval callback) {
        db.collection("Community")
                .document(communityID)
                .collection("MemberApproval")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> users = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                users.add(user);
                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, document.getId() + " => " + document.getData());
                            }
                            callback.onGetMemberApprovalSuccess(users);
                        } else {
                            Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "Error getting documents: ", task.getException());
                            callback.onGetMemberApprovalFailure("Failed to fetch MemberApproval!");
                        }
                    }
                });
    }

    /**
     * Approve or reject a user's request to join a community
     *
     * @param communityID the ID of the community
     * @param userID      the ID of the user
     * @param isApprove   true if the request is approved, false if it is rejected
     */
    public void approveUser(String communityID, String userID, boolean isApprove) {
        if (isApprove) {
            db.collection("Community")
                    .document(communityID)
                    .update("userList", FieldValue.arrayUnion(userID));
        }


        db.collection("Community")
                .document(communityID)
                .collection("MemberApproval")
                .document(userID)
                .delete();
    }

    /**
     * Make a user an admin of a community
     *
     * @param communityID the ID of the community
     * @param userID      the ID of the user
     */
    public void makeAdmin(String communityID, String userID) {
        db.collection("Community")
                .document(communityID)
                .update("adminList", FieldValue.arrayUnion(userID));
    }

    /**
     * Remove an admin from a community
     *
     * @param communityID the ID of the community
     * @param userID      the ID of the user
     */
    public void removeAdmin(String communityID, String userID) {
        db.collection("Community")
                .document(communityID)
                .update("adminList", FieldValue.arrayRemove(userID));

    }

    /**
     * Remove a user from a community
     *
     * @param communityID the ID of the community
     * @param userID      the ID of the user
     */
    public void removeUser(String communityID, String userID) {
        db.collection("Community")
                .document(communityID)
                .update("userList", FieldValue.arrayRemove(userID));
    }

    private void createCommunityNewPost(String communityID, String userID) {
        Map<String, Object> data = new HashMap<>();
        data.put("communityID", communityID);
        data.put("userID", userID);
        data.put("totalNewPost", 0);
        db.collection("NewPost").add(data);
    }

    public void thamGia(String communityJoinId, String userId, ICommunityCallBack_A callBack) {
        createCommunityNewPost(communityJoinId, userId);
        db.collection("Community")
                .whereEqualTo("inviteCode", communityJoinId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Community community = document.toObject(Community.class);
                                List<String> userList = community.getUserList();
                                userList.add(userId);
                                db.collection("Community").document(community.getCommunityId())
                                        .update("userList", userList)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                callBack.onJoinCommunitySuccess(FlagsList.ERROR_COMMUNITY_SUCCESS_TO_JOIN);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                callBack.onJoinCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_JOIN);
                                            }
                                        });

                            }

                        } else {
                            // callback thông báo mã không tồn tại, sai mã
                            Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                            callBack.onJoinCommunityFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                        }
                    }
                });
    }

    private void getTotalNewPost(String userID, NewPostCallback callback) {
        Query query = db.collection("NewPost")
                .whereEqualTo("userID", userID);
//        List<NewPost> res = new ArrayList<>();
        Map<String, Integer> res = new HashMap<>();
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    NewPost newPost = document.toObject(NewPost.class);
//                    res.add(newPost);
                    res.put(newPost.getCommunityID(), newPost.getTotalNewPost());
                    Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, document.getId() + " => " + document.getData());
                }
                callback.onCallback(res);
            }
        });
    }

    // TODO: tìm cách gọi lại hàm observeDocument khi navigate từ community về home
    public void observeDocument(String userID, ICommunityChangeListener listener) {
        getTotalNewPost(userID, new NewPostCallback() {
            @Override
            public void onCallback(Map<String, Integer> newPosts) {
                registration = db.collection("Community")
                        .where(
                                Filter.or(
                                        Filter.arrayContains("userList", userID),
                                        Filter.arrayContains("adminList", userID)
                                )
                        ).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "listen:error", e);
                                    return;
                                }
                                assert snapshots != null;

                                List<Community> isMemberOf = new ArrayList<>();
                                List<Community> isAdminOf = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : snapshots) {
                                    Community community = doc.toObject(Community.class);
                                    // TODO: make sure this is correct
                                    community.setTotalNewPost(newPosts.get(community.getCommunityId()));
                                    community.setCommunityId(doc.getId());
                                    if (community.getAdminList().contains(userID)) {
                                        isAdminOf.add(community);
                                    } else if (community.getUserList().contains(userID)) {
                                        isMemberOf.add(community);
                                    }
                                }
                                listener.onCommunityFetch(isMemberOf);
                                listener.onCreateNewCommunity(isAdminOf);
                            }
                        });
            }
        });
        //                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                        QueryDocumentSnapshot doc = dc.getDocument();
//
//                        Community community = doc.toObject(Community.class);
//                        if (doc.getMetadata().hasPendingWrites() && community.getAdminList().contains(userID)) {
//                            isAdminOf.add(community);
//                        }
//                        else if (dc.getType() == DocumentChange.Type.ADDED) {
//                            if (community.getAdminList().contains(userID)){
//                                isAdminOf.add(community);
//                            }
//                        }
//                        else if (community.getUserList().contains(userID)) {
//                            isMemberOf.add(community);
//                        }
//                    }

    }

    /**
     * Get community's member list
     *
     * @param communityID community ID
     * @param callBackC   callback provides list of user ID and admin ID in the community
     */
    public void getCommunityMember(String communityID, ICommunityCallBack_C callBackC) {
        // Create a Map to hold the data
        Map<String, Object> data = new HashMap<>();
        data.put("communityID", communityID);

        // Call the function and add listeners
        mFunctions.getHttpsCallable("getMemberInfo")
                .call(data)
                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                    @Override
                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                        // The function executed successfully, parse the result
                        Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                        List<Object> userListRaw = (List<Object>) result.get("userList");
                        List<Object> adminListRaw = (List<Object>) result.get("adminList");
                        // parse the raw object into User object
                        List<User> userList = new ArrayList<>();
                        List<User> adminList = new ArrayList<>();
                        assert userListRaw != null;
                        for (Object userRaw : userListRaw) {
                            Map<String, Object> userMap = (Map<String, Object>) userRaw;
                            User user = new User();
                            user.setUserId((String) userMap.get("userID"));
                            user.setName((String) userMap.get("name"));
                            user.setProfilePicture((String) userMap.get("profilePicture"));
                            user.setDepartment((String) userMap.get("department"));
                            userList.add(user);
                        }
                        assert adminListRaw != null;
                        for (Object adminRaw : adminListRaw) {
                            Map<String, Object> adminMap = (Map<String, Object>) adminRaw;
                            User admin = new User();
                            admin.setUserId((String) adminMap.get("userID"));
                            admin.setName((String) adminMap.get("name"));
                            admin.setProfilePicture((String) adminMap.get("profilePicture"));
                            admin.setDepartment((String) adminMap.get("department"));
                            adminList.add(admin);
                        }

                        callBackC.onGetCommunityMemberSuccess(userList, adminList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // The function execution failed
                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "get community member failed with: ", e);
                    }
                });
    }

}
