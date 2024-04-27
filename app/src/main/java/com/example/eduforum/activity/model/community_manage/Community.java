package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.io.File;

public class Community {
    private String communityId;
    private String communityName;

    private Uri image = Uri.fromFile(new File("../../../../res/drawable-v24/welcome1.xml"));
    private String createdDate;
    private String department;


    public Community() {
    }

    public Community(String communityId, String communityName, String createdDate, String department) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.createdDate = createdDate;
        this.department = department;
    }
}
