package com.example.effortkotlin.api

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.effortkotlin.View.TalkTranstalteActivity
import com.example.effortkotlin.View.TalkTranstalteActivity.Companion.ngonngu
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class TranslateTalkEng(
    internal var ctx: Context
) : AsyncTask<String, Void, String>() {

    internal var resultString: String? =""
    internal var textToBeTranslated: String? = ""


    override fun doInBackground(vararg p0: String?): String? {
        textToBeTranslated = p0[0]
        val languagePair = p0[1]

        var jsonString: String?=""

        try {
            //Set up the translation call URL
            val yandexKey = "trnsl.1.1.20190325T063022Z.6b93a3a2396dd7fe.0f642233147a0b2fb0d5c9b60580502ebd7d961c"
            val yandexUrl = ("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair)
            val yandexTranslateURL = URL(yandexUrl)

            //Set Http Conncection, Input Stream, and Buffered Reader
            val httpJsonConnection = yandexTranslateURL.openConnection() as HttpURLConnection
            val inputStream = httpJsonConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            //Read Text
            resultString = bufferedReader.readText()
            //Close and disconnect
            bufferedReader.close()
            inputStream.close()
            httpJsonConnection.disconnect()
            //Making answer readable
            resultString = resultString?.substring(resultString!!.indexOf("[")+2,resultString!!.indexOf("]")-1)

            //{"code":200,"lang":"en-vi","text":["xin chào"]}


            Log.e("zxczzzz", resultString)
            Log.e("zxczzzz", " zâsfàa $ngonngu")

            return resultString!!
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            Log.e("zxc", e.toString())

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("zxc", e.toString())
        }
        return resultString

    }

    override fun onPostExecute(result: String?) {
        Log.e("zxczzzz", ngonngu)

            TalkTranstalteActivity.tvTalkViet.text = (resultString)


//        TalkTranstalteActivity.tvTalkViet.text = (resultString)

        super.onPostExecute(result)

    }


}