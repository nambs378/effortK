package com.example.effortkotlin.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effortkotlin.Model.LevelSpeak
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListSpeakActivity
import com.squareup.picasso.Picasso

class LevelspAdapter(private val levelspeakList: List<LevelSpeak>,
                     private val context: Context ) : RecyclerView.Adapter<LevelspAdapter.LevelspHolder>() {


    class LevelspHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvChude: TextView = itemView.findViewById(R.id.tv_chude_speak);
        var tvDescription: TextView = itemView.findViewById(R.id.tv_description);
//        var imgRet: ImageView = itemView.findViewById(R.id.image_rT_speak);
        var imgLevel: ImageView = itemView.findViewById(R.id.panorama_image_view_speak);
        var contextsss: View =itemView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelspHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_levelspeak, parent, false)
        return LevelspHolder(view)
    }

    override fun getItemCount(): Int {
        return levelspeakList.size
    }

    override fun onBindViewHolder(holder: LevelspHolder, position: Int) {
        var levelSpeak : LevelSpeak = levelspeakList[position]
        holder.tvChude.text = levelSpeak.Title
        holder.tvDescription.text = levelSpeak.Description

        Picasso.get()
            .load(levelSpeak
                .Imagelevel)
            .into(holder.imgLevel)


        holder.contextsss.setOnClickListener {
            var intent = Intent(context, ListSpeakActivity::class.java)
            val bundle = Bundle()
            bundle.putString("idlevel", levelSpeak.Idlevel?.toString()  +"")
            bundle.putString("imageLevel", levelSpeak.Imagelevel)
            bundle.putString("title", levelSpeak.Title)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }



    }



}