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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotiFragment newInstance(String param1, String param2) {
        NotiFragment fragment = new NotiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        viewModel.getNotificationList().observe(getViewLifecycleOwner(), notifications -> {
            adapter.setNotificationList(notifications);
        });
        viewModel.getNotificationList();

        RecyclerView recyclerView = binding.notiRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.readAllButton.setOnClickListener(v -> {
            viewModel.markAllAsRead();

        });
    }



}