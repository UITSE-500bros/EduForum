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
    MutableLiveData<List<CommentViewState>> cmts_child;
    MutableLiveData<List<CommentViewState>> cmts_child_child;

    MutableLiveData<PostViewState> currentPost;


    public PostDetailsViewModel() {
        postRepository = PostRepository.getInstance();
        commentRepository = CommentRepository.getInstance();
        cmts = new MutableLiveData<>();
        cmts_child = new MutableLiveData<>();
        currentPost = new MutableLiveData<>();
        cmts.setValue(new ArrayList<CommentViewState>());
        cmts_child_child= new MutableLiveData<>();
    }
    public LiveData<List<CommentViewState>> getCmts_child_child() {
        return cmts_child_child;
    }

    public LiveData<PostViewState> getPost(){
        return currentPost;
    }
    public LiveData<List<CommentViewState>> getComments(){
        return cmts;
    }
    public LiveData<List<CommentViewState>> getCommentsChild(){
        return cmts_child;
    }

    // TODO: anh nam lam cai nay ne
    private List<CommentViewState> convertCommentListToCommentViewStateList(List<Comment> comments){
        List<CommentViewState> commentViewStates = new ArrayList<>();
        for(Comment comment: comments){
            commentViewStates.add(new CommentViewState(
                    comment.getCommentID(),
                    comment.getContent(),
                    String.valueOf(comment.getTimeCreated()),
                    comment.getCreator(),
                    comment.getTotalUpVote(),
                    comment.getTotalDownVote(),
                    comment.getVoteDifference(),
                    String.valueOf(comment.getLastModified()),
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
    private String pt_id;
    private String community_id;
    public void loadComments(PostViewState postViewState){
        Post post = new Post();
        post.setPostID(postViewState.getPostId());
        post.setCommunityID(postViewState.getCommunity().getCommunityID());


        pt_id = postViewState.getPostId();
        community_id = postViewState.getCommunity().getCommunityID();

        commentRepository.loadTopLevelComments(post, new CommentCallback() {

            @Override
            public void onCreateSuccess(Comment comments) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onInitialLoadSuccess(List<Comment> comments) {
                cmts.postValue(convertCommentListToCommentViewStateList(comments));
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

    public void downVote(PostViewState postViewState) {
        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), null, null, postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());

        postRepository.updateVoteCount(post, FirebaseAuth.getInstance().getCurrentUser().getUid(), -1);
    }

    public void addParentComment(CommentViewState comment,String postID, String communityID) {
        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setCommunityID(community_id);
        newComment.setPostID(pt_id);
        newComment.setCreator(comment.getCreator());
        newComment.setImage(comment.getImage());
        newComment.setTotalReply(0);
        newComment.setTotalUpVote(0);
        newComment.setTotalDownVote(0);
        newComment.setVoteDifference(0);


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
                        null,
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

    public void addChildComment(CommentViewState commentParentViewState, CommentViewState commentChildViewState) {
        Comment parentComment = new Comment();
        parentComment.setCommentID(commentParentViewState.getCommentID());
        parentComment.setContent(commentParentViewState.getContent());
        parentComment.setCommunityID(community_id);
        parentComment.setPostID(pt_id);
        parentComment.setCreator(commentParentViewState.getCreator());


        Comment childComment = new Comment();
        childComment.setPostID(pt_id);
        childComment.setCommunityID(community_id);
        childComment.setContent(commentChildViewState.getContent());
        childComment.setCreator(commentChildViewState.getCreator());
        childComment.setReplyCommentID(commentParentViewState.getCommentID());
        childComment.setCommentID(null);
        childComment.setImage(commentChildViewState.getImage());


        commentRepository.createComment(parentComment, childComment, new CommentCallback() {
            @Override
            public void onCreateSuccess(Comment comments) {
                CommentViewState commentViewState = new CommentViewState(
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
                );

                List<CommentViewState> commentViewStates = cmts.getValue();
                assert commentViewStates != null;
                commentViewStates.add(commentViewState);
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

    public void loadChildComments(CommentViewState commentParentViewState) {
        Comment parentComment = new Comment(
        );
        parentComment.setCommentID(commentParentViewState.getCommentID());
        parentComment.setContent(commentParentViewState.getContent());
        parentComment.setCommunityID(community_id);
        parentComment.setPostID(pt_id);
        parentComment.setCreator(commentParentViewState.getCreator());


        commentRepository.loadReplies(parentComment, new CommentCallback() {
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
                List<CommentViewState> newCommentViewStates = new ArrayList<>(cmts.getValue());
                newCommentViewStates.addAll(convertCommentListToCommentViewStateList(comments));
                cmts.postValue(newCommentViewStates);
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

    public void downVote(CommentViewState commentViewState){
        Comment comment = new Comment();
        comment.setCommentID(commentViewState.getCommentID());
        comment.setContent(commentViewState.getContent());
        comment.setCommunityID(community_id);
        comment.setPostID(pt_id);

        commentRepository.updateVoteCount(comment, FirebaseAuth.getInstance().getCurrentUser().getUid(), -1);
    }

    public void upVote(CommentViewState commentViewState){
        Comment comment = new Comment();
        comment.setCommentID(commentViewState.getCommentID());
        comment.setContent(commentViewState.getContent());
        comment.setCommunityID(community_id);
        comment.setPostID(pt_id);

        commentRepository.updateVoteCount(comment, FirebaseAuth.getInstance().getCurrentUser().getUid(), 1);
    }

    public void getVoteStatus(CommentViewState commentViewState){
        Comment comment = new Comment(
                commentViewState.getCommentID(),
                commentViewState.getContent(),
                postInstance.getCommunityID(),
                commentViewState.getReplyCommentID(),
                commentViewState.getContent(),
                null,
                null,
                commentViewState.getCreator(),
                commentViewState.getTotalUpVote(),
                commentViewState.getTotalDownVote(),
                commentViewState.getVoteDifference(),
                commentViewState.getImage()
        );

            commentRepository.getVoteStatus(comment, FirebaseAuth.getInstance().getCurrentUser().getUid(), new CommentCallback() {
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

    public void deletePost(PostViewState postViewState) {
        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), null, null, postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());

        postRepository.deletePost(post, new IPostCallback() {

            @Override
            public void onGetPostSuccess(List<Post> posts) {

            }

            @Override
            public void onGetPostFailure(String errorMsg) {

            }

            @Override
            public void onAddPostFailure(String errorMsg) {

            }

            @Override
            public void onAddPostSuccess(Post newPost) {

            }

            @Override
            public void onEditPostSuccess() {

            }

            @Override
            public void onEditPostFailure(String errorMsg) {

            }

            @Override
            public void onQueryPostError(String errorMsg) {

            }

            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults) {

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

    public void editPost(PostViewState postViewState) {
        Post post = new Post(postViewState.getPostId(), postViewState.getCommunity().getCommunityID(), postViewState.getTitle(), postViewState.getContent(), postViewState.getIsAnonymous(), null, null, postViewState.getCreator(), 0, 0, 0,0, null, null, postViewState.getTags());

        postRepository.editPost(post, new IPostCallback() {

            @Override
            public void onGetPostSuccess(List<Post> posts) {

            }

            @Override
            public void onGetPostFailure(String errorMsg) {

            }

            @Override
            public void onAddPostFailure(String errorMsg) {

            }

            @Override
            public void onAddPostSuccess(Post newPost) {

            }

            @Override
            public void onEditPostSuccess() {

            }

            @Override
            public void onEditPostFailure(String errorMsg) {

            }

            @Override
            public void onQueryPostError(String errorMsg) {

            }

            @Override
            public void onQueryPostSuccess(List<Post> queryPostResults) {

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
}
