package com.example.eduforum.activity.ui.community.viewstate;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

import java.util.List;

public class PostViewState {
    private User author;
    private CreateCommunityViewState community;
    private String title;
    private String content;
    private String date;
    private List<Category> tags;
    public PostViewState(User author, CreateCommunityViewState community, String title, String content, String date, List<Category> tags) {
        this.author = author;
        this.community = community;
        this.title = title;
        this.content = content;
        this.date = date;
        this.tags = tags;
    }
    public PostViewState(){
        this.author = null;
        this.community = null;
        this.title = null;
        this.content = null;
        this.date = null;
        this.tags = null;
    }
    public User getAuthor() {
        return author;
    }
    public CreateCommunityViewState getCommunity() {
        return community;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
    public List<Category> getTags() {
        return tags;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public void setCommunity(CreateCommunityViewState community) {
        this.community = community;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTags(List<Category> tags) {
        this.tags = tags;
    }



}
