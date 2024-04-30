package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.io.File;

public class CommunityConcreteBuilder implements CommunityBuilder{
    private String communityName;
    private String communityId;

    private Uri profileImg;
    private String createdDate;
    private String department;


    @Override
    public CommunityBuilder setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }

    @Override
    public CommunityBuilder setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public CommunityBuilder setDepartment(String department) {
        this.department = department;
        return this;
    }

    @Override
    public Community build() {

        return new Community( communityName,  createdDate,  department,  profileImg);
    }

    @Override
    public CommunityBuilder setCommunityId(String id) {
        this.communityName = id;
        return this;
    }
}
