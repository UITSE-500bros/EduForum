package com.example.eduforum.activity.ui.main.fragment;

import android.net.Uri;

public class CreateCommunityViewState {
    String name;
    String description;
    String category;
    Uri commuAvt;
    String communityID;

    public CreateCommunityViewState(String name, String description, String category, Uri commuAvt) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.commuAvt = commuAvt;
    }
    public CreateCommunityViewState() {}

    public Uri getCommuAvt() {
        return commuAvt;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public String getCommunityID() {return communityID;}
    public void setCommuAvt(Uri commuAvt) {
        this.commuAvt = commuAvt;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setCommunityID(String communityID) {this.communityID = communityID;}
}
