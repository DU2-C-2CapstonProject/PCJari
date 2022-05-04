package com.example.capston.pcjari.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.capston.pcjari.util.retrofit.RetrofitClient
import jp.wasabeef.glide.transformations.CropCircleTransformation

@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(visible: Boolean?) {
    isVisible = visible ?: false
}

@BindingAdapter(value = ["pcIcon"])
fun pcIcon(view: ImageView, url: String) {
    val img_url: String = RetrofitClient.serverUrl + "pc_images/" + url
    Glide.with(view.context)
        .load(img_url)
        .bitmapTransform(CropCircleTransformation(CustomBitmapPool()))
        .into(view)
}