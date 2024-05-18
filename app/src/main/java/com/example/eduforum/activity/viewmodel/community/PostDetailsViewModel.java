package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.repository.comment.CommentCallback;
import com.example.eduforum.activity.repository.comment.CommentRepository;
import com.example.eduforum.activity.repository.post.IPostCallback;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

// TODO: Nam lam cai nay ne Nam
public class PostDetailsViewModel extends ViewModel {
    PostRepository postRepository;
    CommentRepository commentRepository;

    MutableLiveData<List<CommentViewState>> cmts;

    MutableLiveData<PostViewState> currentPost;


    public PostDetailsViewModel(PostViewState postViewState) {
        postRepository = PostRepository.getInstance();
        commentRepository = CommentRepository.getInstance();
        cmts = new MutableLiveData<>();
        currentPost = new MutableLiveData<>();
        currentPost.setValue(postViewState);
    }

    public LiveData<PostViewState> getPost(){
        return currentPost;
    }
    public LiveData<List<CommentViewState>> getComments(){
        return cmts;
    }

    private List<CommentViewState> convertCommentListToCommentViewStateList(List<Comment> comments){
        List<CommentViewState> commentViewStates = new ArrayList<>();
        for(Comment comment: comments){
            commentViewStates.add(new CommentViewState(
                    comment.getCommentID(),
                    comment.getContent(),
                    comment.getTimeCreated(),
                    comment.getCreator(),
                    comment.getTotalUpVote(),
                    comment.getTotalDownVote(),
                    comment.getVoteDifference(),
                    comment.getLastModified(),
                    comment.getImage(),
                    comment.getReplyCommentID(),
                    comment.getTotalReply()
            ));
        }
        return commentViewStates;

    }

    public void setCurrentPost(PostViewState postViewState) {
        currentPost.setValue(postViewState);

        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), postViewState.getDate(), postViewState.getDate(), postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());
        commentRepository.loadTopLevelComments(post, new CommentCallback() {

            @Override
            public void onCreateSuccess(Comment comments) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onInitialLoadSuccess(List<Comment> comments) {

            }

            @Override
            public void onLoadRepliesSuccess(List<Comment> comments) {

            }

            @Override
            public void onDeleteSuccess() {

            }

            @Override
            public void onUpdateSuccess(Comment comment) {

            }

            @Override
            public void onGetVoteStatusSuccess(int voteType) {

            }
        });
    }
}
