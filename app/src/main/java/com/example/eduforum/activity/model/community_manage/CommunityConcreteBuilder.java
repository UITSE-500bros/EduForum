package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class CommunityConcreteBuilder implements CommunityBuilder{
    private String name;
    private String communityId;

    private List<String> adminList;
    private Uri profileImg;
    private Timestamp timeCreated;
    private String department;
    private String description;


    @Override
    public CommunityBuilder setName(String communityName) {
        this.name = communityName;
        return this;
    }

    @Override
    public CommunityBuilder setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    @Override
    public CommunityBuilder setDepartment(String department) {
        this.department = department;
        return this;
    }

    @Override
    public CommunityBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Community build() {

        return new Community(communityId, name, adminList, profileImg, timeCreated, description, department, null);
    }

    @Override
    public CommunityBuilder setCommunityId(String id) {
        this.communityId = id;
        return this;
    }
    public CommunityBuilder setProfileImage(Uri profileImage) {
        this.profileImg = profileImage;
        return this;
    }
}
