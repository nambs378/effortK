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
import com.example.effortkotlin.Model.LevelGiaoTiep
import com.example.effortkotlin.R
import com.example.effortkotlin.View.ListActivity
import com.squareup.picasso.Picasso

class LevelGiaoTiepAdapter(
    private val levelGiaoTiepList: List<LevelGiaoTiep>,
    private val context: Context,
    private val kind: Int
) : RecyclerView.Adapter<LevelGiaoTiepAdapter.LevelGiaoTiepHolder>() {

    class LevelGiaoTiepHolder(itemView : View) :RecyclerView.ViewHolder(itemView)  {
        var tvHienthi: TextView = itemView.findViewById(R.id.tv_level);
        var tvChude:TextView = itemView.findViewById(R.id.tv_chude);
        var btGoList: TextView = itemView.findViewById(R.id.bt_goList);
        var imgRet: ImageView = itemView.findViewById(R.id.image_rT);
        var imgLevel:ImageView = itemView.findViewById(R.id.panorama_image_view);


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelGiaoTiepHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_level, parent, false)

        return LevelGiaoTiepHolder(view)
    }

    override fun getItemCount(): Int {
        return levelGiaoTiepList.size
    }

    override fun onBindViewHolder(holder: LevelGiaoTiepHolder, position: Int) {
        val levelGiaoTiep = levelGiaoTiepList[position]
//        holder.tvHienthi.text = "Level " + levelGiaoTiep.getIdlevel()
        holder.tvChude.text = levelGiaoTiep.getTitlelevel()
        Picasso.get().load(levelGiaoTiep.getImagelevel()).into(holder.imgLevel)

        if (levelGiaoTiep.getLevelstate() == 1) {
            holder.imgRet.setImageResource(R.drawable.green_rs)
        } else if (levelGiaoTiep.getLevelstate() == 2) {
            holder.imgRet.setImageResource(R.drawable.yellow)
        } else if (levelGiaoTiep.getLevelstate() == 3) {
            holder.imgRet.setImageResource(R.drawable.red_rs)
        } else if (levelGiaoTiep.getLevelstate() == 0) {
            holder.imgRet.setImageResource(R.drawable.gray_rs)
        }

        holder.btGoList.setOnClickListener {
            val intent = Intent(context, ListActivity::class.java)
            val bundle = Bundle()
            bundle.putString("imageLevel", levelGiaoTiep.getImagelevel())
            bundle.putString("title", levelGiaoTiep.getTitlelevel())
            bundle.putString("idlevel", levelGiaoTiep.getIdlevel().toString() + "")
            bundle.putInt("kind", kind)

            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }


}