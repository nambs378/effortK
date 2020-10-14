package com.example.effortkotlin.ToastCustom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class SuccessToastView : View {

    internal var rectF = RectF()
    internal var valueAnimator: ValueAnimator? = null
    internal var mAnimatedValue = 0f
    private var mPaint: Paint? = null
    private var mWidth = 0f
    private var mEyeWidth = 0f
    private var mPadding = 0f
    private var endAngle = 0f
    private var isSmileLeft = false
    private var isSmileRight = false

     constructor(context: Context?) : super(context)
     constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
     constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPaint()
        initRect()
        mWidth = measuredWidth.toFloat()
        mPadding = dip2px(10f).toFloat()
        mEyeWidth = dip2px(3f).toFloat()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.setAntiAlias(true)
        mPaint!!.setStyle(Paint.Style.STROKE)
        mPaint!!.setColor(Color.parseColor("#5cb85c"))
        mPaint!!.setStrokeWidth(dip2px(2f).toFloat())
    }

    private fun initRect() {
        rectF = RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint!!.setStyle(Paint.Style.STROKE)
        canvas.drawArc(rectF, 180f, endAngle, false, mPaint!!)

        mPaint!!.setStyle(Paint.Style.FILL)
        if (isSmileLeft) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint!!)
        }
        if (isSmileRight) {
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint!!)
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
            isSmileLeft = false
            isSmileRight = false
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
                isSmileLeft = false
                isSmileRight = false
                endAngle = -360 * mAnimatedValue
            } else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7) {
                endAngle = -180f
                isSmileLeft = true
                isSmileRight = false
            } else {
                endAngle = -180f
                isSmileLeft = true
                isSmileRight = true
            }

            postInvalidate()
        })

        if (!valueAnimator!!.isRunning()) {
            valueAnimator!!.start()

        }
        return valueAnimator!!
    }


}