package studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import studio.eyesthetics.sbdelivery.extensions.dpToPx

class ItemDecorator(
    private val space: Int = 0,
    private var spacePosition: Int = -1
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (spacePosition == -1)
            spacePosition = state.itemCount

        val position = parent.getChildAdapterPosition(view)
        when(position) {
            0 -> {
                outRect.left = 20.dpToPx()
                outRect.right = space
            }
            spacePosition - 1 -> outRect.right = 20.dpToPx()
            else -> outRect.right = space
        }
    }
}