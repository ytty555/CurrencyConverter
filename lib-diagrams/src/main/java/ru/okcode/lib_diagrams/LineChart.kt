package ru.okcode.lib_diagrams

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.round

class LineChartIcon : View {

    // Variables
    private var data: List<Float> = emptyList()
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var segmentsAmount: Int = 0
    private var segmentHeight: Int = 0
    private var segmentWidth: Int = 0

    private var usefulSize: Int = 0

    // Initialization
    init {
        linePaint.color = Color.RED
        linePaint.strokeWidth = 20f
    }

    // Constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    companion object {
        private const val MAX_SIZE_VIEW: Int = 300
        private const val MIN_DATA_SIZE: Int = 2
        private const val MAX_DATA_SIZE: Int = 5
    }

    // onMeasure
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth =
            MAX_SIZE_VIEW - paddingStart - paddingTop - paddingEnd - paddingBottom

        val desiredHeight =
            MAX_SIZE_VIEW - paddingStart - paddingTop - paddingEnd - paddingBottom

        val resolveWidth = resolveSize(desiredWidth, widthMeasureSpec)
        val resolveHeight = resolveSize(desiredHeight, heightMeasureSpec)

        usefulSize = if (resolveWidth > resolveHeight) resolveHeight else resolveWidth

        setSegments(usefulSize)

        setMeasuredDimension(usefulSize, usefulSize)
    }

    // OnDraw
    override fun onDraw(canvas: Canvas) {
        if (segmentsAmount == 0) {
            return
        }

        var startX = 0f
        var startY = calculateY(data, 0)

        for (i in 0 until segmentsAmount) {
            val stopX = startX + segmentWidth
            val stopY = calculateY(data, i + 1)

            canvas.drawLine(startX, startY, stopX, stopY, linePaint)

            startX = stopX
            startY = stopY
        }
    }

    private fun calculateY(data: List<Float>, index: Int): Float {
        val maxData = data.maxOrNull() ?: return 0f

        return data[index] * usefulSize / maxData
    }

    // Set data
    fun setData(data: List<Float>) {
        if (data.isEmpty() || data.size < MIN_DATA_SIZE || data.size > MAX_DATA_SIZE) {
            return
        }

        this.data = data
        invalidate()
    }

    private fun setSegments(usefulSize: Int) {
        if (data.isEmpty() || data.size < MIN_DATA_SIZE || data.size > MAX_DATA_SIZE) {
            segmentsAmount = 0
            return
        }

        segmentsAmount = data.size - 1
        segmentHeight = usefulSize
        segmentWidth = round(usefulSize * 1f / segmentsAmount).toInt()
    }
}