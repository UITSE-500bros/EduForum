package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.post.PostRepository;

// TODO: Nam lam cai nay ne Nam
public class PostDetailsViewModel extends ViewModel {
    PostRepository postRepository;
    public PostDetailsViewModel() {
        postRepository = PostRepository.getInstance();
        
    }


}
