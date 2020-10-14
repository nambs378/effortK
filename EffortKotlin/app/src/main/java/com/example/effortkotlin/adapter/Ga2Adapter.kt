package com.example.effortkotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effortkotlin.Model.Grammar2
import com.example.effortkotlin.R
import com.example.effortkotlin.View.RenderPdfActivity

class Ga2Adapter(
    internal var ga2ModelList: List<Grammar2>,
    internal var context: Context
) : RecyclerView.Adapter<Ga2Adapter.Ga2Holder>() {

    class Ga2Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvGa2: TextView = itemView.findViewById(R.id.tv_ga2)
        var tvGa2Note:TextView = itemView.findViewById(R.id.tv_ga2_note)
        var contextsss: View =itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Ga2Holder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ga2, parent, false)

        return Ga2Holder(view)
    }

    override fun getItemCount(): Int {
        return ga2ModelList.size
    }

    override fun onBindViewHolder(holder: Ga2Holder, position: Int) {
        val ga2Model = ga2ModelList[position]

        holder.tvGa2.setText(ga2Model.nguphap)
        holder.tvGa2Note.setText(ga2Model.chitiet)

        holder.contextsss.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, RenderPdfActivity::class.java)
            intent.putExtra("Grammar", ga2Model.nguphap)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        })


    }


}