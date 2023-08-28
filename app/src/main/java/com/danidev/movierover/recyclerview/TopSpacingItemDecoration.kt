package com.danidev.movierover.recyclerview

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This class represents margins between the elements in the [RecyclerView]. `px`s are converted to
 * `dp` automatically, so that the values in `dp` are passed right to the constructor.
 *
 * @property paddingInDp is [Int] padding value in `dp`.
 */
class TopSpacingItemDecoration (private val paddingInDp: Int): RecyclerView.ItemDecoration() {

    /**
     * Value to convert `px` to `dp` using Extension Function.
     */
    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    /**
     * Retrieves any offsets for the given item.
     *
     * @param outRect is [Rect] object holding the coordinates of the rectangle, through which the
     * output can be received.
     * @param view is the child [View] to decorate.
     * @param parent is [RecyclerView] that this ItemDecoration is decorating.
     * @param state is the current state of [RecyclerView].
     */
    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = paddingInDp.convertPx
        outRect.right = paddingInDp.convertPx
        outRect.left = paddingInDp.convertPx
    }
}