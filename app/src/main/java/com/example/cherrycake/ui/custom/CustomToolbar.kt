package com.example.cherrycake.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.cherrycake.R
import com.example.cherrycake.databinding.LayoutCustomToolbarBinding

@SuppressLint("Recycle")
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : RelativeLayout(context, attrs, defStyleAttrs) {

    lateinit var onClickButtonLeft: () -> Unit
    lateinit var onClickButtonRight: () -> Unit?
    lateinit var onClickBackPress: () -> Unit?

    private var showBackPressButton: Boolean? = null
    private var titleToolbar: String? = null
    private var iconRight: Int? = null
    private var iconLeft: Int? = null
    private var textDone: String? = null

    private val binding: LayoutCustomToolbarBinding = LayoutCustomToolbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)

        showBackPressButton = typedArray.getBoolean(R.styleable.CustomToolbar_showBackPressButton, true)
        titleToolbar = typedArray.getString(R.styleable.CustomToolbar_titleToolbar)
        textDone = typedArray.getString(R.styleable.CustomToolbar_textDone)
        iconLeft = typedArray.getResourceId(R.styleable.CustomToolbar_iconLeft, 0)
        iconRight = typedArray.getResourceId(R.styleable.CustomToolbar_iconRight, 0)

        setTitle(titleToolbar)
        setUpBackPressButton(showBackPressButton!!)
        setUpIconRight(iconRight!!)
        setUpIconLeft(iconLeft!!)
        setUpTextDone(textDone)
    }

    private fun setUpTextDone(textDone: String?) {
        if (textDone.isNullOrEmpty()) {
            binding.apply {
                icLeft.visibility = View.VISIBLE
                icRight.visibility = View.VISIBLE
                tvDone.visibility = View.GONE
            }
        } else {
            binding.apply {
                icLeft.visibility = View.GONE
                icRight.visibility = View.GONE
                tvDone.apply {
                    visibility = View.VISIBLE
                    text = textDone
                }
            }
        }
    }

    private fun setUpIconRight(iconRight: Int) {
        if (iconRight == 0) {
            binding.icRight.visibility = View.GONE
        } else {
            binding.icRight.apply {
                visibility = View.VISIBLE
                setImageDrawable(ContextCompat.getDrawable(context, iconRight))
                setOnClickListener {
                    if (this@CustomToolbar::onClickButtonRight.isInitialized) {
                        onClickButtonRight.invoke()
                    }
                }
            }
        }
    }

    private fun setUpIconLeft(iconLeft: Int) {
        if (iconLeft == 0) {
            binding.icLeft.visibility = View.GONE
        } else {
            binding.icLeft.apply {
                visibility = View.VISIBLE
                setImageDrawable(ContextCompat.getDrawable(context, iconLeft))
                setOnClickListener {
                    if (this@CustomToolbar::onClickButtonLeft.isInitialized) {
                        onClickButtonLeft.invoke()
                    }
                }
            }
        }
    }

    private fun setUpBackPressButton(visible: Boolean) {
        binding.ivBack.apply {
            visibility = if (visible) View.VISIBLE else View.GONE
            setOnClickListener {
                if (this@CustomToolbar::onClickBackPress.isInitialized) {
                    onClickBackPress.invoke()
                }
            }
        }
    }

    private fun setTitle(titleToolbar: String?) {
        if (titleToolbar.isNullOrEmpty()) {
            binding.tvTitle.visibility = View.GONE
        } else {
            binding.tvTitle.apply {
                visibility = View.VISIBLE
                text = titleToolbar
            }
        }
    }
}