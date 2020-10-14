package com.example.effortkotlin.adapter

import android.content.Context
import android.os.AsyncTask
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListActivity.Companion.kind
import com.example.effortkotlin.factory.Server
import java.util.*

class ListTuSpeakAdapter(private val tuVungList: MutableList<TuVung>,
                         private val contexts: Context ) : RecyclerView.Adapter<ListTuSpeakAdapter.ListTuSpeakHolder>() {

    lateinit var mTTS: TextToSpeech


    class ListTuSpeakHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTuhienthi: TextView = itemView.findViewById(R.id.tv_qt);
        var tvNghia: TextView = itemView.findViewById(R.id.tv_nghia);
        var imgLight: ImageView = itemView.findViewById(R.id.iv_light_listquest);
        var contextsss: View =itemView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTuSpeakHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rvquestion, parent, false)
        return ListTuSpeakHolder(view)
    }

    override fun getItemCount(): Int {
        return tuVungList.size
    }

    override fun onBindViewHolder(holder: ListTuSpeakHolder, position: Int) {
        val tuVung = tuVungList[position]

        holder.tvTuhienthi.text = tuVung.getTu()
        holder.tvNghia.text = tuVung.getNghia()

        mTTS = TextToSpeech(contexts, TextToSpeech.OnInitListener { status ->
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


        if (tuVung.getImportants() === 1) {
            holder.imgLight!!.setImageResource(R.drawable.like_star)
        } else if (tuVung.getImportants() === 0) {
            holder.imgLight!!.setImageResource(R.drawable.unlike_star)
        }

        holder.imgLight?.setOnClickListener(View.OnClickListener {
            if (tuVung.getImportants() === 1) {

                holder.imgLight!!.setImageResource(R.drawable.unlike_star)
                val updatestarlist = UpdatestarlistSpeaking(contexts)
                updatestarlist.execute("$kind","${tuVung.getIdlevel()}","${tuVung.getTu()}","0")



            } else if (tuVung.getImportants() === 0) {

                holder.imgLight!!.setImageResource(R.drawable.like_star)
                val updatestarlist2 = UpdatestarlistSpeaking(contexts)
                updatestarlist2.execute("$kind","${tuVung.getIdlevel()}","${tuVung.getTu()}","1")




            }
        })


        holder.contextsss.setOnClickListener {
            mTTS.setPitch(1.05f)
            mTTS.setSpeechRate(0.88f)
            mTTS.speak(tuVung.getTu(), TextToSpeech.QUEUE_FLUSH, null)

        }


    }

}


class UpdatestarlistSpeaking(internal var ctx: Context) : AsyncTask<String, Void, Void>() {

    var idlevel: String? = ""
    var importains: String? = ""
    var tuvung: String? = ""
    var kind: Int? = 0

    var duongdan: String? = ""

    override fun doInBackground(vararg p0: String?): Void? {
        kind = p0[0]?.toInt()

        idlevel = p0[1]
        importains = p0[3]
        tuvung = p0[2]

        if (kind == 1) {
            duongdan = Server.DuongdanupdateInportainsTugiaotiep
        } else if (kind == 2) {
            duongdan = Server.DuongdanupdateInportainsTuvung
        }

        Log.e("zxczxcz", "kind : $kind  idlevel : $idlevel  impro: $importains   tu uvng: $tuvung $duongdan")

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, duongdan,
            Response.Listener { response ->

                Log.e("zxczxcz", response.toString())

//                TranslateActivity.transVocaList.clear()
//
//                TranslateActivity.setAdapterr(ctx)

            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["importants"] = importains
                param["idlevel"] = idlevel
                param["tuvung"] = tuvung

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