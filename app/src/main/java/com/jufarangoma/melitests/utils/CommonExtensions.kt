package com.jufarangoma.melitests.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jufarangoma.melitests.R
import java.text.DecimalFormat
import java.util.Currency

fun Double?.isNullOrEmpty(): Boolean {
    return if (this == null) {
        true
    } else {
        this == 0.0
    }
}

fun Double?.toMoney(): String {
    val format = DecimalFormat()
    val currencyInstance = Currency.getInstance("USD")
    format.apply {
        maximumFractionDigits = 0
        currency = currencyInstance
    }

    return currencyInstance.symbol + format.format(this)
}

fun ImageView.setImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.im_placeholder)
        .into(this)
}
