package com.example.eduforum.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eduforum.R;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    //dotIndicator la layout chua cac cham tron bieu thi vi tri cua trang hien tai
    LinearLayout dotIndicator;
    // viewPageAdapter la adapter dung de do noi dung vao cac trang trong slider
    ViewPageAdapter viewPageAdapter;
    Button nextButton, skipButton;
    TextView [] dots;

    ViewPager.OnPageChangeListener viewOnPageLisner = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position){
            setDotIndicator(position);
            if(position < 3){
                nextButton.setText("Next");
            }
            else {
                    nextButton.setText("Finish");
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

        skipButton = findViewById(R.id.skipWelcomeButton);
        nextButton = findViewById(R.id.nextButton);
        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIdicator);
        viewPageAdapter = new ViewPageAdapter(this);

        slideViewPager.setAdapter(viewPageAdapter);
        slideViewPager.addOnPageChangeListener(viewOnPageLisner);
        setDotIndicator(0);



        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                //finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = slideViewPager.getCurrentItem();
                if(currentPage < 3){
                    slideViewPager.setCurrentItem(getItem(1), true);
                }
                else {
                    //startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    //finish();
                }
            }
        });

    }
    public void setDotIndicator(int position){
        dots = new TextView[4];
        dotIndicator.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            //&#8226 tu Html la ky hieu cham tron. cham tron nay hien thi tren man hinh cho biet ta dang o trang nao
            dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray, getApplication().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        // mau trang cua cac cham la mau trang hien tai theo vi tri position
        dots[position].setTextColor(getResources().getColor(R.color.white, getApplication().getTheme()));
    }
    public int getItem(int i){
        return slideViewPager.getCurrentItem() + i;
    }
}