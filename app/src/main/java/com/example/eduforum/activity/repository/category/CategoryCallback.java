package com.example.eduforum.activity.repository.category;

import com.example.eduforum.activity.model.post_manage.Category;

import java.util.List;

public abstract class CategoryCallback {
    public abstract void onSuccess(List<Category> categories);
    public abstract void onFailure(String errorMsg);
}
