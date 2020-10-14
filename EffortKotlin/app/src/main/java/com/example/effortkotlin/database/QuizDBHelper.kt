package com.example.effortkotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.effortkotlin.Model.NotiVoca
import com.example.effortkotlin.Model.Settings
import java.util.ArrayList

private val DATABASE_NAME = "MyAwesomeQuiz.db"
private val DATABASE_VERSION = 1

class QuizDBHelper(val context : Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {


    private var db: SQLiteDatabase? = null


    override fun onCreate(p0: SQLiteDatabase?) {
        db = p0;

        val CREATE_SQL_NOTI = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME_NOTI + "(" +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_VOCANOTI + " TEXT," +
                QuizContract.QuestionsTable.COLUMN_MEAN + " TEXT," +
                QuizContract.QuestionsTable.COLUMN_IMPORTANTS + " TEXT " + ")"

        db?.execSQL(CREATE_SQL_NOTI)

        val CREATE_SQL_SETTINGS = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME_SETTINGS + "(" +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_TIMESET + " INTEGER," +
                QuizContract.QuestionsTable.COLUMN_SW + " INTEGER " + ")"

        db?.execSQL(CREATE_SQL_SETTINGS)

        val settings1 = Settings(5, 0)
        addSetting(settings1)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME_NOTI)

        db?.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME_SETTINGS)

        onCreate(db)
    }

    fun addSetting(settings: Settings) {
        val cv = ContentValues()
        cv.put(QuizContract.QuestionsTable.COLUMN_TIMESET, settings.getTimeSet())
        cv.put(QuizContract.QuestionsTable.COLUMN_SW, settings.getSw())
        db?.insert(QuizContract.QuestionsTable.TABLE_NAME_SETTINGS, null, cv)
    }


    fun getAllNoti(): MutableList<NotiVoca> {
        val notiVocaList = ArrayList<NotiVoca>()
        db = readableDatabase
        val c = db?.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME_NOTI, null)
        if (c!!.moveToFirst()) {
            do {
                val notiVoca = NotiVoca()
                notiVoca.setVocat(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_VOCANOTI)))
                notiVoca.setMean(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_MEAN)))
                notiVoca.setImportants(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_IMPORTANTS)))

                notiVocaList.add(notiVoca)
            } while (c.moveToNext())
        }
        c.close()
        return notiVocaList
    }

    fun insertVocaNoti(vocanoti: String, mean: String, importants: Int) {

        db = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(QuizContract.QuestionsTable.COLUMN_VOCANOTI, vocanoti)
        contentValues.put(QuizContract.QuestionsTable.COLUMN_MEAN, mean)
        contentValues.put(QuizContract.QuestionsTable.COLUMN_IMPORTANTS, importants)
        db?.insert(QuizContract.QuestionsTable.TABLE_NAME_NOTI, null, contentValues)
    }


    fun deleteVocaNoti(voccanoti: String): Int? {
        return db?.delete(QuizContract.QuestionsTable.TABLE_NAME_NOTI, QuizContract.QuestionsTable.COLUMN_VOCANOTI + "=?", arrayOf(voccanoti))
    }


 fun getSetting(id:Int):Settings {
        val settings = Settings()
        db = readableDatabase
        val c = db?.rawQuery(
            "SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME_SETTINGS +
            " WHERE " + QuizContract.QuestionsTable._ID + " = ?", arrayOf((id).toString()))

        if (c!!.moveToFirst())
        {
        do
        {
        settings.setTimeSet(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_TIMESET)))
        settings.setSw(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_SW)))
        }
        while (c.moveToNext())
        }
        c.close()
        return settings
}

    fun updateSetting(timeset: Int, sw: Int): Int? {
        db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QuizContract.QuestionsTable.COLUMN_TIMESET, timeset)
        contentValues.put(QuizContract.QuestionsTable.COLUMN_SW, sw)
        return db?.update(QuizContract.QuestionsTable.TABLE_NAME_SETTINGS, contentValues, "id = ?", arrayOf(1.toString()))
    }


    fun getRandomNoti(): NotiVoca {
        val notiVoca = NotiVoca()
        db = readableDatabase
        val c = db!!.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME_NOTI + " ORDER BY RANDOM() LIMIT 1", null)
        //        Cursor c = db.rawQuery("SELECT * FROM QuestionsTable.TABLE_NAME_NOTI WHERE " + QuestionsTable.COLUMN_VOCANOTI + " IN (SELECT " + QuestionsTable.COLUMN_VOCANOTI + " FROM QuestionsTable.TABLE_NAME_NOTI ORDER BY RANDOM() LIMIT 1)",null);

        if (c.moveToFirst()) {
            do {
                notiVoca.setVocat(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_VOCANOTI)))
                notiVoca.setMean(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_MEAN)))
                notiVoca.setImportants(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_IMPORTANTS)))
            } while (c.moveToNext())
        }
        c.close()
        return notiVoca
    }


 fun getNotiVoCa(voca:String):NotiVoca {
     val notiVoca = NotiVoca()

     db = readableDatabase
     val c = db?.rawQuery(
         "SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME_NOTI + " WHERE " + QuizContract.QuestionsTable.COLUMN_VOCANOTI + " = ?", arrayOf(voca))

     if (c!!.moveToFirst())
     {
         do
         { notiVoca.setVocat(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_VOCANOTI)))
             notiVoca.setMean(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_MEAN)))
             notiVoca.setImportants(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_IMPORTANTS)))
         }
         while (c.moveToNext())
     }
     c.close()
     return notiVoca
}

    fun updateLightVoca(vocanoti: String, importants: Int): Int {
        db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QuizContract.QuestionsTable.COLUMN_IMPORTANTS, importants)

        return db!!.update(
            QuizContract.QuestionsTable.TABLE_NAME_NOTI,
            contentValues,
            QuizContract.QuestionsTable.COLUMN_VOCANOTI + " = ?",
            arrayOf(vocanoti)
        )
    }

}