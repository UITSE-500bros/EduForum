package com.example.eduforum.activity.util;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.eduforum.R;

public class LoadingDialog {
    Activity activity;
    android.app.AlertDialog dialog;
    LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    void startLoadingDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
}
