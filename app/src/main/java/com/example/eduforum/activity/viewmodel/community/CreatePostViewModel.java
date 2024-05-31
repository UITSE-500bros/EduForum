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
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreatePostViewModel extends ViewModel {
    MutableLiveData<PostViewState> postViewState;
    MutableLiveData<String> communityId;
    MutableLiveData<String> errorMessage;
    MutableLiveData<Boolean> isPostCreated;
    MutableLiveData<List<Category>> allCategories;
    PostRepository postRepository;
    CategoryRepository categoryRepository;
    public CreatePostViewModel() {
        postViewState = new MutableLiveData<>();
        postViewState.setValue(new PostViewState());
        communityId = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        isPostCreated = new MutableLiveData<>();
        isPostCreated.setValue(false);
        postRepository = PostRepository.getInstance();
        categoryRepository = CategoryRepository.getInstance();
        allCategories = new MutableLiveData<>();
        allCategories.setValue(new ArrayList<>());
    }
    public void updateCategories() {
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        categoryRepository.fetchCategory(community, new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                if(categories == null) {
                    allCategories.setValue(new ArrayList<>());
                }
                else allCategories.setValue(categories);
            }
            @Override
            public void onFailure(String errorMsg) {
                errorMessage.setValue("Không thể tải danh sách chuyên mục");
            }

        });
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<PostViewState> getPostViewState() {
        return postViewState;
    }
    public LiveData<Boolean> getIsPostCreated() {
        return isPostCreated;
    }
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    public void setPostViewState(PostViewState postViewState) {
        this.postViewState.setValue(postViewState);
    }
    public LiveData<String> getCommunityId() {
        return communityId;
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
    }
    // ----------------------------------------------------------------------------
    public void createPost(PostViewState state){
        PostViewState newPost = state;
        if(newPost == null){
            errorMessage.setValue("Không thể tạo bài viết");
            return;
        }
        if(!isPostValid(newPost)){
            return;
        }
        String sth = communityId.getValue();
        Post post = new Post(newPost.getPostId(),
                sth,
                newPost.getTitle(),
                newPost.getContent(),
                newPost.getIsAnonymous(),
                null, null, newPost.getCreator(),
                0, 0, 0,0,
                newPost.getImage(), null, newPost.getTags());
        // add new post to database
        postRepository.addPost(post, new IPostCallback() {
            @Override
            public void onAddPostSuccess(Post newPost) {
                //PostViewState newPostViewState = new PostViewState(newPost.getPostID(), newPost.getCreator(), null, newPost.getTitle(), newPost.getContent(), newPost.getAnonymous(), convertTimestampToReadable(newPost.getTimeCreated()), newPost.getImage(), newPost.getTaggedUsers(), newPost.getCategory());
                //postViewState.setValue(newPostViewState);
                isPostCreated.setValue(true);
            }
            @Override
            public void onAddPostFailure(String errorMsg) {
                errorMessage.setValue("Không thể tạo bài viết");
            }
            @Override
            public void onEditPostSuccess() {
            }
            @Override
            public void onEditPostFailure(String errorMsg) {
            }
            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults) {
            }
            @Override
            public void onQueryPostError(String errorMsg) {
            }
            @Override
            public void onGetPostSuccess(List<Post> posts) {
            }
            @Override
            public void onGetPostFailure(String errorMsg) {
            }
            @Override
            public void onDeletePostSuccess() {
            }
            @Override
            public void onDeletePostError(String errorMsg) {
            }
            @Override
            public void onSubscriptionSuccess() {
            }
            @Override
            public void onSubscriptionError(String errorMsg) {
            }
            @Override
            public void onBookmarkError(String errorMsg) {
            }
            @Override
            public void onBookmarkSuccess() {
            }

            @Override
            public void onGetVoteStatusSuccess(int voteType) {

            }

            @Override
            public void onGetOnePostSuccess(Post post) {

            }
        });
    }
    private Boolean isPostValid(PostViewState post){
        if(post.getTitle() == null || post.getTitle().isEmpty()){
            errorMessage.setValue("Tiêu đề không được để trống");
            return false;
        }
        if(post.getContent() == null || post.getContent().isEmpty()){
            errorMessage.setValue("Nội dung không được để trống");
            return false;
        }
        return true;
    }
    private String convertTimestampToReadable(Timestamp timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(timestamp == null){
            return "";
        }
        return sdf.format(timestamp.toDate());
    }
}
