package com.example.eduforum.activity.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.Topic;
import com.example.eduforum.activity.viewmodel.auth.SignUpViewModel;
import com.example.eduforum.databinding.ActivitySignUpBinding;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel viewModel;
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        String[] items = getResources().getStringArray(R.array.gioiTinh);
        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items);
        binding.ACTVGioiTinh.setAdapter(adapterGioiTinh);

        binding.ACTVGioiTinh.setOnItemClickListener((parent, view, position, id) -> {
            String selectedGender = adapterGioiTinh.getItem(position);
            // update the ViewModel
            viewModel.setSelectedGender(selectedGender);
        });
        ArrayAdapter<Topic> adapterKhoa = new ArrayAdapter<Topic>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Topic>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                Topic item = (Topic) getItem(position);  // Explicitly casting the returned Object to Topic
                if (item != null) {
                    textView.setText(item.getName());  // Displaying only the name
                }
                return view;
            }
        };
        binding.ACTVKhoa.setAdapter(adapterKhoa);

        viewModel.getDepartmentList().observe(this, departments -> {
            adapterKhoa.clear();
            adapterKhoa.addAll(departments);
        });

        binding.ACTVKhoa.setOnItemClickListener((parent, view, position, id) -> {
            Topic selectedDepartment = adapterKhoa.getItem(position);
            if (selectedDepartment != null) {
                viewModel.setSelectedDepartmentId(selectedDepartment.getId());
            }
        });


        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }
}