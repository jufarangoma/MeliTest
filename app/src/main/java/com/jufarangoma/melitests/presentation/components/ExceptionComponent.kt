package com.jufarangoma.melitests.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jufarangoma.melitests.databinding.LayoutExceptionBinding

class ExceptionComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleSet: Int = 0
) : ConstraintLayout(context, attrs, defStyleSet) {

    private val binding = LayoutExceptionBinding.inflate(LayoutInflater.from(context), this, true)

    fun setView(title: String, description: String? = null) {
        binding.txvTitleException.text = title
        binding.txvSubtitleException.text = description
        isVisible = true
    }
}
