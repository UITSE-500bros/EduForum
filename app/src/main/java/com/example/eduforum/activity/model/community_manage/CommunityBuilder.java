package com.example.eduforum.activity.model.community_manage;

public interface CommunityBuilder {
    CommunityBuilder setCommunityId(String communityId);
    CommunityBuilder setCommunityName(String communityName);
    CommunityBuilder setCreatedDate(String createdDate);
    CommunityBuilder setDepartment(String department);
    Community build();
}
