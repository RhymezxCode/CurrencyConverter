package com.project.currencyconverter.data.util

/**
 * Created by Awodire Babajide samuel on 15/02/2022.
 */
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.AttrRes

class CurveGraphDrawable : View {

    val path = Path()
    var height = 0.0f
    var width = 0.0f
    var length = 0f

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        super.onSizeChanged(xNew, yNew, xOld, yOld)
        width = xNew.toFloat()
        height = yNew.toFloat()
        init()
    }

    fun init() {
        val radius = 8f
        val baseY = 18f
        height -= baseY
        val sides = width / 10

        // draw line
        path.moveTo(0F, baseY)
        path.lineTo(sides.toFloat(), baseY)

        // draw curve
        path.cubicTo(
            (sides).toFloat(),
            baseY,
            (sides * 3).toFloat(),
            (30).toFloat(),
            (sides * 3.9).toFloat(),
            (height / 2).toFloat()
        )

        // draw curve
        path.cubicTo(
            (sides * 3.9).toFloat(),
            (height / 2).toFloat(), (sides * 4.5).toFloat(), (height - 34f).toFloat(),
            (sides * 6.5).toFloat(),
            height.toFloat()
        )

        path.lineTo(
            width.toFloat(), height.toFloat()
        )

        // draw point
        path.moveTo(sides.toFloat(), baseY)
        path.addCircle(sides.toFloat(), baseY, radius, Path.Direction.CW)

        path.moveTo((width - sides).toFloat(), height.toFloat())
        path.addCircle((width - sides).toFloat(), height.toFloat(), radius, Path.Direction.CW)

        path.moveTo((width - sides * 3).toFloat(), height.toFloat())
        path.addCircle(
            (width - sides * 3).toFloat(),
            height.toFloat(),
            radius,
            Path.Direction.CW
        )
        // Measure the path
        val measure = PathMeasure(path, false)
        length = measure.length

        val intervals = floatArrayOf(length, length)

        val animator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f)
        animator.duration = 3000
        animator.start()
    }

    //is called by animator object
    fun setPhase(phase: Float) {
        Log.d("CurveGraphDrawable", "setPhase called with:$phase")
        linePaint.pathEffect = createPathEffect(length, phase, 0.0f)
        invalidate() //will call onDraw
    }

    private fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect? {
        return DashPathEffect(
            floatArrayOf(pathLength, pathLength),
            (phase * pathLength).coerceAtLeast(offset)
        )
    }

    private val linePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 14f
        color = Color.parseColor("#E02E2E")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, linePaint)
    }
}