package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class CreatePostViewModel extends ViewModel {
    MutableLiveData<PostViewState> postViewState;
    MutableLiveData<String> communityId;
    MutableLiveData<String> errorMessage;
    MutableLiveData<Boolean> isPostCreated;
    PostRepository postRepository;
    public CreatePostViewModel() {
        postViewState = new MutableLiveData<>();
        postViewState.setValue(new PostViewState());
        communityId = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        isPostCreated = new MutableLiveData<>();
        isPostCreated.setValue(false);
        postRepository = PostRepository.getInstance();
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
    public void createPost(){
        PostViewState newPost = postViewState.getValue();
        if(newPost == null){
            errorMessage.setValue("Không thể tạo bài viết");
            return;
        }
        if(!isPostValid(newPost)){
            return;
        }
        String sth = communityId.getValue();
        Post post = new Post(newPost.getPostId(), sth, newPost.getTitle(), newPost.getContent(), newPost.getIsAnonymous(), null, null, newPost.getCreator(), 0, 0, 0,0, null, null, newPost.getTags());
        // add new post to database
        postRepository.addPost(post, new IPostCallback() {
            @Override
            public void onAddPostSuccess(Post newPost) {
                PostViewState newPostViewState = new PostViewState(newPost.getPostID(), newPost.getCreator(), null, newPost.getTitle(), newPost.getContent(), newPost.getAnonymous(), convertTimestampToReadable(newPost.getTimeCreated()), newPost.getImage(), newPost.getTaggedUsers(), newPost.getCategory());
                postViewState.setValue(newPostViewState);
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
        });
    }
    private Boolean isPostValid(PostViewState post){
        if(post.getTitle() == null || post.getTitle().isEmpty()){
            errorMessage.setValue("Tiêu đề không được để trống");
            return false;
        }
        // other validations ...
        return true;
    }
    private String convertTimestampToReadable(Timestamp timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(timestamp.toDate());
    }
}
