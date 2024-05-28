package com.example.eduforum.activity.model.post_manage;

import java.io.Serializable;
import java.util.Map;

public class PostCategory implements Serializable {
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

    public Map<String, Object> convertToDataObject() {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("categoryID", categoryID);
        data.put("title", title);
        return data;
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
