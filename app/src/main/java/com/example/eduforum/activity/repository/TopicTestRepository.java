package com.example.eduforum.activity.repository;

@Deprecated
public class TopicTestRepository extends TopicRepository {

    public TopicTestRepository() {
        super();
        db.useEmulator("10.0.2.2", 8080);
    }


}
