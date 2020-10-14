package com.example.effortkotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.effortkotlin.Model.GrammarKind
import com.example.effortkotlin.R
import com.example.effortkotlin.View.Ga2Activity
import com.example.effortkotlin.View.RenderPdfActivity

class GrammarKindAdapter(
    val context: FragmentActivity?, private val grammarKindList: List<GrammarKind>,
    internal var kind: String
) : RecyclerView.Adapter<GrammarKindAdapter.GrammarKindHolder>() {

    class GrammarKindHolder(itemView : View) :  RecyclerView.ViewHolder(itemView) {
        var tvKind: TextView = itemView.findViewById(R.id.tv_grammar_kind);
        var tvNote:TextView = itemView.findViewById(R.id.tv_grammar_note);
        var contextsss: View = itemView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrammarKindHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grammar_kind, parent, false)
        return GrammarKindHolder(view)
    }

    override fun getItemCount(): Int {
        return grammarKindList.size
    }

    override fun onBindViewHolder(holder: GrammarKindHolder, position: Int) {

        val grammarKind = grammarKindList[position]


        holder.tvKind.setText(grammarKind.getGrammar1())
        holder.tvNote.setText(grammarKind.getGrammarNote1())


        holder.contextsss.setOnClickListener {
            if (grammarKind.getPdf() == 1) {
                val intent = Intent(context, Ga2Activity::class.java)
//                intent.putExtra("kind", grammarKind.grammar1)
                intent.putExtra("Grammar", grammarKind.getGrammar1())
                context?.startActivity(intent)
            } else if (grammarKind.getPdf() == 0) {
                val intent = Intent(context, RenderPdfActivity::class.java)
                intent.putExtra("Grammar", grammarKind.getGrammar1())
                context?.startActivity(intent)
            }
        }



    }



}