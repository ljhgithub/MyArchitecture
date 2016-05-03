package com.ljh.www.myarchitecture.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ljh on 2016/4/29.
 */
public class ImageLoader {
    @BindingAdapter({"bind:imageUrl","bind:imageError"})
    public static void loadImage(ImageView imageView, String url,Drawable error) {
        Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .placeholder(error)
                .into(imageView);
    }
}
