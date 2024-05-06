package com.example.eduforum.activity.model.PostModel;

public class Creator {
    public String creatorID;
    public String creatorName;
    public String creatorDepartment;
    public String creatorImage;

    public Creator() {
    }

    public Creator(String creatorID, String creatorName, String creatorDepartment, String creatorImage) {
        this.creatorID = creatorID;
        this.creatorName = creatorName;
        this.creatorDepartment = creatorDepartment;
        this.creatorImage = creatorImage;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorDepartment() {
        return creatorDepartment;
    }

    public void setCreatorDepartment(String creatorDepartment) {
        this.creatorDepartment = creatorDepartment;
    }

    public String getCreatorImage() {
        return creatorImage;
    }

    public void setCreatorImage(String creatorImage) {
        this.creatorImage = creatorImage;
    }
}
