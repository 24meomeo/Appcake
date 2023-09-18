package com.example.cherrycake.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.cherrycake.R
import com.example.cherrycake.databinding.ItemAppColorBinding
import com.example.cherrycake.databinding.LayoutColorSelectBinding
import com.example.cherrycake.utils.Constants

class ColorSelectionSectionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding = LayoutColorSelectBinding.inflate(LayoutInflater.from(context), this, true)
    private val colorAdapter = ColorAdapter(Constants.DEFAULT_COLORS) {
        it?.let { color ->
            listener?.onColorSelected(this@ColorSelectionSectionView, color)
        } ?: run {
            listener?.onOtherColorSelected(this@ColorSelectionSectionView)
        }
    }
    var listener: OnColorSelectedChangedListener? = null

    init {
        if (attributeSet != null) {
            val attrs = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ColorSelectionSectionView,
                defStyleAttr,
                0
            )

            binding.tvSectionNumber.text =
                attrs.getInt(
                    R.styleable.ColorSelectionSectionView_index,
                    1
                ).toString()

            binding.tvSectionTitle.text =
                attrs.getString(
                    R.styleable.ColorSelectionSectionView_android_title,
                )

            attrs.recycle()
        }
        with(binding) {
            rvColors.adapter = colorAdapter
        }
    }

    interface OnColorSelectedChangedListener {
        fun onColorSelected(view: ColorSelectionSectionView, color: Int)
        fun onOtherColorSelected(view: ColorSelectionSectionView)
    }

    fun setSelectedColor(color:Int) {
        colorAdapter.selectedColor = color
    }
    fun getSelectedColor() = colorAdapter.selectedColor
}

class ColorAdapter(private val colors: IntArray, private val onColorSelected: (Int?) -> Unit) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    var selectedColor: Int = Constants.DEFAULT_COLORS[0]
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            ItemAppColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors.getOrNull(position))
    }

    override fun getItemCount() = colors.size + 1

    inner class ColorViewHolder(private val binding: ItemAppColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onColorSelected.invoke(colors.getOrNull(layoutPosition))
            }
        }

        fun bind(color: Int?) {
            with(binding) {
                color?.let {
                    defaultColor.visibility = View.VISIBLE
                    containerOtherColor.visibility = View.GONE
                    binding.defaultColor.setColorFilter(it)
                    if (selectedColor == it) {
                        binding.icSelected.visibility = View.VISIBLE
                    } else {
                        binding.icSelected.visibility = View.GONE
                    }
                } ?: run {
                    defaultColor.visibility = View.GONE
                    containerOtherColor.visibility = View.VISIBLE
                    if (!colors.any { it == selectedColor }) {
                        binding.icSelected.visibility = View.VISIBLE
                        otherColor.visibility = View.VISIBLE
                        otherColor.setColorFilter(selectedColor)
                    } else {
                        binding.icSelected.visibility = View.GONE
                        otherColor.visibility = View.GONE
                    }
                }
            }
        }
    }
}
