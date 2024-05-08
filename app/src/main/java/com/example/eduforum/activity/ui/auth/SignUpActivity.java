package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.viewmodel.auth.SignUpViewModel;
import com.example.eduforum.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {
    private SignUpViewModel viewModel;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        String[] genderItems = getResources().getStringArray(R.array.gioiTinh);
        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genderItems);
        binding.ACTVGioiTinh.setAdapter(adapterGioiTinh);

        binding.ACTVGioiTinh.setOnItemClickListener((parent, view, position, id) -> {
            String selectedGender = adapterGioiTinh.getItem(position);
            viewModel.setSelectedGender(selectedGender);
        });

        String[] departmentItems = getResources().getStringArray(R.array.ds_khoa);
        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, departmentItems);
        binding.ACTVKhoa.setAdapter(adapterDepartment);

        binding.ACTVKhoa.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDepartment = adapterDepartment.getItem(position);
            viewModel.setSelectedDepartment(selectedDepartment);
        });

        // navigation

        viewModel.getNavigateToEmailVerification().observe(this, shouldNavigate -> {
            if (shouldNavigate) {
                Intent intent = new Intent(this, VerificationWaitingActivity.class);
                startActivity(intent);
            }
        });
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        });

        // handle when user click on the avatar image view

        // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        SignUpViewState state = viewModel.getUser().getValue();
                        assert state != null;
                        state.setProfilePicture(uri);
                        binding.getViewModel().setUser(state);
                        binding.avatarIView.setImageURI(uri);
                    } else {
//                        TODO: Show errors
                    }
                });

        binding.avatarIView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
//                if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    mGetContent.launch("image/*");
//                } else {
//                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
//                }
            }
        });

//        mGetContent = registerForActivityResult(
//                new ActivityResultContracts.GetContent(),
//                new ActivityResultCallback<Uri>() {
//                    @Override
//                    public void onActivityResult(Uri uri) {
//                        // Returned Uri when Úser selects an image from the gallery
//
//                        if(uri != null){
//                            selectedImageUri = uri;
//                            binding.avatarIView.setImageURI(uri);
//                        }
//                        else {
//                            binding.avatarIView.setImageResource(R.drawable.user_ava_default);
//                        }
//                    }
//                });


//        ArrayAdapter<Topic> adapterKhoa = new ArrayAdapter<Topic>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Topic>()) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView textView = (TextView) view.findViewById(android.R.id.text1);
//                Topic item = (Topic) getItem(position);  // Explicitly casting the returned Object to Topic
//                if (item != null) {
//                    textView.setText(item.getName());  // Displaying only the name
//                }
//                return view;
//            }
//        };
//        binding.ACTVKhoa.setAdapter(adapterKhoa);
//
//        viewModel.getDepartmentList().observe(this, departments -> {
//            adapterKhoa.clear();
//            adapterKhoa.addAll(departments);
//        });
//
//        binding.ACTVKhoa.setOnItemClickListener((parent, view, position, id) -> {
//            Topic selectedDepartment = adapterKhoa.getItem(position);
//            if (selectedDepartment != null) {
//                viewModel.setSelectedDepartmentId(selectedDepartment.getId());
//            }
//        });
    }

}