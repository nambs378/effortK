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
import com.example.effortkotlin.View.ListActivity
import com.example.effortkotlin.View.ListActivity.Companion.mTTS
import com.example.effortkotlin.View.ListSpeakActivity
import com.example.effortkotlin.View.TranslateActivity
import com.example.effortkotlin.asyn.DeleteItemTrans
import com.example.effortkotlin.factory.Server
import java.util.*


class ListTuAdapter(
    private val tuVungList: MutableList<TuVung>,
    private val contexts: Context,
    private val kind : Int
) : RecyclerView.Adapter<ListTuAdapter.ListtuHolder>() {



    class ListtuHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var tvTuhienthi: TextView = itemView.findViewById(R.id.tv_qt);
        var tvNghia:TextView = itemView.findViewById(R.id.tv_nghia);
        var imgLight: ImageView = itemView.findViewById(R.id.iv_light_listquest);
        var contextsss: View =itemView;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListtuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rvquestion, parent, false)
        return ListtuHolder(view)
    }

    override fun getItemCount(): Int {
        return tuVungList.size
    }

    override fun onBindViewHolder(holder: ListtuHolder, position: Int) {
        val tuVung = tuVungList[position]

        holder.tvTuhienthi.text = tuVung.getTu()
        holder.tvNghia.text = tuVung.getNghia()



        if (tuVung.getImportants() === 1) {
            holder.imgLight!!.setImageResource(R.drawable.like_star)
        } else if (tuVung.getImportants() === 0) {
            holder.imgLight!!.setImageResource(R.drawable.unlike_star)
        }

        holder.imgLight?.setOnClickListener(View.OnClickListener {
            if (tuVung.getImportants() === 1) {

                holder.imgLight!!.setImageResource(R.drawable.unlike_star)
                val updatestarlist = Updatestarlist(contexts)
                updatestarlist.execute("$kind","${tuVung.getIdlevel()}","${tuVung.getTu()}","0")



            } else if (tuVung.getImportants() === 0) {

                holder.imgLight!!.setImageResource(R.drawable.like_star)
                val updatestarlist2 = Updatestarlist(contexts)
                updatestarlist2.execute("$kind","${tuVung.getIdlevel()}","${tuVung.getTu()}","1")

            }
        })


        holder.contextsss.setOnClickListener {

            if (kind==3){

                ListSpeakActivity.mTTS1.setPitch(1.05f)
                ListSpeakActivity.mTTS1.setSpeechRate(0.88f)
                ListSpeakActivity.mTTS1.speak(tuVung.getTu(), TextToSpeech.QUEUE_FLUSH, null)

            } else if (kind==1 || kind ==2){

                ListActivity.mTTS.setPitch(1.05f)
                ListActivity.mTTS.setSpeechRate(0.88f)
                ListActivity.mTTS.speak(tuVung.getTu(), TextToSpeech.QUEUE_FLUSH, null)

            }

        }







    }



}

class Updatestarlist(internal var ctx: Context) : AsyncTask<String, Void, Void>(){

    var idlevel : String?= ""
    var importains : String ? = ""
    var tuvung : String ? = ""
    var kind : Int? = 0

    var duongdan : String? =""

    override fun doInBackground(vararg p0: String?): Void? {
        kind =  p0[0]?.toInt()

        idlevel = p0[1]
        importains = p0[3]
        tuvung = p0[2]

        if (kind==1){
            duongdan = Server.DuongdanupdateInportainsTugiaotiep
        } else if (kind == 2) {
            duongdan = Server.DuongdanupdateInportainsTuvung
        } else if (kind == 3) {
            duongdan = Server.DuongdanupdateInportainsTuSpeak
        }


        Log.e("zxczxcz","kind : $kind  idlevel : $idlevel  impro: $importains   tu uvng: $tuvung $duongdan")

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, duongdan ,
            Response.Listener { response ->

                Log.e("zxczxcz",response.toString())

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

