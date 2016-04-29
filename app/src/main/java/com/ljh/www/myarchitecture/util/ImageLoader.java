package com.ljh.www.myarchitecture.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ljh.www.myarchitecture.R;

/**
 * Created by ljh on 2016/4/29.
 */
public class ImageLoader {

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable error) {
        imageView.setImageResource(R.mipmap.ic_launcher);

    }
}
