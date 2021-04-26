package studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import studio.eyesthetics.sbdelivery.extensions.dpToPx

class VerticalItemDecorator(
    private val space: Int = DEFAULT_BOTTOM_MARGIN,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0 || position == 1) {
            outRect.top = DEFAULT_TOP_MARGIN.dpToPx()
        }
        outRect.bottom = space.dpToPx()
    }

    companion object {
        private const val DEFAULT_TOP_MARGIN = 38
        private const val DEFAULT_BOTTOM_MARGIN = 20
    }
}