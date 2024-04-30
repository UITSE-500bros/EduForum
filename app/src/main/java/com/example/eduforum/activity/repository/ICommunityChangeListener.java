package com.example.eduforum.activity.repository;

import com.google.firebase.firestore.DocumentSnapshot;

public interface ICommunityChangeListener {
    void onDocumentChange(DocumentSnapshot snapshot);
    void onError(Exception exception);
    void onDocumentNotFound();
}
