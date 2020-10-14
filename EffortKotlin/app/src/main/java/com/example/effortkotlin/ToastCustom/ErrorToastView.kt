package com.example.effortkotlin.ToastCustom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Color.parseColor
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.animation.LinearInterpolator
import android.icu.lang.UCharacter.GraphemeClusterBreak.T















class ErrorToastView : View {


    var rectF = RectF()
    var leftEyeRectF = RectF()
    var rightEyeRectF = RectF()
    var valueAnimator: ValueAnimator? = null
    var mAnimatedValue = 0f
    private var mPaint: Paint? = null
    private var mWidth = 0f
    private var mEyeWidth = 0f
    private var mPadding = 0f
    private var endAngle = 0f
    private var isJustVisible = false
    private var isSad = false


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPaint()
        initRect()
        mWidth = measuredWidth.toFloat()
        mPadding = dip2px(10F).toFloat()
        mEyeWidth = dip2px(3F).toFloat()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.color = Color.parseColor("#d9534f")
        mPaint!!.strokeWidth = dip2px(2F).toFloat()
    }

    private fun initRect() {
        rectF = RectF(mPadding / 2, mWidth / 2, mWidth - mPadding / 2, 3 * mWidth / 2)
        leftEyeRectF = RectF(
            mPadding + mEyeWidth - mEyeWidth,
            mWidth / 3 - mEyeWidth,
            mPadding + mEyeWidth + mEyeWidth,
            mWidth / 3 + mEyeWidth
        )
        rightEyeRectF = RectF(
            mWidth - mPadding - 5 * mEyeWidth / 2,
            mWidth / 3 - mEyeWidth, mWidth - mPadding - mEyeWidth / 2, mWidth / 3 + mEyeWidth
        )
    }


     override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint!!.setStyle(Paint.Style.STROKE)
        canvas.drawArc(rectF, 210F, endAngle, false, mPaint!!)

        mPaint!!.setStyle(Paint.Style.FILL)
        if (isJustVisible) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint!!)
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint!!)
        }
        if (isSad) {
            canvas.drawArc(leftEyeRectF, 160F, -220F, false, mPaint!!)
            canvas.drawArc(rightEyeRectF, 20F, 220F, false, mPaint!!)
        }
    }


    fun dip2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 2000)
    }


    fun stopAnim() {
        if (valueAnimator != null) {
            clearAnimation()
            isSad = false
            endAngle = 0f
            isJustVisible = false
            mAnimatedValue = 0f
            valueAnimator!!.end()
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long): ValueAnimator {
        valueAnimator = ValueAnimator.ofFloat(startF, endF)
        valueAnimator!!.setDuration(time)
        valueAnimator!!.setInterpolator(LinearInterpolator())
        valueAnimator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator ->
            mAnimatedValue = valueAnimator.animatedValue as Float
            if (mAnimatedValue < 0.5) {
                isSad = false
                isJustVisible = false
                endAngle = 240 * mAnimatedValue
                isJustVisible = true
            } else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7) {
                endAngle = 120F
                isSad = false
                isJustVisible = true
            } else {
                endAngle = 120F
                isSad = true
                isJustVisible = false
            }

            postInvalidate()
        })

        if (!valueAnimator!!.isRunning()) {
            valueAnimator!!.start()

        }
        return valueAnimator!!
    }



}