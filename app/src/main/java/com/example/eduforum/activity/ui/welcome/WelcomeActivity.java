package com.example.eduforum.activity.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.auth.LoginActivity;
import com.example.eduforum.databinding.ActivityWelcomeBinding;
import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;

    ViewPageAdapter viewPageAdapter;
    TextView [] dots;

    ViewPager.OnPageChangeListener viewOnPageLisner = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position){
            setDotIndicator(position);
            if(position < 3){
                binding.nextButton.setText("Tiếp theo");
            }
            else {
                binding.nextButton.setText("Kết thúc");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        viewPageAdapter = new ViewPageAdapter(this);

        binding.slideViewPager.setAdapter(viewPageAdapter);
        binding.slideViewPager.addOnPageChangeListener(viewOnPageLisner);
        setDotIndicator(0);



        binding.skipWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = binding.slideViewPager.getCurrentItem();
                if(currentPage < 3){
                    binding.slideViewPager.setCurrentItem(getItem(1), true);
                }
                else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

    }
    public void setDotIndicator(int position){
        dots = new TextView[4];
        binding.dotIdicator.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            //&#8226 tu Html la ky hieu cham tron. cham tron nay hien thi tren man hinh cho biet ta dang o trang nao
            dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray, getApplication().getTheme()));
            binding.dotIdicator.addView(dots[i]);
        }
        // mau trang cua cac cham la mau trang hien tai theo vi tri position
        dots[position].setTextColor(getResources().getColor(R.color.white, getApplication().getTheme()));
    }
    public int getItem(int i){
        return  binding.slideViewPager.getCurrentItem() + i;
    }
}