package com.example.effortkotlin.adapter

import android.content.Context
import android.os.AsyncTask
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
import com.example.effortkotlin.Model.TransVoca
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListActivity.Companion.idlevel
import com.example.effortkotlin.View.ListActivity.Companion.kind
import com.example.effortkotlin.database.QuizDBHelper
import com.example.effortkotlin.factory.Server
import java.util.HashMap

class TransListAdapter(
    private val transVocaList: List<TransVoca>,
    private val context: Context
) : RecyclerView.Adapter<TransListAdapter.TranListHolder>() {

    lateinit var quizDBHelper: QuizDBHelper


    class TranListHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvVoca: TextView = itemView.findViewById(R.id.tv_vocanoti)
        var tvMean:TextView = itemView.findViewById(R.id.tv_mean)
        var imgLight: ImageView = itemView.findViewById(R.id.iv_light_translist);
        var contextsss: View =itemView

//        var likeButton: LikeButton = itemView.findViewById(R.id.star_button);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranListHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translist, parent, false)

        return TranListHolder(view)    }

    override fun getItemCount(): Int {
        return transVocaList.size
    }

    override fun onBindViewHolder(holder: TranListHolder, position: Int) {
        val transVoca = transVocaList[position]
        holder.tvVoca.setText(transVoca.vocat)
        holder.tvMean.setText(transVoca.mean)


        if (transVoca.importants == 1) {
            holder.imgLight!!.setImageResource(R.drawable.like_star)
        } else if (transVoca.importants == 0) {
            holder.imgLight!!.setImageResource(R.drawable.unlike_star)
        }


        holder.imgLight?.setOnClickListener(View.OnClickListener {
            if (transVoca.importants == 1) {

                holder.imgLight!!.setImageResource(R.drawable.unlike_star)
                val updatetrans = Updatestartrans(context)
                updatetrans.execute("0",transVoca.vocat)

            } else if (transVoca.importants== 0) {

                holder.imgLight!!.setImageResource(R.drawable.like_star)
                val updatetrans2 = Updatestartrans(context)
                updatetrans2.execute("1",transVoca.vocat)

            }
        })



    }

    fun getTranAt(position: Int): String? {
        return transVocaList[position].vocat
    }


}


class Updatestartrans(internal var ctx: Context) : AsyncTask<String, Void, Void>(){

    var importains : String ? = ""
    var tuvung : String ? = ""


    override fun doInBackground(vararg p0: String?): Void? {

        importains = p0[0]
        tuvung = p0[1]


        Log.e("zxczxcz"," impro: $importains   tu uvng: $tuvung ")

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdanupdatetrans ,
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
                param["vocatrans"] = tuvung

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