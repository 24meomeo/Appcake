package com.example.cherrycake.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.cherrycake.R
import com.example.cherrycake.databinding.ViewActionToolbarBinding

class ActionToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    val binding = ViewActionToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    var onToolbarClick : OnToolbarClick? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ActionToolbar,
            0, 0
        ).apply {
            try {
                binding.tvTitle.text = getString(R.styleable.ActionToolbar_android_title) ?: ""
                binding.leftButton.text = getString(R.styleable.ActionToolbar_leftMenuTitle) ?: ""
                binding.rightButton.text = getString(R.styleable.ActionToolbar_rightMenuTitle) ?: ""
            } finally {
                recycle()
            }
        }
        binding.leftButton.setOnClickListener {
            onToolbarClick?.onLeftClick()
        }
        binding.rightButton.setOnClickListener {
            onToolbarClick?.onRightClick()
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setLeftMenuButtonTitle(title: String) {
        binding.leftButton.text = title
    }

    fun setRightMenuButtonTitle(title: String) {
        binding.rightButton.text = title
    }

    interface OnToolbarClick {
        fun onLeftClick()
        fun onRightClick()
    }
}
