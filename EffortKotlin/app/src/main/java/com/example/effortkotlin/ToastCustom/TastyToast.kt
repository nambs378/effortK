package com.example.effortkotlin.ToastCustom

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast









class TastyToast {




    companion object{

        val LENGTH_SHORT = 0
        val LENGTH_LONG = 1

        val ERROR = 3

        val SUCCESS = 1


        lateinit var successToastView: SuccessToastView
        lateinit var errorToastView: ErrorToastView


        fun makeText(context: Context, msg: String, length: Int, type: Int): Toast {

            val toast = Toast(context)


            when (type) {
                1 -> {
                    val layout = LayoutInflater.from(context).inflate(com.example.effortkotlin.R.layout.success_toast_layout, null, false)
                    val text = layout.findViewById(com.example.effortkotlin.R.id.toastMessage) as TextView
                    text.text = msg
                    successToastView = layout.findViewById(com.example.effortkotlin.R.id.successView)
                    successToastView.startAnim()
                    text.setBackgroundResource(com.example.effortkotlin.R.drawable.success_toast)
                    text.setTextColor(Color.parseColor("#FFFFFF"))
                    toast.view = layout
                }
                3 -> {
                    val layout = LayoutInflater.from(context).inflate(com.example.effortkotlin.R.layout.error_toast_layout, null, false)

                    val text = layout.findViewById(com.example.effortkotlin.R.id.toastMessage) as TextView
                    text.text = msg
                    errorToastView = layout.findViewById(com.example.effortkotlin.R.id.errorView) as ErrorToastView
                    errorToastView.startAnim()
                    text.setBackgroundResource(com.example.effortkotlin.R.drawable.error_toast)
                    text.setTextColor(Color.parseColor("#FFFFFF"))
                    toast.view = layout
                }

            }
            toast.duration = length
            toast.show()
            return toast
        }

    }



}