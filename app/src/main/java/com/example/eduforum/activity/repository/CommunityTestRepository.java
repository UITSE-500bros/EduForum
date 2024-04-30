package com.example.eduforum.activity.repository;

public class CommunityTestRepository extends CommunityRepository{
    public CommunityTestRepository() {
        super();
        db.useEmulator("127.0.0.1", 8080);
    }
}
