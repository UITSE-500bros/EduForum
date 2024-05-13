package com.example.eduforum.activity.model.post_manage;

import java.util.List;

public abstract class PostingObject {
    protected String content;
    protected String timeCreated;
    protected String lastModified;
    protected Creator creator;
    protected Integer totalUpVote;
    protected Integer totalDownVote;
    protected List<String> image;

    public PostingObject() {
    }

    public PostingObject(String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, List<String> image) {
        this.content = content;
        this.timeCreated = timeCreated;
        this.lastModified = lastModified;
        this.creator = creator;
        this.totalUpVote = totalUpVote;
        this.totalDownVote = totalDownVote;
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
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

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
