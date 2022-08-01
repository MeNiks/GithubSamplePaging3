package com.niks.githubapp.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

object GlideImageLoader {

    fun loadImage(
        imageView: ImageView?,
        imageUri: String?,
        placeholder: Int? = null,
        cornerRadiusInPixel: Int? = null
    ) {
        imageView ?: return
        imageUri ?: return
        val request = Glide
            .with(imageView.context)
            .load(imageUri)
        if (placeholder != null)
            request.placeholder(placeholder)
        if (cornerRadiusInPixel != null) {
            request.apply(
                bitmapTransform(
                    RoundedCornersTransformation(
                        cornerRadiusInPixel,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
            )
        }
        request.into(imageView)
    }

}