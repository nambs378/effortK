package com.example.effortkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effortkotlin.Model.NotiVoca
import com.jackandphantom.androidlikebutton.AndroidLikeButton
import android.widget.Toast
import com.example.effortkotlin.R
import com.example.effortkotlin.database.QuizDBHelper


class NotiAdapter(
    private var notiVocaList: MutableList<NotiVoca>,
    private val context: Context) : RecyclerView.Adapter<NotiAdapter.NotiHolder>(){
    lateinit var quizDBHelper: QuizDBHelper


    class NotiHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var tvVoca: TextView = itemView.findViewById(com.example.effortkotlin.R.id.tv_vocanoti)
        var tvMean:TextView = itemView.findViewById(com.example.effortkotlin.R.id.tv_mean)
        var imgLight: ImageView = itemView.findViewById(R.id.iv_light);
        var contextsss: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(com.example.effortkotlin.R.layout.item_notivoca, parent, false)
        return NotiHolder(view)
    }

    override fun getItemCount(): Int {
        return notiVocaList.size
    }

    override fun onBindViewHolder(holder: NotiHolder, position: Int) {

        val notiVoca = notiVocaList[position]
        quizDBHelper = QuizDBHelper(context)

        holder.tvVoca.setText(notiVoca.getVocat())
        holder.tvMean.setText(notiVoca.getMean())


        if (notiVoca.getImportants() === 1) {
            holder.imgLight!!.setImageResource(R.drawable.like_star)
        } else if (notiVoca.getImportants() === 0) {
            holder.imgLight!!.setImageResource(R.drawable.unlike_star)
        }


        holder.imgLight?.setOnClickListener(View.OnClickListener {
            if (notiVoca.getImportants() === 1) {
                notiVoca.getVocat()?.let { it1 -> quizDBHelper.updateLightVoca(it1, 0) }
                holder.imgLight!!.setImageResource(R.drawable.unlike_star)

                notiVocaList.clear()
                notiVocaList = quizDBHelper.getAllNoti()
                notifyDataSetChanged()


            } else if (notiVoca.getImportants() === 0) {
                notiVoca.getVocat()?.let { it1 -> quizDBHelper.updateLightVoca(it1, 1) }
                holder.imgLight!!.setImageResource(R.drawable.like_star)

                notiVocaList.clear()
                notiVocaList = quizDBHelper.getAllNoti()
                notifyDataSetChanged()


            }
        })


    }

    fun getNotiAt(position: Int): String? {
        return notiVocaList[position].getVocat()
    }


}