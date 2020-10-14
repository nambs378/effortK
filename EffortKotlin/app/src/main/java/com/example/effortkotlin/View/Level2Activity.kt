package com.example.effortkotlin.View

import android.content.Context
import android.os.AsyncTask
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
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.LevelGiaoTiep
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.View.Level2Activity.Companion.id
import com.example.effortkotlin.View.Level2Activity.Companion.idlevel
import com.example.effortkotlin.View.Level2Activity.Companion.kind
import com.example.effortkotlin.View.Level2Activity.Companion.levelstate
import com.example.effortkotlin.adapter.LevelGiaoTiepAdapter
import com.example.effortkotlin.adapter.ListTuAdapter
import com.example.effortkotlin.factory.CheckConnection
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class Level2Activity : AppCompatActivity() {

    lateinit var view: View



    companion object{
        lateinit var recyclerView: RecyclerView
        lateinit var levelGiaoTiepList: MutableList<LevelGiaoTiep>
        lateinit var levelGiaoTiepAdapter: LevelGiaoTiepAdapter
        lateinit var titlelevel: String
        lateinit var imagelevel:String
        internal var kind = 1
        internal var duongdan: String? =""
        var id: Int = 0
        var idlevel:Int = 0
        var levelstate:Int = 0
    }

    //



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)



        levelGiaoTiepList = ArrayList()
        initView()
        kind  = intent.getIntExtra("kindd",0)
        try {
            if (kind == 1) {
                title = "Level Listen"

            } else if (kind == 2) {
                title = "Level Vocabulary"

            }
        }catch (e : Exception){

        }
        Log.e("zxc", "$kind")

        if (CheckConnection.haveNetworkConnection(this)) {
            val updatestarlist = SetAdapterLevelList(this)
            updatestarlist.execute()
        } else {
            Toast.makeText(this, "Check the internet", Toast.LENGTH_SHORT).show()
            finish()
        }

    }



    private fun initView() {
        view = findViewById(R.id.view_level)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)
        recyclerView = findViewById(R.id.rv_lv)

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


}


class SetAdapterLevelList(internal var ctx: Context) : AsyncTask<Void, Void, Void>() {

    var duongdan : String? =""

    override fun doInBackground(vararg p0: Void?): Void? {
        if (kind == 1) {
            duongdan = Server.Duongdanlevelgiaotiep
        } else if (kind == 2) {
            duongdan = Server.Duongdanleveltuvung
        }
        val requestQueue = Volley.newRequestQueue(ctx)

        val jsonArrayRequest = JsonArrayRequest(duongdan, Response.Listener { response ->
            if (response != null) {
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        id = jsonObject.getInt("id")
                        idlevel = jsonObject.getInt("idlevel")
                        Level2Activity.titlelevel = jsonObject.getString("titlelevel")
                        Level2Activity.imagelevel = jsonObject.getString("imagelevel")
                        levelstate = jsonObject.getInt("levelstate")
                        Level2Activity.levelGiaoTiepList.add(LevelGiaoTiep(id, idlevel,
                            Level2Activity.titlelevel,
                            Level2Activity.imagelevel, levelstate))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                Log.e("zxc", " ${Level2Activity.levelGiaoTiepList}")
                Level2Activity.levelGiaoTiepAdapter = LevelGiaoTiepAdapter(Level2Activity.levelGiaoTiepList, ctx, kind)
                val mLayoutManager = LinearLayoutManager(ctx)
                Level2Activity.recyclerView.layoutManager = mLayoutManager
                Level2Activity.recyclerView.itemAnimator = DefaultItemAnimator()
                Level2Activity.recyclerView.adapter = Level2Activity.levelGiaoTiepAdapter
            }
        }, Response.ErrorListener { error -> Log.e("cvcv", " $error") })
        requestQueue.add(jsonArrayRequest)
        return null
    }

}
