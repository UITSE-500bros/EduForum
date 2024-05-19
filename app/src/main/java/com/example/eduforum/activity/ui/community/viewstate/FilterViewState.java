package com.example.eduforum.activity.ui.community.viewstate;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.repository.post.PostQuery;

import java.util.List;

public class FilterViewState {
    private PostQuery postQuery;
    private List<Category> tags;
    public FilterViewState(PostQuery postQuery, List<Category> tags) {
        this.postQuery = postQuery;
        this.tags = tags;
    }
    public FilterViewState(List<Category> tags){
        this.postQuery = PostQuery.NEWEST;
        this.tags = tags;
    }
    public FilterViewState(){
        this.postQuery = PostQuery.NEWEST;
        this.tags = null;
    }
    public PostQuery getPostQuery() {
        return postQuery;
    }
    public List<Category> getTags() {
        return tags;
    }
    public void setPostQuery(PostQuery postQuery) {
        this.postQuery = postQuery;
    }
    public void setTags(List<Category> tags) {
        this.tags = tags;
    }
}
