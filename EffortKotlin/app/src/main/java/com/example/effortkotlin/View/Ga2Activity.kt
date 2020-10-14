package com.example.effortkotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.Grammar2
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.adapter.Ga2Adapter
import com.example.effortkotlin.adapter.ListTuAdapter
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList
import java.util.HashMap

class Ga2Activity : AppCompatActivity() {
    lateinit var view: View
    lateinit var ga2ModelList: MutableList<Grammar2>
    lateinit var ga2Adapter: Ga2Adapter
    lateinit var rvGa2: RecyclerView
    internal var grammar: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ga2)

        view = findViewById(R.id.view_ga2)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Grammar")
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        grammar = bundle?.getString("Grammar")

        rvGa2 = findViewById(R.id.rv_ga2)

        Log.e("vxxcv", grammar)


        setadapter()



    }

    private fun setadapter() {
        ga2ModelList = ArrayList<Grammar2>()

        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdangrammar2,
            Response.Listener { response ->
                var id: Int
                var nguphap: String
                var chitiet: String
                var idgrammar: String
                if (response != null) {
                    try {
                        val jsonArray = JSONArray(response)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            nguphap = jsonObject.getString("nguphap")
                            chitiet = jsonObject.getString("chitiet")
                            idgrammar = jsonObject.getString("idgrammar")

                            ga2ModelList.add(Grammar2(id, nguphap, chitiet, idgrammar))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    Log.e("vxxcv", ga2ModelList.toString())
                    ga2Adapter = Ga2Adapter(ga2ModelList, applicationContext)
                    val layoutManager = LinearLayoutManager(applicationContext)
                    rvGa2.layoutManager = layoutManager
                    rvGa2.adapter = ga2Adapter

                }
            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["idgrammar"] = grammar
                return param
            }
        }
        requestQueue.add(stringRequest)



    }

}
