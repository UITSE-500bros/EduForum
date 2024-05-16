package com.example.eduforum.activity.ui.community.adapter;

import android.net.Uri;

public class MediaItem {
    private Uri uri;
    private boolean isVideo;

    public MediaItem(Uri uri, boolean isVideo) {
        this.uri = uri;
        this.isVideo = isVideo;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean isVideo() {
        return isVideo;
    }
}
