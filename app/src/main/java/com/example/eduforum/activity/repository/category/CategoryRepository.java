package com.example.eduforum.activity.repository.category;

import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryRepository {

    private static CategoryRepository instance;
    private FirebaseFirestore db;

    public CategoryRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

}
