package com.jufarangoma.melitests.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jufarangoma.melitests.R

fun ImageView.setImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.im_placeholder)
        .into(this)
}
