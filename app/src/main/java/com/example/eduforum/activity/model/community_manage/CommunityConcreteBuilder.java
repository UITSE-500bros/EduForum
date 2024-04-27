package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.io.File;

public class CommunityConcreteBuilder implements CommunityBuilder{

    private String communityId;
    private String communityName;

    private Uri image = Uri.fromFile(new File("../../../../res/drawable-v24/welcome1.xml"));
    private String createdDate;
    private String department;

    @Override
    public CommunityBuilder setCommunityId(String communityId) {
        this.communityId = communityId;
        return this;
    }

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
        return new Community(communityId, communityName, createdDate, department);
    }
}
