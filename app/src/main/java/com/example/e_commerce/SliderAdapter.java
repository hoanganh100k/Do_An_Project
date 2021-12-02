package com.example.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lib.Model.SliderModel;

import java.util.List;

public class SliderAdapter  extends PagerAdapter {
    private List<SliderModel> sliderModelsList;

    public SliderAdapter(List<SliderModel> sliderModelsList) {
        this.sliderModelsList = sliderModelsList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container,false);
        ImageView banner = view.findViewById(R.id.banner_slider);
        Glide.with(container.getContext()).load(sliderModelsList.get(position).getBanner()).apply(new RequestOptions().placeholder(R.mipmap.ic_logo_trans_round)).into(banner);
        container.addView(view,0);
        return view;
    }

    //Xóa 1 page
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    //Trả về số lượng view trong viewpager
    @Override
    public int getCount() {
        if(sliderModelsList != null)
            return sliderModelsList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
