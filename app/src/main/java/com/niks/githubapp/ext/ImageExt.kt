package com.niks.githubapp.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.niks.githubapp.R
import com.niks.githubapp.image.GlideImageLoader

object ImageExt {
    @DrawableRes
    @JvmStatic
    fun getDefaultUserImage(): Int {
        return R.drawable.ic_user_default_thumb
    }

}

@BindingAdapter("android:src", "placeholder")
fun ImageView.setImageUri(imageUri: String?, placeholder: Int) {
    GlideImageLoader
        .loadImage(imageView = this, imageUri = imageUri, placeholder = placeholder)
}

