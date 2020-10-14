package com.example.effortkotlin.service

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

class NotiVocaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let { showNotification(it) }


    }


    internal fun showNotification(context: Context) {
        val notificationManager = NotificationManagerCompat.from(context)

        val quizDBHelper = QuizDBHelper(context)

        val notiVoca = quizDBHelper.getRandomNoti()


        val remoteViews = RemoteViews(context.packageName, R.layout.notification_just_voca)



        remoteViews.setTextViewText(R.id.tv_notiJustVoca, notiVoca.getVocat())
        remoteViews.setTextViewText(R.id.tv_notiJustNghia, notiVoca.getMean())


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