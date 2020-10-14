package com.example.effortkotlin.database

import android.provider.BaseColumns

class QuizContract {

    private fun QuizContract(){}

    class QuestionsTable : BaseColumns {
        companion object {

            val _ID ="id"
            val TABLE_NAME_RESULT = "question_result"
            val COLUMN_RESULT = "result"


            val TABLE_NAME_NOTI = "noti_vocabulary"
            val COLUMN_VOCANOTI = "vocabulary"
            val COLUMN_MEAN = "mean"
            val COLUMN_IMPORTANTS = "importants"

            val TABLE_NAME_SETTINGS = "settings"
            val COLUMN_TIMESET = "timeset"
            val COLUMN_SW = "sw"

            val TABLE_NAME_TRANSLATE = "translate_list"
        }


    }


}