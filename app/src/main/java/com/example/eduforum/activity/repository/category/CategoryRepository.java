package com.example.eduforum.activity.repository.category;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.util.FlagsList;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository {

    private static CategoryRepository instance;
    private FirebaseFirestore db;
    private Map<String, ListenerRegistration> listeners = new HashMap<>();

    public CategoryRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    // real time fetching category to database
    public void fetchCategory(Community community, CategoryCallback callback) {
        final CollectionReference categoryRef = db.collection("Community").document(community.getCommunityId()).collection("Category");
        ListenerRegistration listener = categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(FlagsList.DEBUG_CATEGORY_FLAG, "Category listener failed.", e);
                    callback.onFailure(FlagsList.ERROR_CATEGORY);
                    return;
                }

                List<Category> categories = new ArrayList<>();

                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
                    Category category = doc.toObject(Category.class);
                    categories.add(category);
                }
                Log.d(FlagsList.DEBUG_CATEGORY_FLAG, "Current categories in " + community.getName() + ": " + categories);
                callback.onSuccess(categories);
            }
        });

        listeners.put(community.getCommunityId(), listener);
    }

    // create a new category
    public void createCategory(Community community, Category category, CategoryCallback callback) {
        final CollectionReference categoryRef = db.collection("Community").document(community.getCommunityId()).collection("Category");
        categoryRef.add(category)
                .addOnSuccessListener(documentReference -> {
                    Log.d(FlagsList.DEBUG_CATEGORY_FLAG, "Category written with ID: " + documentReference.getId());
                    category.setCategoryID(documentReference.getId());
                    callback.onCreateCategorySuccess(category);
                })
                .addOnFailureListener(e -> {
                    Log.w(FlagsList.DEBUG_CATEGORY_FLAG, "Error adding category", e);
                    callback.onFailure(FlagsList.ERROR_CATEGORY);
                });
    }
    // delete a category
    // TODO: delete category in post when delete category - THY
    public void deleteCategory(Community community, Category category, CategoryCallback callback) {
        final CollectionReference categoryRef = db.collection("Community").document(community.getCommunityId()).collection("Category");
        categoryRef.document(category.getCategoryID()).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(FlagsList.DEBUG_CATEGORY_FLAG, "Category deleted with ID: " + category.getCategoryID());
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.w(FlagsList.DEBUG_CATEGORY_FLAG, "Error deleting category", e);
                    callback.onFailure(FlagsList.ERROR_CATEGORY);
                });
    }

    // TODO: call removeListener when navigate out of community @Duong Thuan Tri

    public void removeListener(String communityId) {
        ListenerRegistration listener = listeners.get(communityId);
        if (listener != null) {
            listener.remove();
            listeners.remove(communityId);
        }
    }

}
