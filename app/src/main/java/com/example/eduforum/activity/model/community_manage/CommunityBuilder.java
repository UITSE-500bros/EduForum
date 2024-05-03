package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

public interface CommunityBuilder {
    CommunityBuilder setName(String name);
    CommunityBuilder setCreatedDate(String createdDate);
    CommunityBuilder setDepartment(String department);
    CommunityBuilder setDescription(String description);
    Community build();

    CommunityBuilder setCommunityId(String id);

    CommunityBuilder setProfileImage(Uri profileImage);

}
