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


    public PostDetailsViewModel() {
        postRepository = PostRepository.getInstance();
        commentRepository = CommentRepository.getInstance();
        cmts = new MutableLiveData<>();
        currentPost = new MutableLiveData<>();
        cmts.setValue(new ArrayList<CommentViewState>());
    }

    public LiveData<PostViewState> getPost(){
        return currentPost;
    }
    public LiveData<List<CommentViewState>> getComments(){
        return cmts;
    }

    // TODO: anh nam lam cai nay ne
    private List<CommentViewState> convertCommentListToCommentViewStateList(List<Comment> comments){
        List<CommentViewState> commentViewStates = new ArrayList<>();
        for(Comment comment: comments){
            commentViewStates.add(new CommentViewState(
                    comment.getCommentID(),
                    comment.getContent(),
                    null,
                    comment.getCreator(),
                    comment.getTotalUpVote(),
                    comment.getTotalDownVote(),
                    comment.getVoteDifference(),
                    null,
                    comment.getImage(),
                    comment.getReplyCommentID(),
                    comment.getTotalReply()
            ));
        }
        return commentViewStates;

    }

    private Post postInstance;
    // TODO: anh em lam cai nay ne
    public void setCurrentPost(PostViewState postViewState) {
        currentPost.setValue(postViewState);

        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), null, null, postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());
        postInstance = post;
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

    public void loadComments(PostViewState postViewState){
        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), null, null, postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());
        commentRepository.loadTopLevelComments(post, new CommentCallback() {

            @Override
            public void onCreateSuccess(Comment comments) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onInitialLoadSuccess(List<Comment> comments) {
                cmts.setValue(convertCommentListToCommentViewStateList(comments));
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

    public void upVote() {
        postRepository.updateVoteCount(postInstance, FirebaseAuth.getInstance().getCurrentUser().getUid(), 1);
    }

    public void downVote() {
        postRepository.updateVoteCount(postInstance, FirebaseAuth.getInstance().getCurrentUser().getUid(), -1);
    }

    public void addParentComment(CommentViewState comment,String postID, String communityID) {
        Comment newComment = new Comment(
                null,
                comment.getContent(),
                communityID,
                postID,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                null
        );
        commentRepository.createComment(postInstance, newComment, new CommentCallback() {
            @Override
            public void onCreateSuccess(Comment comments) {
                List<CommentViewState> commentViewStates = cmts.getValue();
                assert commentViewStates != null;
                commentViewStates.add(new CommentViewState(
                        comments.getCommentID(),
                        comments.getContent(),
                        null,
                        comments.getCreator(),
                        comments.getTotalUpVote(),
                        comments.getTotalDownVote(),
                        comments.getVoteDifference(),
                        null,
                        comments.getImage(),
                        comments.getReplyCommentID(),
                        comments.getTotalReply()
                ));
                cmts.setValue(commentViewStates);
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

    public void addComment(CommentViewState commentViewState) {
    }

    public void addChildComment(CommentViewState commentParentViewState, CommentViewState commentChildViewState) {
        Comment parentComment = new Comment(); // TODO: transfer parentCommentViewState to Comment
        Comment childComment = new Comment(); // TODO: transfer childCommentViewState to Comment

        commentRepository.createComment(parentComment, childComment, new CommentCallback() {
            @Override
            public void onCreateSuccess(Comment comments) {
                List<CommentViewState> commentViewStates = cmts.getValue();
                commentViewStates.add(new CommentViewState(
                        comments.getCommentID(),
                        comments.getContent(),
                        null,
                        comments.getCreator(),
                        comments.getTotalUpVote(),
                        comments.getTotalDownVote(),
                        comments.getVoteDifference(),
                        null,
                        comments.getImage(),
                        comments.getReplyCommentID(),
                        comments.getTotalReply()
                ));
                cmts.setValue(commentViewStates);
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
