package com.example.githubsearch.adapter

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githubsearch.R
import com.google.android.material.imageview.ShapeableImageView

class ViewBindingAdapter {

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadPhotoFromUrl(view: ShapeableImageView, url: String?) {
            url?.let {
                Glide.with(view.context)
                    .load(it)
                    .placeholder(R.drawable.icongel_person)
                    .error(R.drawable.icongel_error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view)
            }
        }
    }
}