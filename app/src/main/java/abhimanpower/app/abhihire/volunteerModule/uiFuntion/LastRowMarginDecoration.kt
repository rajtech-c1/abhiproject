package abhimanpower.app.abhihire.volunteerModule.uiFuntion

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastRowMarginDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        // Set spacing normally (optional)
        outRect.left = 0
        outRect.right = 0
        outRect.top = 0
        outRect.bottom = 0

        // Calculate number of rows
        val totalRows = Math.ceil(itemCount / spanCount.toDouble()).toInt()
        val currentRow = position / spanCount + 1

        if (currentRow == totalRows) {
            // Last row — add bottom margin
            outRect.bottom = spacing
        }
    }
}
