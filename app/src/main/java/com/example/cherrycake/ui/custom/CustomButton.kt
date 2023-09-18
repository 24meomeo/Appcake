package com.example.cherrycake.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.example.cherrycake.R
import com.example.cherrycake.databinding.ViewButtonBinding
import com.example.cherrycake.utils.ColorUtils
import com.google.android.material.card.MaterialCardView


@SuppressLint("ResourceType")
class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding = ViewButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var enabledCardColor: ColorStateList? = null
    private var enabledTextColor: Int = Color.WHITE

    init {
        cardElevation = 0f
        radius = context.resources.getDimensionPixelSize(R.dimen.button_corner_radius).toFloat()
        setCardBackgroundColor(Color.TRANSPARENT)
        binding.root.setBackgroundColor(Color.WHITE)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            0, 0
        ).apply {
            try {
                binding.buttonTitle.text =
                    context.getString(R.styleable.CustomButton_android_title) ?: ""
                enabledTextColor = getColor(
                    R.styleable.CustomButton_android_textColor,
                    getColor(context, R.color.white)
                )
                binding.buttonTitle.setTextColor(enabledTextColor)
                getDrawable(R.styleable.CustomButton_android_icon)?.let {
                    binding.buttonIcon.setImageDrawable(it)
                } ?: run {
                    binding.buttonIcon.visibility = GONE
                }
                enabledCardColor = ColorUtils.getBackgroundColorTintList(
                    getColor(
                        R.styleable.CustomButton_backgroundColor,
                        getColor(context, R.color.colorButton)
                    )
                )
                binding.root.backgroundTintList = enabledCardColor
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        binding.buttonTitle.text = title
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            binding.root.setBackgroundColor(Color.WHITE)
            binding.root.backgroundTintList = enabledCardColor
            binding.buttonTitle.setTextColor(enabledTextColor)
            strokeWidth = 0
        } else {
            strokeWidth = resources.getDimensionPixelSize(R.dimen.divider_height)
            setStrokeColor(
                ColorUtils.getBackgroundColorTintList(
                    getColor(
                        context,
                        R.color.grey800
                    )
                )
            )
            binding.root.setBackgroundColor(Color.TRANSPARENT)
            binding.buttonTitle.setTextColor(getColor(context, R.color.grey800))
        }

    }

    override fun setCardBackgroundColor(color: Int) {
        if (isEnabled) {
            binding.root.backgroundTintList = ColorUtils.getBackgroundColorTintList(color)
        } else {
            enabledCardColor = ColorUtils.getBackgroundColorTintList(color)
        }
    }

    override fun setCardBackgroundColor(color: ColorStateList?) {
        if (isEnabled) {
            binding.root.backgroundTintList = color
        } else {
            enabledCardColor = color
        }
    }
}
