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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListActivity.Companion.mTTS
import com.example.effortkotlin.View.ListActivity.Companion.tuVunglist
import com.example.effortkotlin.View.ListSpeakActivity.Companion.idlevelSpeak
import com.example.effortkotlin.adapter.ListTuAdapter
import com.example.effortkotlin.adapter.Updatestarlist
import com.example.effortkotlin.factory.CheckConnection
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import java.io.Serializable
import java.util.*

class ListSpeakActivity : AppCompatActivity() {
    var context: Context = this

    lateinit var view: View
    private var gyroscopeObserver: ImageView? = null
    internal var image: String? = null

    lateinit var floatingActionButton: FloatingActionButton

    companion object{
        lateinit var tuVunglistSpeak: MutableList<TuVung>
        lateinit var listTuAdapteSpeak: ListTuAdapter
        lateinit var recyclerView: RecyclerView
        internal var idlevelSpeak: String? = null
        internal var kindSpeak: Int = 0

        var duongdan: String? = ""
        lateinit var mTTS1: TextToSpeech

        fun setAdapter(context  : Context) {




        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_speak)

        floatingActionButton = findViewById(R.id.fabid_speak)
        recyclerView = findViewById(R.id.rv_listQuest_speak)

        mTTS1 = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS1.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //                            mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })

        tuVunglistSpeak = ArrayList<TuVung>()

        view = findViewById(R.id.view_list)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        gyroscopeObserver = findViewById(R.id.panorama_image_view_list_speak)

        tuVunglist = ArrayList()
        val bundle = intent.extras!!

        image = bundle.getString("imageLevel")
        idlevelSpeak = bundle.getString("idlevel")

        Picasso.get().load(image).into(gyroscopeObserver)


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
            title = bundle.getString("title")
        }

        if (CheckConnection.haveNetworkConnection(this)) {
            val updatestarlist = SetAdapterListSpeak(this)
            updatestarlist.execute()

        } else {
            Toast.makeText(this, "Check the internet", Toast.LENGTH_SHORT).show()
            finish()
        }

        floatingActionButton.setOnClickListener {
            val intent3 = Intent(this, QuizSpeakActivity::class.java)
            intent3.putExtra("listVoca", tuVunglistSpeak as Serializable)
            intent3.putExtra("kind", ListActivity.kind)
            startActivity(intent3)
        }

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
        mTTS1.stop()
        mTTS1.shutdown()
    }

}

class SetAdapterListSpeak(internal var ctx: Context) : AsyncTask<Void, Void, Void>(){


    var idlevel : String?= ""
    var importains : String ? = ""
    var tuvung : String ? = ""
    var kind : Int? = 0

    var duongdan : String? =""


    override fun doInBackground(vararg p0: Void?): Void? {
        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdantuspeak,
            Response.Listener { response ->
                var id: Int
                var tu: String
                var nghia: String
                var idlevel: Int
                var importains : Int
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
                            ListSpeakActivity.tuVunglistSpeak.add(TuVung(id, tu, nghia, idlevel,importains))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    Log.e("vxxcv", ListSpeakActivity.tuVunglistSpeak.toString())
                    ListSpeakActivity.listTuAdapteSpeak = ListTuAdapter(ListSpeakActivity.tuVunglistSpeak, ctx,3)
                    val layoutManager = LinearLayoutManager(ctx)
                    ListSpeakActivity.recyclerView.layoutManager = layoutManager
                    ListSpeakActivity.recyclerView.adapter = ListSpeakActivity.listTuAdapteSpeak

                }
            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["idlevelkey"] = idlevelSpeak
                return param
            }
        }
        requestQueue.add(stringRequest)


        return null
    }




}
