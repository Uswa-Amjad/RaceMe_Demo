package com.example.raceme.ui.badges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.raceme.R

class BadgesAdapter(private val items: List<Badge>) :
    RecyclerView.Adapter<BadgesAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.ivBadge)
        val label: TextView = v.findViewById(R.id.tvBadgeName)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_badge, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, i: Int) {
        val b = items[i]
        h.label.text = b.name
        h.icon.setImageResource(if (b.unlocked) R.drawable.ic_person_24 else R.drawable.ic_person_24)
        h.icon.alpha = if (b.unlocked) 1f else 0.3f
    }

    override fun getItemCount() = items.size
}
