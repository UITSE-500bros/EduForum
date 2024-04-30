package com.example.eduforum.activity.repository;

import com.google.firebase.firestore.DocumentSnapshot;

public interface CommunityChangeListener {
    void onDocumentChange(DocumentSnapshot snapshot);
    void onError(Exception exception);
    void onDocumentNotFound();
}
