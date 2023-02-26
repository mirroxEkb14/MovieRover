package com.danidev.movierover.customs

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.danidev.movierover.R

/**
 * Custom view that represents a progress bar with movie rating
 */
class RatingDonutView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    // oval to draw segments of progress bar
    private val oval = RectF()
    // coordinates of the center of View, and Radius
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    // width of progress line
    private var stroke = 10f
    // progress value from 0 to 100
    private var progress = 50
    // value of text size inside ring
    private var scaleSize = 60f
    // paints for our figures
    private lateinit var strokePaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint

    /**
     * Link the attributes to the custom class content
     */
    init {
        // get the attributes and set it according to the fields
        val a = context.theme.obtainStyledAttributes(attributeSet, R.styleable.RatingDonutView, DEFAULT_DEF_STYLE_ATTRIBUTE, DEFAULT_DEF_STYLE_RESOURCE)
        try {
            stroke = a.getFloat(R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()
        }
        // initialize initial Paints
        initPaint()
    }

    /**
     * Apply the dimensions got from setMeasuredDimension() to our View
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(DEFAULT_SIZE_DIVIDER)
        } else {
            width.div(DEFAULT_SIZE_DIVIDER)
        }
    }

    /**
     * Get dimensions
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(DEFAULT_SIZE_DIVIDER)
        centerY = minSide.div(DEFAULT_SIZE_DIVIDER)

        setMeasuredDimension(minSide, minSide)
    }

    /**
     * According to getMode() and getSize() we the a certain width and height
     */
    private fun chooseDimension(mode: Int, size: Int) =
        when (mode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> UNSPECIFIED_MEASURE_DIMENSION
    }

    /**
     * Set the rating
     */
    fun setProgress(pr: Int) {
        // put a new value in our class field
        progress = pr
        // create Paints with new colors
        initPaint()
        // call redrawing of the View
        invalidate()
    }

    /**
     * Draws the rating ring
     */
    private fun drawRating(canvas: Canvas) {
        // control the size of the ring
        val scale = radius * 0.8f
        // save the canvas
        canvas.save()
        // move zero coordinates of the canvas to the center (it's easier to draw round objects)
        canvas.translate(centerX, centerY)
        // set the size for the oval
        oval.set(DEFAULT_OVAL_LEFT_COORDINATE - scale, DEFAULT_OVAL_TOP_COORDINATE - scale, scale , scale)
        // draw background (it is advisable to draw it once in bitmap because it is static)
        canvas.drawCircle(DEFAULT_CENTER_CIRCLE_COORDINATE_X, DEFAULT_CENTER_CIRCLE_COORDINATE_Y, radius, circlePaint)
        // draw the "arcs" from which the ring will consist
        canvas.drawArc(oval, ARC_STARTING_ANGLE, convertProgressToDegrees(progress), false, strokePaint)
        // restore the canvas
        canvas.restore()
    }

    /**
     * A method to draw the arcs
     * The progress is from 0 to 100 but the circle is 360 degrees, so that we need to
     * convert these values
     */
    private fun convertProgressToDegrees(progress: Int): Float = progress * 3.6f

    /**
     * Draw text
     */
    private fun drawText(canvas: Canvas) {
        // format the text for us to get a fractional number with one digit after the dot
        val message = String.format("%.1f", progress / 10f)
        // get the width and height of the text to compensate for them while drawing,
        // so that the text was exactly in the center
        val widths = FloatArray(message.length)
        digitPaint.getTextWidths(message, widths)
        var advance = DEFAULT_TEXT_ADVANCE
        for (width in widths) advance += width
        // draw the text
        canvas.drawText(message, centerX - advance / 2, centerY  + advance / 4, digitPaint)
    }

    /**
     * Draw the View
     */
    override fun onDraw(canvas: Canvas) {
        // draw the ring and background
        drawRating(canvas)
        // draw digits
        drawText(canvas)
    }

    /**
     * Initialize the fields for Paints
     */
    private fun initPaint() {
        // paints for rings
        strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            // put a value from class field because the paints will change
            strokeWidth = stroke
            // we get a color in a method because depending on the rating we will change the color of our ring
            color = getPaintColor(progress)
            isAntiAlias = true
        }
        // paint for digits
        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = DEFAULT_DIGIT_STROKE_WIDTH
            setShadowLayer(DEFAULT_SHADOW_LAYER_RADIUS, DEFAULT_SHADOW_LAYER_DX, DEFAULT_SHADOW_LAYER_DY, Color.DKGRAY)
            textSize = scaleSize
            typeface = Typeface.SANS_SERIF
            color = getPaintColor(progress)
            isAntiAlias = true
        }
        // paint for background
        circlePaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.DKGRAY
        }
    }

    /**
     * A method to choose the colors for Paint. The color will change depending on rating level
     */
    private fun getPaintColor(progress: Int): Int = when(progress) {
        in 0 .. 25 -> Color.parseColor(RED_COLOR)
        in 26 .. 50 -> Color.parseColor(ORANGE_COLOR)
        in 51 .. 75 -> Color.parseColor(YELLOW_COLOR)
        else -> Color.parseColor(GREEN_COLOR)
    }

    // color values for Paint
    companion object {
        private const val RED_COLOR = "#e84258"
        private const val ORANGE_COLOR = "#fd8060"
        private const val YELLOW_COLOR = "#fee191"
        private const val GREEN_COLOR = "#b0d8a4"

        // initial class values
        private const val DEFAULT_DEF_STYLE_ATTRIBUTE = 0
        private const val DEFAULT_DEF_STYLE_RESOURCE = 0

        private const val DEFAULT_SIZE_DIVIDER = 2f

        private const val UNSPECIFIED_MEASURE_DIMENSION = 300

        private const val DEFAULT_OVAL_LEFT_COORDINATE = 0f
        private const val DEFAULT_OVAL_TOP_COORDINATE = 0f

        private const val DEFAULT_CENTER_CIRCLE_COORDINATE_X = 0f
        private const val DEFAULT_CENTER_CIRCLE_COORDINATE_Y = 0f

        private const val ARC_STARTING_ANGLE = -90f

        private const val DEFAULT_TEXT_ADVANCE = 0f

        private const val DEFAULT_DIGIT_STROKE_WIDTH = 2f

        private const val DEFAULT_SHADOW_LAYER_RADIUS = 5f
        private const val DEFAULT_SHADOW_LAYER_DX = 0f
        private const val DEFAULT_SHADOW_LAYER_DY = 0f
    }
}