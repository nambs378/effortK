package com.example.effortkotlin.View

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListActivity.Companion.idlevel
import com.example.effortkotlin.View.ListActivity.Companion.kind
import com.example.effortkotlin.View.ListActivity.Companion.listTuAdapter
import com.example.effortkotlin.View.ListActivity.Companion.recyclerView
import com.example.effortkotlin.View.ListActivity.Companion.tuVunglist
import com.example.effortkotlin.adapter.ListTuAdapter
import com.example.effortkotlin.factory.CheckConnection
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import java.io.Serializable
import java.util.*

class ListActivity : AppCompatActivity() {
    var context: Context = this


    lateinit var view: View
    private var gyroscopeObserver: ImageView? = null
    //    FabSpeedDial fabSpeedDial;
    internal var image: String? = null

    lateinit var floatingActionButton: FloatingActionButton


    companion object{
        lateinit var tuVunglist: MutableList<TuVung>
        lateinit var listTuAdapter: ListTuAdapter
        lateinit var recyclerView: RecyclerView
        internal var idlevel: String? = null
        internal var kind: Int = 0
        lateinit var mTTS: TextToSpeech

        var duongdan: String? = ""


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        floatingActionButton = findViewById(R.id.fabid)
        recyclerView = findViewById(R.id.rv_listQuest)

        view = findViewById(R.id.view_list)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        gyroscopeObserver = findViewById(R.id.panorama_image_view_list)

        mTTS = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //                            mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })

        tuVunglist = ArrayList<TuVung>()

        val bundle = intent.extras!!

        image = bundle.getString("imageLevel")
        idlevel = bundle.getString("idlevel")
        kind = bundle.getInt("kind")

        Log.e("zxcz","$kind idlv $idlevel")

        Picasso.get().load(image).into(gyroscopeObserver)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
            title = bundle.getString("title")
        }

        if (CheckConnection.haveNetworkConnection(this)) {
            val updatestarlist = SetAdapterList(this)
            updatestarlist.execute()
        } else {
            Toast.makeText(this, "Check the internet", Toast.LENGTH_SHORT).show()
            finish()
        }


        floatingActionButton.setOnClickListener {
            val intent3 = Intent(this@ListActivity, QuizActivity::class.java)
            intent3.putExtra("listVoca", tuVunglist as Serializable)
            intent3.putExtra("kindtoresult",kind )
            intent3.putExtra("idlevelresult",idlevel)

            startActivity(intent3)
        }


    }



    override fun onBackPressed() {
        super.onBackPressed()
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

    override fun onDestroy() {
        super.onDestroy()
        Log.e("des","sssa")
        mTTS.stop()
        mTTS.shutdown()
    }

}


class SetAdapterList(internal var ctx: Context) : AsyncTask<Void, Void, Void>(){


    var tuvung : String ? = ""

    var duongdan : String? =""


    override fun doInBackground(vararg p0: Void?): Void? {
        if (ListActivity.kind == 1) {
            duongdan = Server.Duongdantugiaotiep
        } else if (ListActivity.kind == 2) {
            duongdan = Server.Duongdantuvung
        }

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, duongdan,
            Response.Listener { response ->
                var id: Int
                var tu: String
                var nghia: String
                var idlevel: Int
                var importains : Int
                Log.e("vxxcv", response.toString())

                if (response != null) {
                    try {
                        val jsonArray = JSONArray(response)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            tu = jsonObject.getString("tuvung")
                            nghia = jsonObject.getString("nghia")
                            idlevel = jsonObject.getInt("idlevel")
                            importains = jsonObject.getInt("importants")
                            tuVunglist.add(TuVung(id, tu, nghia, idlevel,importains))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("vxxcv", e.toString())

                    }

                    Log.e("vxxcv", tuVunglist.toString())
                    listTuAdapter = ListTuAdapter(tuVunglist, ctx, kind)
                    val layoutManager = LinearLayoutManager(ctx)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter =listTuAdapter

                }
            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["idlevelkey"] = idlevel
                return param
            }
        }
        requestQueue.add(stringRequest)

        return null
    }



}