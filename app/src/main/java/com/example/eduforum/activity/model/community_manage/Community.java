package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.io.File;

public class Community {
    private String communityId;
    private String communityName;

    private Uri image = Uri.fromFile(new File("../../../../res/drawable-v24/welcome1.xml"));
    private String createdDate;
    private String description;//todo
    private String department;


    public Community() {
    }

    public Community(String communityName, String createdDate, String department) {
        this.communityName = communityName;
        this.createdDate = createdDate;
        this.department = department;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public Uri getImage() {
        return image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDepartment() {
        return department;
    }
}
