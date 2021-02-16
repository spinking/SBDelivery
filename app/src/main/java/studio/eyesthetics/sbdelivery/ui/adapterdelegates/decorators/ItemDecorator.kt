package studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

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
        if (position < spacePosition)
            outRect.right = space
    }
}