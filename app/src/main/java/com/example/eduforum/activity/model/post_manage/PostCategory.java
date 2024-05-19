package com.example.eduforum.activity.model.post_manage;

public class PostCategory {
    private String categoryID;
    private String title;

    public PostCategory(String categoryID, String title) {
        this.categoryID = categoryID;
        this.title = title;
    }

    public PostCategory() {
    }
    public PostCategory(Category category) {
        this.categoryID = category.getCategoryID();
        this.title = category.getTitle();
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
}
