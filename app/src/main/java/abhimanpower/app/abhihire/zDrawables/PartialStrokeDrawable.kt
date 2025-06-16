package abhimanpower.app.abhihire.zDrawables
import android.graphics.*
import android.graphics.drawable.Drawable

class CustomCornerStrokeDrawable(
    private val backgroundColor: Int = Color.CYAN,
    private val strokeColor: Int = Color.BLACK,
    private val strokeWidth: Float = 4f
) : Drawable() {

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = backgroundColor
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = strokeColor
        strokeWidth = this@CustomCornerStrokeDrawable.strokeWidth
    }

    private val fillPath = Path()
    private val strokePath = Path()
    private var pathDirty = true

    private var cornerRadii = FloatArray(8) { 0f }
    private var showBorders = booleanArrayOf(true, true, true, true) // left, top, right, bottom

    fun setCornerRadius(
        topLeft: Float = 0f,
        topRight: Float = 0f,
        bottomRight: Float = 0f,
        bottomLeft: Float = 0f
    ) {
        cornerRadii = floatArrayOf(
            topLeft, topLeft,
            topRight, topRight,
            bottomRight, bottomRight,
            bottomLeft, bottomLeft
        )
        pathDirty = true
        invalidateSelf()
    }

    fun setBordersVisibility(
        showLeft: Boolean = true,
        showTop: Boolean = true,
        showRight: Boolean = true,
        showBottom: Boolean = true
    ) {
        showBorders = booleanArrayOf(showLeft, showTop, showRight, showBottom)
        pathDirty = true
        invalidateSelf()
    }

    private fun updatePaths() {
        if (!pathDirty) return

        val rect = RectF(bounds).apply {
            inset(strokeWidth / 2, strokeWidth / 2)
        }

        // Create fill path (always full rounded rect)
        fillPath.reset()
        fillPath.addRoundRect(rect, cornerRadii, Path.Direction.CW)

        // Create stroke path (only showing selected borders)
        strokePath.reset()

        // Create a rounded rectangle path first
        val tempPath = Path()
        tempPath.addRoundRect(rect, cornerRadii, Path.Direction.CW)

        // Measure the path to get points
        val pathMeasure = PathMeasure(tempPath, false)
        val coords = FloatArray(2)

        // We'll break the path into segments and only draw the ones we want
        val segmentLength = 0.1f // Small enough to capture all corners
        var distance = 0f

        while (distance < pathMeasure.length) {
            pathMeasure.getPosTan(distance, coords, null)

            // Determine which segment we're in
            val isLeftBorder = coords[0] <= rect.left + strokeWidth
            val isTopBorder = coords[1] <= rect.top + strokeWidth
            val isRightBorder = coords[0] >= rect.right - strokeWidth
            val isBottomBorder = coords[1] >= rect.bottom - strokeWidth

            // Only move to the point if we're starting a new segment
            if (distance == 0f) {
                strokePath.moveTo(coords[0], coords[1])
            }

            // Only draw the line if it's part of a visible border
            when {
                isLeftBorder && showBorders[0] -> strokePath.lineTo(coords[0], coords[1])
                isTopBorder && showBorders[1] -> strokePath.lineTo(coords[0], coords[1])
                isRightBorder && showBorders[2] -> strokePath.lineTo(coords[0], coords[1])
                isBottomBorder && showBorders[3] -> strokePath.lineTo(coords[0], coords[1])
                else -> strokePath.moveTo(coords[0], coords[1])
            }

            distance += segmentLength
        }

        pathDirty = false
    }

    override fun draw(canvas: Canvas) {
        updatePaths()
        canvas.drawPath(fillPath, fillPaint)
        canvas.drawPath(strokePath, strokePaint)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        pathDirty = true
    }

    override fun setAlpha(alpha: Int) {
        fillPaint.alpha = alpha
        strokePaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        fillPaint.colorFilter = colorFilter
        strokePaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}