package com.example.raceme.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.raceme.R
import com.example.raceme.data.ChallengeRow

class ChallengeAdapter(private val list: List<ChallengeRow>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeVH>() {

    class ChallengeVH(v: View) : RecyclerView.ViewHolder(v) {
        private val title = v.findViewById<TextView>(R.id.tvTitle)
        private val desc  = v.findViewById<TextView>(R.id.tvDesc)
        private val bar   = v.findViewById<ProgressBar>(R.id.progress)
        private val left  = v.findViewById<TextView>(R.id.tvProgressLeft)
        private val right = v.findViewById<TextView>(R.id.tvProgressRight)

        fun bind(row: ChallengeRow) {
            title.text = row.def.title
            desc.text  = row.def.description
            bar.progress = row.percent.coerceIn(0, 100)
            left.text  = row.label
            right.text = row.extraLabel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ChallengeVH(view)
    }

    override fun onBindViewHolder(holder: ChallengeVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}




