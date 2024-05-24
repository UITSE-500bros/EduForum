package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import com.google.firebase.Timestamp;

public interface CommunityBuilder {
    CommunityBuilder setName(String name);
    CommunityBuilder setTimeCreated(Timestamp timeCreated);
    CommunityBuilder setDepartment(String department);
    CommunityBuilder setDescription(String description);
    Community build();

    CommunityBuilder setCommunityId(String id);

    CommunityBuilder setProfileImage(Uri profileImage);

}
