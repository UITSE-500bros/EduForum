package com.example.eduforum.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.view.View;

import com.example.eduforum.R;

public class WelcomeActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}