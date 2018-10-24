package com.nekodev.rocketbrowser.util

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemOffsetDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes offset: Int) : this(context.resources.getDimension(offset).toInt())

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(offset, offset, offset, offset)
    }
}