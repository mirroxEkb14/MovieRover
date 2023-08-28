package com.danidev.movierover.customs

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.danidev.movierover.R

/**
 * This class represents a custom progress bar that shows a rating on a particular movie.
 *
 * @param context is an instance of the [Context] class whose objects provide access to the basic
 * functions of this application (resources, file system etc.).
 * @param attributeSet is attributes that will be set from the XML file.
 */
class RatingDonutView @JvmOverloads constructor(context: Context,
                                                attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {

    /** Oval to draw certain segments of progress bar. */
    private val oval = RectF()

    /** Coordinates of the center of the [View], and also its radius. */
    private var radius: Float = FLOAT_INITIALIZATION_VALUE
    private var centerX: Float = FLOAT_INITIALIZATION_VALUE
    private var centerY: Float = FLOAT_INITIALIZATION_VALUE

    /** Width of the progress line. */
    private var stroke = 10f

    /** Progress value from 0 to 100. */
    private var progress = 50

    /** Paint for figures, specifically for rings. */
    private lateinit var ringPaint: Paint

    /** Paint for figures, specifically for digits. */
    private lateinit var digitPaint: Paint

    /** Paint for figures, specifically for background. */
    private lateinit var circlePaint: Paint

    /**
     * This initialization block initializes the paints for figures and gets the values from the
     * attributes (sets the attributes according to the fields).
     */
    init {
        val a = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RatingDonutView, STYLE_ATTRIBUTE, STYLE_RESOURCE)
        try {
            stroke = a.getFloat(R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()
        }
        initPaint()
    }

    /**
     * Applies the dimensions got from [View.setMeasuredDimension] to the [View]. This overridden
     * method is called while creating and each time the size of this [View] is changed.
     *
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(HEIGHT_VIEW_DIVIDER)
        } else {
            width.div(WIDTH_VIEW_DIVIDER)
        }
    }

    /**
     * Sets the dimensions for this [View].
     *
     * The parameters of this method are integer values, in which information about how the [View]
     * should occupy space is transmitted:
     * - `wrap_content`: as much space as it needs;
     * - `match_parent` or `match_constraint(0 dp)`: adjusts to other elements;
     * - or it has an uncompromisingly solid size;
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(X_COORDINATE_DIVIDER)
        centerY = minSide.div(Y_COORDINATE_DIVIDER)

        setMeasuredDimension(minSide, minSide)
    }

    /**
     * Checks the mode, according to which selects and returns a dimension value.
     *
     * @return [Int] value, selected according to the passed mode value: [View.MeasureSpec.UNSPECIFIED],
     * [View.MeasureSpec.EXACTLY], or [View.MeasureSpec.AT_MOST].
     */
    private fun chooseDimension(mode: Int, size: Int) =
        when (mode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> DEFAULT_DIMENSION_VALUE
    }

    /**
     * Sets the rating.
     *
     * Puts a new value in the class field of [progress]. Then, creates Paints with new colors. In
     * the end, calls redrawing of the [View].
     *
     * @param pr is a movie rating.
     *
     * @see initPaint
     */
    fun setProgress(pr: Int) {
        progress = pr
        initPaint()
        invalidate()
    }

    /**
     * Draws the rating ring.
     *
     * Regulates the size of the ring by multiplying it by [RING_RADIUS_MULTIPLIER]. Then, the canvas
     * is saved. Next, moves zero coordinates of the canvas to the center (it's easier to draw round
     * objects). Afterwards, sets the size for the oval. Later, draws background (it is advisable to
     * draw it once in bitmap because it is static). After that, Draws the "arcs", which the ring will
     * consist of. At last, restores the canvas.
     *
     * @param canvas is the canvas on which the background will be drawn.
     *
     * @see convertProgressToDegrees
     */
    private fun drawRating(canvas: Canvas) {
        val scale = radius * RING_RADIUS_MULTIPLIER
        canvas.save()
        canvas.translate(centerX, centerY)
        oval.set(X_COORDINATE_OF_OVAL_LEFT_SIDE - scale,
            Y_COORDINATE_OF_OVAL_TOP_SIDE - scale,
            scale,
            scale)
        canvas.drawCircle(X_COORDINATE_OF_CIRCLE_CENTER,
            Y_COORDINATE_OF_CIRCLE_CENTER,
            radius,
            circlePaint)
        canvas.drawArc(oval,
            ARC_START_ANGLE_DEGREE,
            convertProgressToDegrees(progress),
            false,
            ringPaint)
        canvas.restore()
    }

    /**
     * Converts the progress bar values to degrees, because the progress is from 0 to 100, but the
     * circle is 360 degrees.
     *
     * @param progress is a progress bar value as [Int].
     *
     * @return converted progress bar value to degrees as [Float].
     */
    private fun convertProgressToDegrees(progress: Int): Float = progress * CIRCLE_DEGREE_MULTIPLIER

    /**
     * Draws text.
     *
     * Formats the text to get a fractional number with one digit after the dot. Then, gets the width
     * and height of the text to compensate for them while drawing, so that the text is exactly in
     * the center. Subsequently, draws the text.
     *
     * @param canvas is the canvas for drawing the background.
     */
    private fun drawText(canvas: Canvas) {
        val message = String.format(MESSAGE_FORMAT_STRING, progress / MESSAGE_FORMAT_DIVIDER)
        val widths = FloatArray(message.length)
        digitPaint.getTextWidths(message, widths)
        var advance = FLOAT_INITIALIZATION_VALUE
        for (width in widths) advance += width
        canvas.drawText(message,
            centerX - advance / X_COORDINATE_OF_ORIGIN_TEXT_DIVIDER,
            centerY  + advance / Y_COORDINATE_OF_BASELINE_TEXT_DIVIDER,
            digitPaint)
    }

    /**
     * Draws the [View].
     *
     * Draws the ring and background, then digits.
     *
     * @param canvas is the canvas for background drawing.
     *
     * @see drawRating
     * @see drawText
     */
    override fun onDraw(canvas: Canvas) {
        drawRating(canvas)
        drawText(canvas)
    }

    /**
     * Initializes the fields for the paints:
     * - [ringPaint]
     * - [digitPaint]
     * - [circlePaint]
     *
     * Sets the width for stroking of [ringPaint] using the [android.graphics.Paint.setStrokeWidth]
     * method to a value of [stroke], because the paints will be always changing.
     *
     * Sets the paint's color for [ringPaint] using the [android.graphics.Paint.setColor] method to
     * the result of the [getPaintColor] local method, because the color of the ring will be always
     * changing depending on a particular rating value.
     *
     * @see getPaintColor
     */
    private fun initPaint() {
        ringPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            color = getPaintColor(progress)
            isAntiAlias = true
        }
        digitPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = DIGIT_PAINT_STROKE
            setShadowLayer(SHADOW_RADIUS, SHADOW_X_OFFSET, SHADOW_Y_OFFSET, Color.DKGRAY)
            textSize = SCALE_SIZE
            typeface = Typeface.SANS_SERIF
            color = getPaintColor(progress)
            isAntiAlias = true
        }
        circlePaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.DKGRAY
        }
    }

    /**
     * Changes paint's color depending on rating level.
     *
     * @param progress is actually a rating itself.
     *
     * @return parsed color string as [Int].
     */
    private fun getPaintColor(progress: Int): Int = when(progress) {
        in RED_LOWER_BOUND .. RED_UPPER_BOUND -> Color.parseColor(RED_COLOR)
        in ORANGE_LOWER_BOUND .. ORANGE_UPPER_BOUND -> Color.parseColor(ORANGE_COLOR)
        in YELLOW_LOWER_BOUND .. YELLOW_UPPER_BOUND -> Color.parseColor(YELLOW_COLOR)
        else -> Color.parseColor(GREEN_COLOR)
    }

    companion object {
        /** Value represents a default initialization value for float numbers. */
        private const val FLOAT_INITIALIZATION_VALUE = 0f

        /** Value of the text size inside the ring. */
        private const val SCALE_SIZE = 60f

        /** The following **_four_** constants are related to [digitPaint]:
         * 1. Paint's stroke width value;
         * 2. Blur radius;
         * 3. X offset for the shadow;
         * 4. Y offset for the shadow; */
        private const val DIGIT_PAINT_STROKE = 2f
        private const val SHADOW_RADIUS = 5f
        private const val SHADOW_X_OFFSET = 0f
        private const val SHADOW_Y_OFFSET = 0f

        /** The following **_six_** constants represent lower and upper bounds for colors that
         * [getPaintColor] should return. */
        private const val RED_LOWER_BOUND = 0
        private const val RED_UPPER_BOUND = 25
        private const val ORANGE_LOWER_BOUND = 26
        private const val ORANGE_UPPER_BOUND = 50
        private const val YELLOW_LOWER_BOUND = 51
        private const val YELLOW_UPPER_BOUND = 75

        /** The following **_four_** constants represent color strings used inside [getPaintColor]. */
        private const val RED_COLOR = "#e84258"
        private const val ORANGE_COLOR = "#fd8060"
        private const val YELLOW_COLOR = "#fee191"
        private const val GREEN_COLOR = "#b0d8a4"

        /** The following **_two_** constants represent default style for
         * `Resources.obtainStyledAttributes(AttributeSet, int[], int, int)` inside the `init` block. */
        private const val STYLE_ATTRIBUTE = 0
        private const val STYLE_RESOURCE = 0

        /** The following **_two_** constants represent dividers for this [View] height and width. */
        private const val HEIGHT_VIEW_DIVIDER = 2f
        private const val WIDTH_VIEW_DIVIDER = 2f

        /** The following **_two_** constants represent dividers for [centerX] and [centerY]
         * coordinates of this [View] used inside [onMeasure]. */
        private const val X_COORDINATE_DIVIDER = 2f
        private const val Y_COORDINATE_DIVIDER = 2f

        /** Default value for dimensions for this [View] width and height used inside [chooseDimension]. */
        private const val DEFAULT_DIMENSION_VALUE = 300

        /** The following **_six_** constants are related to [drawRating]. */
        private const val RING_RADIUS_MULTIPLIER = 0.8f
        private const val X_COORDINATE_OF_OVAL_LEFT_SIDE = 0f
        private const val Y_COORDINATE_OF_OVAL_TOP_SIDE = 0f
        private const val X_COORDINATE_OF_CIRCLE_CENTER = 0f
        private const val Y_COORDINATE_OF_CIRCLE_CENTER = 0f
        private const val ARC_START_ANGLE_DEGREE = -90f

        /** Value for converting integers to degrees. Used in [convertProgressToDegrees]. */
        private const val CIRCLE_DEGREE_MULTIPLIER = 3.6f

        /** The following **_four_** constants are related to [drawText]. */
        private const val MESSAGE_FORMAT_STRING = "%.1f"
        private const val MESSAGE_FORMAT_DIVIDER = 10f
        private const val X_COORDINATE_OF_ORIGIN_TEXT_DIVIDER = 2
        private const val Y_COORDINATE_OF_BASELINE_TEXT_DIVIDER = 4
    }
}