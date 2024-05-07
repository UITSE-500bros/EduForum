package com.example.eduforum.activity.model.post_manage;

public class Category {
    public String categoryID;
    public String title;
    public Boolean isAnnouncement;

    public Category() {
    }

    public Category(String categoryID, String title, Boolean isAnnouncement) {
        this.categoryID = categoryID;
        this.title = title;
        this.isAnnouncement = isAnnouncement;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAnnouncement() {
        return isAnnouncement;
    }

    public void setAnnouncement(Boolean announcement) {
        isAnnouncement = announcement;
    }
}