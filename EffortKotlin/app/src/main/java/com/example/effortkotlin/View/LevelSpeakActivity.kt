package com.example.effortkotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.LevelGiaoTiep
import com.example.effortkotlin.Model.LevelSpeak
import com.example.effortkotlin.R

import com.example.effortkotlin.adapter.LevelspAdapter
import com.example.effortkotlin.factory.CheckConnection
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import org.json.JSONException

class LevelSpeakActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var levelspeakList: MutableList<LevelSpeak>
    lateinit var view: View
    lateinit var levelspeakAdapter: LevelspAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_speak)


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
            title = "Level Speaking"
        }
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)



        levelspeakList = ArrayList<LevelSpeak>()
        initView()


        if (CheckConnection.haveNetworkConnection(this)) {
            setAdapter()
        } else {
            Toast.makeText(this, "Check the internet", Toast.LENGTH_SHORT).show()
            finish()
        }


    }


    private fun initView() {
        view = findViewById(R.id.view_level)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)
        recyclerView = findViewById(R.id.rv_lv_speak)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun setAdapter() {
        var id : Int? =0
        var title : String? = ""
        var description : String? = ""
        var imagelv : String? = ""
        var idlevlll : Int? = 0

        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest(Server.Duongdanlevelspeak, Response.Listener { response ->
            if (response != null) {
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        id = jsonObject.getInt("id")
                        title = jsonObject.getString("title")
                        description = jsonObject.getString("description")
                        imagelv = jsonObject.getString("imagelevel")
                        idlevlll = jsonObject.getInt("idlevel")

                        levelspeakList.add(LevelSpeak(id!!, title!!, description!!, imagelv!!, idlevlll!!))

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                Log.e("zxc", " $levelspeakList")

                levelspeakAdapter = LevelspAdapter(levelspeakList, applicationContext)
                val mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView.layoutManager = mLayoutManager
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.adapter = levelspeakAdapter
            }
        }, Response.ErrorListener { error -> Log.e("cvcv", " $error") })

        requestQueue.add(jsonArrayRequest)

    }

}
