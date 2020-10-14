package com.example.effortkotlin.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.effortkotlin.R
import com.example.effortkotlin.database.QuizDBHelper
import com.example.effortkotlin.service.App.Companion.CHANNEL_1_ID
import java.util.*



class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 != null) {
            showNotification(p0)
        }
    }


    internal fun showNotification(context: Context) {
        val notificationManager = NotificationManagerCompat.from(context)

        val quizDBHelper = QuizDBHelper(context)

        val notiVoca = quizDBHelper.getRandomNoti()

        val randomVoca = quizDBHelper.getRandomNoti()

        val remoteViews = RemoteViews(context.packageName, R.layout.notification_custom)

        if (notiVoca != null || randomVoca != null) {

            val itA = Intent(context, ActionReceiver::class.java)
            itA.action = "Action_A"
            //        itA.putExtra("DapAnA",notiVoca.getMean());
            itA.putExtra("Voca", notiVoca!!.getVocat())
            val itB = Intent(context, ActionReceiver::class.java)
            itB.action = "Action_B"
            //        itB.putExtra("DapAnB",notiVoca.getMean());
            itB.putExtra("Voca", notiVoca!!.getVocat())
            remoteViews.setTextViewText(R.id.tv_notiCustom, notiVoca!!.getVocat())
            val rd = Random()
            val rand = rd.nextInt(2) + 1

            if (rand == 1) {
                if (notiVoca!!.getMean().equals(randomVoca!!.getMean())) {
                    //                    do {
                    //                        NotiVoca randomVocaAgain = quizDBHelper.getRandomNoti();
                    //
                    //                        remoteViews.setTextViewText(R.id.bt_dapan1, notiVoca.getMean());
                    //                        remoteViews.setTextViewText(R.id.bt_dapan2, randomVocaAgain.getMean());
                    //                    }while (notiVoca.getMean().equals(randomVocaAgain.getMean()));

                    val randomVocaAgain = quizDBHelper.getRandomNoti()
                    remoteViews.setTextViewText(R.id.bt_dapan1, notiVoca!!.getMean())
                    remoteViews.setTextViewText(R.id.bt_dapan2, randomVocaAgain.getMean())

                    itA.putExtra("DapAnA", notiVoca!!.getMean())

                    itB.putExtra("DapAnB", randomVocaAgain.getMean())

                } else {
                    remoteViews.setTextViewText(R.id.bt_dapan1, notiVoca!!.getMean())
                    remoteViews.setTextViewText(R.id.bt_dapan2, randomVoca!!.getMean())
                    itA.putExtra("DapAnA", notiVoca!!.getMean())
                    itB.putExtra("DapAnB", randomVoca!!.getMean())
                }

            } else if (rand == 2) {
                if (notiVoca!!.getMean().equals(randomVoca!!.getMean())) {

                    val randomVocaAgain = quizDBHelper.getRandomNoti()
                    remoteViews.setTextViewText(R.id.bt_dapan1, randomVocaAgain.getMean())
                    remoteViews.setTextViewText(R.id.bt_dapan2, notiVoca!!.getMean())
                    
                    itA.putExtra("DapAnA", randomVocaAgain.getMean())
                    itB.putExtra("DapAnB", notiVoca!!.getMean())
                } else {
                    remoteViews.setTextViewText(R.id.bt_dapan1, randomVoca!!.getMean())
                    remoteViews.setTextViewText(R.id.bt_dapan2, notiVoca!!.getMean())

                    itA.putExtra("DapAnA", randomVoca!!.getMean())
                    itB.putExtra("DapAnB", notiVoca!!.getMean())
                }
            }

            val penA = PendingIntent.getBroadcast(context, 0, itA, PendingIntent.FLAG_UPDATE_CURRENT)

            val penB = PendingIntent.getBroadcast(context, 0, itB, PendingIntent.FLAG_UPDATE_CURRENT)

            remoteViews.setOnClickPendingIntent(R.id.bt_dapan1, penA)

            remoteViews.setOnClickPendingIntent(R.id.bt_dapan2, penB)

        }

        if (notiVoca != null || randomVoca != null) {
            val notification = NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.vocatron)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViews)
                .build()
            notificationManager.notify(1, notification)
        }


    }


}