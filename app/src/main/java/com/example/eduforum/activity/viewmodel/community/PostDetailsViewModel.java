package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.repository.post.PostRepository;

// TODO: Nam lam cai nay ne Nam
public class PostDetailsViewModel extends ViewModel {
    PostRepository postRepository;

    MutableLiveData<Post> post;
    public PostDetailsViewModel() {
        postRepository = PostRepository.getInstance();
        post = new MutableLiveData<>();
        //postId = new MutableLiveData<>();
//        postRepository.queryPost(postId).observeForever(new Observer<Post>() {
//            @Override
//            public void onChanged(Post post) {
//                //post.setValue(post);
//            }
//        });
    }


}
