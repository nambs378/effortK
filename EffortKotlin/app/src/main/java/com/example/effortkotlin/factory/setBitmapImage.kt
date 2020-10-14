package com.example.effortkotlin.factory

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View

class setBitmapImage {
    companion object{
        fun setBackground(context: Context, view: View, drawableId: Int) {
            var bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = Resources.getSystem().displayMetrics.heightPixels
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            val bitmapDrawable = BitmapDrawable(context.resources, bitmap)
            view.background = bitmapDrawable
        }
    }


}