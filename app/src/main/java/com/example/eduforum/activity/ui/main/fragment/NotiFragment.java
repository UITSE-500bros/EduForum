package com.example.eduforum.activity.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.noti_manage.Notification;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.main.adapter.ExploreCommunityAdapter;
import com.example.eduforum.activity.ui.main.adapter.NotificationAdapter;
import com.example.eduforum.activity.viewmodel.main.NotificationViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.FragmentNotiBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotiFragment extends Fragment {

    private FragmentNotiBinding binding;
    private NotificationViewModel viewModel;
    private UserViewModel userViewModel;
    private NotificationAdapter adapter;


    public NotiFragment() {
        // Required empty public constructor
    }
    
    public static NotiFragment newInstance(String param1, String param2) {
        NotiFragment fragment = new NotiFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding = FragmentNotiBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        EduForum app = (EduForum) getActivity().getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
                viewModel.setCurrentUser(user);
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotificationAdapter(viewModel.getNotificationList().getValue());
        viewModel.setupListener(userViewModel);
        viewModel.getNotificationList().observe(getViewLifecycleOwner(), notifications -> {

            adapter.setNotificationList(notifications);
        });
        viewModel.getNotificationList();

        binding.notiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notiRecyclerView.setAdapter(adapter);

        binding.readAllButton.setOnClickListener(v -> {
            viewModel.markAllAsRead();
        });
    }

}