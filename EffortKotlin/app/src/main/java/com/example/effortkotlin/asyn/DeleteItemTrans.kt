package com.example.effortkotlin.asyn

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.View.TranslateActivity
import com.example.effortkotlin.factory.Server
import java.util.HashMap

class DeleteItemTrans(internal var ctx: Context) : AsyncTask<String,Void,Void>(){

    var deleTra : String?= ""


    override fun doInBackground(vararg p0: String?): Void? {
        deleTra = p0[0]
        Log.e("zxczxcz",deleTra)

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdandeletetranslist,
            Response.Listener { response ->

                Log.e("zxczxcz","fisis")

                TranslateActivity.transVocaList.clear()

                TranslateActivity.setAdapterr(ctx)

            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["vocatransdelete"] = deleTra
                return param
            }
        }
        requestQueue.add(stringRequest)

        return null

    }


    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)


    }


}