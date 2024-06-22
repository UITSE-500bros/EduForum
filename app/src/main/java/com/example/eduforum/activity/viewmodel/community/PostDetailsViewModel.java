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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// TODO: Nam lam cai nay ne Nam
public class PostDetailsViewModel extends ViewModel {
    PostRepository postRepository;
    CommentRepository commentRepository;
    MutableLiveData<List<CommentViewState>> cmts;
    MutableLiveData<List<CommentViewState>> cmts_child;
    MutableLiveData<PostViewState> currentPost;
    MutableLiveData<Integer> voteType;

    public PostDetailsViewModel() {
        postRepository = PostRepository.getInstance();
        commentRepository = CommentRepository.getInstance();
        cmts = new MutableLiveData<>();
        cmts_child = new MutableLiveData<>();
        currentPost = new MutableLiveData<>();
        cmts.setValue(new ArrayList<CommentViewState>());
        voteType = new MutableLiveData<>();


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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

        for(Comment comment: comments){
            commentViewStates.add(new CommentViewState(
                    comment.getCommentID(),
                    comment.getContent(),
                    dateFormat.format(comment.getTimeCreated().toDate()), // Convert timestamp to string
                    comment.getCreator(),
                    comment.getTotalUpVote(),
                    comment.getTotalDownVote(),
                    comment.getVoteDifference(),
                    dateFormat.format(comment.getLastModified().toDate()), // Convert timestamp to string
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

        Post post = new Post(postViewState.getPostId(),
                postViewState.getCommunity().getCommunityID(),
                postViewState.getTitle(),
                postViewState.getContent(),
                postViewState.getIsAnonymous(), null, null,
                postViewState.getCreator(), 0, 0, 0,0,
                null, null,
                postViewState.getTags());
        postInstance = post;
        pt_id = post.getPostID();
        community_id = post.getCommunityID();

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
    private String pt_id;
    private String community_id;


    public void upVote() {
        Post newPost = new Post();
        newPost.setPostID(pt_id);
        newPost.setCommunityID(community_id);
        postRepository.updateVoteCount(newPost, FirebaseAuth.getInstance().getCurrentUser().getUid(), 1);
    }

    public void downVote(PostViewState postViewState) {
        Post newPost = new Post();
        newPost.setPostID(pt_id);
        newPost.setCommunityID(community_id);
        postRepository.updateVoteCount(newPost, FirebaseAuth.getInstance().getCurrentUser().getUid(), -1);
    }

    public void addParentComment(CommentViewState comment) {
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

        Post newPost = new Post();
        newPost.setPostID(pt_id);
        newPost.setCommunityID(community_id);

        commentRepository.createComment(newPost, newComment, new CommentCallback() {
            @Override
            public void onCreateSuccess(Comment comments) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                List<CommentViewState> commentViewStates = cmts.getValue();
                assert commentViewStates != null;
                commentViewStates.add(new CommentViewState(
                        comments.getCommentID(),
                        comments.getContent(),
                        dateFormat.format(comments.getTimeCreated().toDate()),
                        comments.getCreator(),
                        comments.getTotalUpVote(),
                        comments.getTotalDownVote(),
                        0,
                        dateFormat.format(comments.getTimeCreated().toDate()),
                        comments.getImage(),
                        null,
                        0
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
        parentComment.setImage(commentParentViewState.getImage());

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
                List<CommentViewState> commentViewStates = cmts.getValue();
                assert commentViewStates != null;
                commentViewStates.add(new CommentViewState(
                        comments.getCommentID(),
                        comments.getContent(),
                        "just now",
                        comments.getCreator(),
                        comments.getTotalUpVote(),
                        comments.getTotalDownVote(),
                        0,
                        "just now",
                        comments.getImage(),
                        null,
                        0
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

            @Override
            public void onGetOnePostSuccess(Post post) {

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

            @Override
            public void onGetOnePostSuccess(Post post) {

            }
        });
    }


    public void loadPost(String postID, String communityID){
        pt_id = postID;
        community_id = communityID;
        postRepository.getOnePost(communityID,postID, new IPostCallback() {

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

            @Override
            public void onGetOnePostSuccess(Post post) {
                PostViewState fetPost = new PostViewState(
                        post.getPostID(),
                        post.getCreator(),
                        post.getCommunityID(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAnonymous(),
                        post.getTimeCreated().toDate().toString(),
                        post.getImage(), post.getTaggedUsers(),
                        post.getCategory(),
                        post.getVoteDifference(),
                        post.getTotalComment()
                );
                fetPost.setPictures(post.getDownloadImage());
                pt_id = post.getPostID();
                community_id = post.getCommunityID();
                currentPost.setValue(fetPost);
            }
        });

    }

    public void loadComments(String postID,String communityID) {
        Post postLoad = new Post();
        postLoad.setPostID(postID);
        postLoad.setCommunityID(communityID);
        commentRepository.loadTopLevelComments(postLoad, new CommentCallback() {
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

    public LiveData<Integer> isVoted(PostViewState postViewState, String userID) {
        Post post = new Post();
        post.setPostID(postViewState.getPostId());
        post.setCommunityID(community_id);

        postRepository.getVoteStatus(post, userID, new IPostCallback() {
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
            public void onGetVoteStatusSuccess(int voteTypeValue) {
                voteType.postValue(voteTypeValue);
            }

            @Override
            public void onGetOnePostSuccess(Post post) {

            }
        });
        return voteType;
    }

    public LiveData<Integer> isVotedComment(CommentViewState commentViewState, String userID, String postID, String communityID) {
        Comment comment = new Comment();
        comment.setCommentID(commentViewState.getCommentID());
        comment.setCommunityID(communityID);
        comment.setPostID(postID);

        commentRepository.getVoteStatus(comment, userID, new CommentCallback() {
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
            public void onGetVoteStatusSuccess(int voteTypeValue) {
                voteType.postValue(voteTypeValue);
            }
        });
        return voteType;
    }
}
