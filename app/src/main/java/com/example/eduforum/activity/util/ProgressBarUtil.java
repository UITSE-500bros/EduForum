package com.example.eduforum.activity.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduforum.R;

public class ProgressBarUtil {
    private static View progressBarView;

    // Show a progress bar on the screen

    public static void showProgressBar(Activity activity) {
        ViewGroup layout = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
        progressBarView = LayoutInflater.from(activity).inflate(R.layout.progress_bar, layout, false);
        layout.addView(progressBarView);
    }

// Hide the progress bar from the screen
    public static void hideProgressBar() {
        if (progressBarView != null) {
            ViewGroup parent = (ViewGroup) progressBarView.getParent();
            if (parent != null) {
                parent.removeView(progressBarView);
            }
            progressBarView = null;
        }
    }
}