package com.example.effortkotlin.api

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.View.TranslateActivity
import com.example.effortkotlin.adapter.ListTuAdapter
import com.example.effortkotlin.database.QuizDBHelper
import com.example.effortkotlin.factory.Server
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap

class TranslatorBackgroundTask(
    internal var ctx: Context
) : AsyncTask<String, Void, String>() {

        var resultString: String? = ""


    internal var textToBeTranslated: String? = ""
    internal var input: String? = ""
    internal var output:String? = ""
//    lateinit var quizDBHelper: QuizDBHelper

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

            Log.e("zxczzzz", resultString)

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
        TranslateActivity.tvValue.setText(result)

        input = textToBeTranslated
        output = resultString

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdaninserttranslist,
            Response.Listener { response ->


                TranslateActivity.transVocaList.clear()

                TranslateActivity.setAdapterr(ctx)

            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["vocatrans"] = input
                param["mean"] = output
                param["importants"] = "0"
                return param
            }
        }
        requestQueue.add(stringRequest)



        super.onPostExecute(result)
    }



}