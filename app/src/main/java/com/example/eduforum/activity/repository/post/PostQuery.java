package com.example.eduforum.activity.repository.post;

public class PostQuery {
    private boolean newest;
    private boolean oldest;
    private boolean mostVoted;
    private boolean mostCommented;

    public PostQuery() {
        resetQuery();
    }

    private void resetQuery() {
        this.newest = false;
        this.oldest = false;
        this.mostVoted = false;
        this.mostCommented = false;
    }

    public boolean isNewest() {
        return newest;
    }

    public void setNewest(boolean newest) {
        resetQuery();
        this.newest = newest;
    }

    public boolean isOldest() {
        return oldest;
    }

    public void setOldest(boolean oldest) {
        resetQuery();
        this.oldest = oldest;
    }

    public boolean isMostVoted() {
        return mostVoted;
    }

    public void setMostVoted(boolean mostVoted) {
        resetQuery();
        this.mostVoted = mostVoted;
    }

    public boolean isMostCommented() {
        return mostCommented;
    }

    public void setMostCommented(boolean mostCommented) {
        resetQuery();
        this.mostCommented = mostCommented;
    }
}
