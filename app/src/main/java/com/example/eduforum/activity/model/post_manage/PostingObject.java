package com.example.eduforum.activity.model.post_manage;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public abstract class PostingObject {
    protected String communityID;
    protected String postID;
    protected String content;
    protected Timestamp timeCreated;
    protected Timestamp lastModified;
    protected Creator creator;
    protected Integer totalUpVote;
    protected Integer totalDownVote;
    protected Integer voteDifference;

    protected List<Uri> image;
    protected List<String> downloadImage;

    public PostingObject() {
    }

//    public PostingObject(String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<String> image) {
//        this.content = content;
//        this.timeCreated = timeCreated;
//        this.lastModified = lastModified;
//        this.creator = creator;
//        this.totalUpVote = totalUpVote;
//        this.totalDownVote = totalDownVote;
//        this.voteDifference = voteDifference;
//        this.image = image;
//    }


    public PostingObject(String communityID, String postID, String content, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<Uri> image, List<String> downloadImage) {
        this.communityID = communityID;
        this.postID = postID;
        this.content = content;
        this.timeCreated = timeCreated;
        this.lastModified = lastModified;
        this.creator = creator;
        this.totalUpVote = totalUpVote;
        this.totalDownVote = totalDownVote;
        this.voteDifference = voteDifference;
        this.image = image;
        this.downloadImage = downloadImage;
    }

    public PostingObject(String communityID, String postID, String content, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<Uri> image) {
        this.communityID = communityID;
        this.postID = postID;
        this.content = content;
        this.timeCreated = timeCreated;
        this.lastModified = lastModified;
        this.creator = creator;
        this.totalUpVote = totalUpVote;
        this.totalDownVote = totalDownVote;
        this.voteDifference = voteDifference;
        this.image = image;
    }
    /**
     * @return A list of image URL - use for downloading-caching <strong>remote images</strong> from firebase storage.
     */
    public List<String> getDownloadImage() {
        return downloadImage;
    }
    /**
     * @param downloadImages A list of image URL - use for downloading-caching <strong>remote images</strong> from firebase storage.
     */
    public void setDownloadImage(List<String> downloadImages) {
        this.downloadImage = downloadImages;
    }

    public Integer getVoteDifference() {
        return voteDifference;
    }

    public void setVoteDifference(Integer voteDifference) {
        this.voteDifference = voteDifference;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Integer getTotalUpVote() {
        return totalUpVote;
    }

    public void setTotalUpVote(Integer totalUpVote) {
        this.totalUpVote = totalUpVote;
    }

    public Integer getTotalDownVote() {
        return totalDownVote;
    }

    public void setTotalDownVote(Integer totalDownVote) {
        this.totalDownVote = totalDownVote;
    }
    /**
     * @return A list of local image URI - use for uploading <strong>local images</strong> to firebase storage
     */
    public List<Uri> getImage() {
        return image;
    }
    /**
     * @param image A list of local image URI - use for uploading <strong>local images</strong> to firebase storage
     */

    public void setImage(List<Uri> image) {
        this.image = image;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
