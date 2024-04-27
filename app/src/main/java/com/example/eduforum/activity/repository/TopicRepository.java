package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.Topic;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopicRepository {
    protected FirebaseFirestore db;
    public TopicRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        db.collection("Topic")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(FlagsList.DEBUG_TOPIC_FLAG, document.getId() + " => " + document.getData());
                                topics.add(document.toObject(Topic.class));
                            }
                        } else {
                            Log.w(FlagsList.DEBUG_TOPIC_FLAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return topics;
    }

    public void getAllDepartments(TopicCallback callback) {
        List<Topic> topics = new ArrayList<>();
        db.collection("Topic")
                .whereEqualTo("isDepartment", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(FlagsList.DEBUG_TOPIC_FLAG, document.getId() + " => " + document.getData());
                                Topic topic = new Topic();
                                topic = document.toObject(Topic.class);
                                topic.setId(document.getId());
                                topics.add(topic);
                                callback.onTopicLoaded(topics);
                            }
                        } else {
                            callback.onError(task.getException());
                            Log.w(FlagsList.DEBUG_TOPIC_FLAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
