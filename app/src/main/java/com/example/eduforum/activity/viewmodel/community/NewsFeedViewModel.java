package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eduforum.activity.model.community_manage.Community;

import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_C;
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.ui.community.viewstate.FilterViewState;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedViewModel extends ViewModel {
    MutableLiveData<CreateCommunityViewState> currentCommunity;
    MutableLiveData<String> communityId;
    MutableLiveData<List<PostViewState>> postList; // posts displaying in news feed, not always the same as the posts in the community
    MutableLiveData<FilterViewState> currentFilter;
    CommunityRepository communityRepository;
    PostRepository postRepository;
    public NewsFeedViewModel() {
        communityRepository = CommunityRepository.getInstance();
        postRepository = PostRepository.getInstance();
        communityId = new MutableLiveData<>();
        currentCommunity = new MutableLiveData<>();
        currentCommunity.setValue(new CreateCommunityViewState());
        postList = new MutableLiveData<>();


    }
    public void setCurrentCommunity(CreateCommunityViewState community) {
        currentCommunity.setValue(community);
        postRepository.getPosts(community.getCommunityID(), FirebaseAuth.getInstance().getUid(), new IPostCallback() {
            @Override
            public void onGetPostSuccess(List<Post> posts){
                postList.setValue(convertPostListToPostViewStateList(posts));
            }
            @Override
            public void onGetPostFailure(String errorMsg){
                postList.setValue(new ArrayList<>());
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

    private List<PostViewState> convertPostListToPostViewStateList(List<Post> posts) {
        List<PostViewState> postViewStateList = new ArrayList<>();
        for(Post post : posts) {
            postViewStateList.add(new PostViewState(post.getPostID(), post.getCreator(), currentCommunity.getValue(), post.getTitle(), post.getContent(),post.getAnonymous(), convertTimestampToReadable(post.getTimeCreated()), null, post.getTaggedUsers(), post.getCategory()));
        }
        return postViewStateList;
    }
    private String convertTimestampToReadable(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(time.toDate());
    }
}
