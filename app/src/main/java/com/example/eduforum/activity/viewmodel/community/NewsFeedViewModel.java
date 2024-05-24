package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eduforum.activity.model.community_manage.Community;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.repository.category.CategoryCallback;
import com.example.eduforum.activity.repository.category.CategoryRepository;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_C;
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.ui.community.viewstate.FilterViewState;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsFeedViewModel extends ViewModel {
    MutableLiveData<CreateCommunityViewState> currentCommunity;
    MutableLiveData<String> communityId;
    MutableLiveData<List<PostViewState>> postList; // posts displaying in news feed, not always the same as the posts in the community
    MutableLiveData<FilterViewState> currentFilter;
    MutableLiveData<List<PostCategory>> allCategories;
    MutableLiveData<String> errorMessage;
    CommunityRepository communityRepository;
    PostRepository postRepository;
    CategoryRepository categoryRepository;
    public NewsFeedViewModel() {
        communityRepository = CommunityRepository.getInstance();
        postRepository = PostRepository.getInstance();
        categoryRepository = CategoryRepository.getInstance();
        communityId = new MutableLiveData<>();
        currentCommunity = new MutableLiveData<>();
        currentCommunity.setValue(new CreateCommunityViewState());
        postList = new MutableLiveData<>();
        allCategories = new MutableLiveData<>();

        currentFilter = new MutableLiveData<>();
        currentFilter.setValue(new FilterViewState());
        errorMessage = new MutableLiveData<>();
    }
    public void updateCategories() {
        Community community = new Community();
        community.setCommunityId(currentCommunity.getValue().getCommunityID());
        categoryRepository.fetchCategory(community, new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                List<PostCategory> postCategories = new ArrayList<>();
                for(Category category : categories) {
                    postCategories.add(new PostCategory(category.getCategoryID(), category.getTitle()));
                }
                allCategories.setValue(postCategories);
            }
            @Override
            public void onFailure(String errorMsg) {}

        });
    }
    public void setFilter(FilterViewState filter) {
        currentFilter.setValue(filter);
        postRepository.queryPost(currentCommunity.getValue().getCommunityID(),FirebaseAuth.getInstance().getUid(), filter.getTags(),  filter.getPostQuery(), new IPostCallback() {
            @Override
            public void onGetPostSuccess(List<Post> posts){
            }
            @Override
            public void onGetPostFailure(String errorMsg){
            }
            @Override
            public void onAddPostFailure(String errorMsg){

            }
            @Override
            public void onAddPostSuccess(Post newPost){

            }
            @Override
            public void onEditPostSuccess(){

            }
            @Override
            public void onEditPostFailure(String errorMsg){
            }
            @Override
            public void onQueryPostError(String errorMsg){
                errorMessage.setValue("Không thể lấy bài viết");
            }
            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults){
                postList.setValue(convertPostListToPostViewStateList(queryPostResults));

            }
            @Override
            public void onDeletePostSuccess(){

            }
            @Override
            public void onDeletePostError(String errorMsg){
            }
            @Override
            public void onSubscriptionSuccess(){

            }
            @Override
            public void onSubscriptionError(String errorMsg){

            }
            @Override
            public void onBookmarkError(String errorMsg){

            }
            @Override
            public void onBookmarkSuccess(){

            }

            @Override
            public void onGetVoteStatusSuccess(int voteType) {

            }

        });
    }
    public void filterBySearch(String keyword) {
        keyword = keyword.trim();
        if(keyword.isEmpty()) {
            refreshPostList();
            return;
        }
        postRepository.searchPost(currentCommunity.getValue().getCommunityID(), keyword, new IPostCallback() {
            @Override
            public void onGetPostSuccess(List<Post> posts){
            }
            @Override
            public void onGetPostFailure(String errorMsg){
            }
            @Override
            public void onAddPostFailure(String errorMsg){

            }
            @Override
            public void onAddPostSuccess(Post newPost){

            }
            @Override
            public void onEditPostSuccess(){

            }
            @Override
            public void onEditPostFailure(String errorMsg){

            }
            @Override
            public void onQueryPostError(String errorMsg){
                errorMessage.setValue("Đã xảy ra lỗi! Không thể tìm kiếm bài viết!");

            }
            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults){
                postList.setValue(convertPostListToPostViewStateList(queryPostResults));

            }
            @Override
            public void onDeletePostSuccess(){

            }
            @Override
            public void onDeletePostError(String errorMsg){
            }
            @Override
            public void onSubscriptionSuccess(){

            }
            @Override
            public void onSubscriptionError(String errorMsg){

            }
            @Override
            public void onBookmarkError(String errorMsg){

            }
            @Override
            public void onBookmarkSuccess(){

            }

            @Override
            public void onGetVoteStatusSuccess(int voteType) {

            }
        });
    }

    public void setCurrentCommunity(CreateCommunityViewState community) {
        currentCommunity.setValue(community);
        refreshPostList();
    }
    public void refreshPostList(){
        postRepository.getPosts(currentCommunity.getValue().getCommunityID(), FirebaseAuth.getInstance().getUid(), new IPostCallback() {
            @Override
            public void onGetPostSuccess(List<Post> posts){
                postList.setValue(convertPostListToPostViewStateList(posts));
                errorMessage.setValue("Success");
            }
            @Override
            public void onGetPostFailure(String errorMsg){
                errorMessage.setValue("Không thể lấy bài viết");
            }
            @Override
            public void onAddPostFailure(String errorMsg){

            }
            @Override
            public void onAddPostSuccess(Post newPost){

            }
            @Override
            public void onEditPostSuccess(){

            }
            @Override
            public void onEditPostFailure(String errorMsg){
            }
            @Override
            public void onQueryPostError(String errorMsg){

            }
            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults){

            }
            @Override
            public void onDeletePostSuccess(){

            }
            @Override
            public void onDeletePostError(String errorMsg){
            }
            @Override
            public void onSubscriptionSuccess(){

            }
            @Override
            public void onSubscriptionError(String errorMsg){

            }
            @Override
            public void onBookmarkError(String errorMsg){

            }
            @Override
            public void onBookmarkSuccess(){

            }

            @Override
            public void onGetVoteStatusSuccess(int voteType) {

            }
        });
    }
    public LiveData<CreateCommunityViewState> getCurrentCommunity() {
        return currentCommunity;
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
    }
    public LiveData<String> getCommunityId() {
        return communityId;
    }
    public LiveData<List<PostViewState>> getPostList() {
        return postList;
    }
    public LiveData<List<PostCategory>> getAllCategories() {
        return allCategories;
    }
    public LiveData<FilterViewState> getCurrentFilter() {
        return currentFilter;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    private List<PostViewState> convertPostListToPostViewStateList(List<Post> posts) {
        List<PostViewState> postViewStateList = new ArrayList<>();
        for(Post post : posts) {
            postViewStateList.add(new PostViewState(post.getPostID(), post.getCreator(), currentCommunity.getValue(), post.getTitle(), post.getContent(),post.getAnonymous(), convertTimestampToReadable(post.getTimeCreated()), null, post.getTaggedUsers(), post.getCategory(), post.getVoteDifference(),post.getTotalComment()));
        }
        return postViewStateList;
    }

    private String convertTimestampToReadable(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(time == null) {
            return "";
        }
        return sdf.format(time.toDate());

    }
}
