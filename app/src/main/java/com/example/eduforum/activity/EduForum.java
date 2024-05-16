package com.example.eduforum.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class EduForum extends Application implements ViewModelStoreOwner {
    private ViewModelStore viewModelStore = new ViewModelStore();
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelProvider viewModelProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this);
        viewModelProvider = new ViewModelProvider(this, viewModelFactory);
    }
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }

    public <T extends ViewModel> T getSharedViewModel(Class<T> modelClass) {
        return viewModelProvider.get(modelClass);
    }
}
