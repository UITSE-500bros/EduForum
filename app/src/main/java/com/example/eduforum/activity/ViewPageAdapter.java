package com.example.eduforum.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.eduforum.R;

public class ViewPageAdapter extends PagerAdapter {

    Context context;
    //thiet lap cac hinh anh va noi dung cho slide bang mang
    int [] slideImages = new int[] {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3};
    int [] slideTitles = new int[] {R.string.screen1, R.string.screen2, R.string.screen3};
    int [] slideDescriptions = new int[] {R.string.desscreen1, R.string.desscreen2, R.string.desscreen3};

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slideImages.length + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container la viewgroup chua cac trang trong slider
        //position la vi tri cua trang hien tai
        // LayoutInflater: dung de chuyen doi file xml thanh 1 doi tuong view, tham chieu den layout can dung
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if(position == 0){
            view = layoutInflater.inflate(R.layout.slider_first_screen, container, false);
        }
        else {
            view = layoutInflater.inflate(R.layout.slider_screen, container, false);
            ImageView imageView = view.findViewById(R.id.sliderImageView);
            TextView titleTv = view.findViewById(R.id.sliderTitleTextView);
            TextView descriptionTv = view.findViewById(R.id.sliderDescriptionTextView);
            //set hinh anh va noi dung cho slide, boi vi 3 slide tiep theo tu slide dau tien chung 1 layout
            // ma no bat dau tu index 1 toi 4 nen ta phai tru 1 de lay id trong mang
            imageView.setImageResource(slideImages[position - 1]);
            titleTv.setText(slideTitles[position - 1]);
            descriptionTv.setText(slideDescriptions[position - 1]);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
