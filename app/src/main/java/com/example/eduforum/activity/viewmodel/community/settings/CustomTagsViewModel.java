package com.example.eduforum.activity.viewmodel.community.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.repository.category.CategoryCallback;
import com.example.eduforum.activity.repository.category.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomTagsViewModel extends ViewModel {
    MutableLiveData<List<PostCategory>> categoryList;
    MutableLiveData<String> communityId;
    MutableLiveData<String> errorMessage;
    CategoryRepository categoryRepository;
    public CustomTagsViewModel() {
        categoryList = new MutableLiveData<>();
        categoryList.setValue(new ArrayList<>());
        communityId = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        categoryRepository = CategoryRepository.getInstance();
    }
    public LiveData<List<PostCategory>> getCategoryList() {
        return categoryList;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void setCategoryList(List<PostCategory> categoryList) {
        this.categoryList.setValue(categoryList);
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
    }

    public void refreshCategories(){
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        categoryRepository.fetchCategory(community, new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                List<PostCategory> postCategories = new ArrayList<>();
                for(Category category : categories) {
                    postCategories.add(new PostCategory(category.getCategoryID(), category.getTitle()));
                }
                categoryList.setValue(postCategories);
            }
            public void onFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }
      });
    }
    public void addCategory(PostCategory category) {
        Category newCategory = new Category(category.getCategoryID(), category.getTitle(), false);
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        categoryRepository.createCategory(community, newCategory, new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
            }

            @Override
            public void onFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }


    });
    }
    public void deleteCategory(PostCategory category) {
        Category newCategory = new Category(category.getCategoryID(), category.getTitle(), false);
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        categoryRepository.deleteCategory(community, newCategory, new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {

            }

            @Override
            public void onFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }
        });
    }
}
