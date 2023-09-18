package com.example.thietkebanh.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridSpacingItemDecoration(private val spanCount: Int, private val spacingVertical: Int, private val spacingHorizontal : Int, private val includeEdge: Boolean) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (includeEdge) {
            outRect.left =
                spacingHorizontal - column * spacingHorizontal / spanCount
            outRect.right =
                (column + 1) * spacingHorizontal / spanCount
            if (position < spanCount) {
                outRect.top = spacingVertical
            }
            outRect.bottom = spacingVertical
        } else {
            outRect.left = column * spacingHorizontal / spanCount
            outRect.right =
                spacingHorizontal - (column + 1) * spacingHorizontal / spanCount
            if (position >= spanCount) {
                outRect.top = spacingVertical
            }
        }
    }

}