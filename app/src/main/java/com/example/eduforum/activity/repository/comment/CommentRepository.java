package com.example.eduforum.activity.repository.comment;

import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.model.post_manage.PostingObject;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

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

    // create a comment
    public void createComment(PostingObject parent, Comment newComment) {

    }
}
