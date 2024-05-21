package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityAdminMemberListBinding;
import com.google.android.material.appbar.MaterialToolbar;
import  androidx.appcompat.widget.SearchView;

public class AdminMemberListActivity extends AppCompatActivity {
    private ActivityAdminMemberListBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAdminMemberListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // This should be the only call to setContentView

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup recyclerView
        RecyclerView adminMemberListRecyclerView = binding.AdminrecyclerView;
        adminMemberListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminMemberListRecyclerView.setHasFixedSize(true);





    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Tìm kiếm thành viên");

        ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text)).setTextColor(getResources().getColor((R.color.white)));


//        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // filter(newText);
//                return true;
//            }
//        });

        return true;
    }
}