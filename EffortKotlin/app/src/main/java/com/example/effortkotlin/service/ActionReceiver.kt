package com.example.effortkotlin.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.core.app.NotificationManagerCompat
import com.example.effortkotlin.ToastCustom.TastyToast
import com.example.effortkotlin.database.QuizDBHelper
;
class ActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.let { NotificationManagerCompat.from(it) }
        val Voca = intent?.getStringExtra("Voca")
        val dapanA = intent?.getStringExtra("DapAnA")
        val dapanB = intent?.getStringExtra("DapAnB")

        val quizDBHelper = context?.let { QuizDBHelper(it) }

        val notiVoca = Voca?.let { quizDBHelper?.getNotiVoCa(it) }
        val handler = Handler()

        if (intent?.getAction() == "Action_A") {

            if (notiVoca?.getMean().equals(dapanA)) {
                handler.postDelayed({
                    notificationManager?.cancel(1)
                    val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                    context?.sendBroadcast(it)
                }, 1000)
                TastyToast.makeText(context!!, "$Voca : $dapanA", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show()
            } else {
                handler.postDelayed({
                    val it = Intent(context, AlarmBroadcastReceiver::class.java)
                    context?.sendBroadcast(it)
                }, 1000)
                TastyToast.makeText(context!!, Voca + " : " + notiVoca?.getMean(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show()
            }

        } else if (intent?.getAction() == "Action_B") {
            if (notiVoca?.getMean().equals(dapanB)) {
                handler.postDelayed({
                    notificationManager?.cancel(1)
                    val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                    context?.sendBroadcast(it)
                }, 1000)
                TastyToast.makeText(context!!, "$Voca : $dapanB", TastyToast.LENGTH_LONG, TastyToast.SUCCESS)

            } else {
                handler.postDelayed({
                    val it = Intent(context, AlarmBroadcastReceiver::class.java)
                    context?.sendBroadcast(it)
                }, 1000)
                TastyToast.makeText(
                    context!!, Voca + " : " + notiVoca?.getMean(), TastyToast.LENGTH_LONG, TastyToast.ERROR
                )
            }
        }





    }


}