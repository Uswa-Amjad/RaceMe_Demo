package com.example.raceme.ui.adapters
import android.view.*; import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.raceme.R
import com.example.raceme.data.BadgeDef

data class BadgeRow(val def: BadgeDef, val earned:Boolean)

class BadgeAdapter: RecyclerView.Adapter<BadgeVH>() {
    private val rows = mutableListOf<BadgeRow>()
    fun submit(list: List<BadgeRow>) { rows.clear(); rows.addAll(list); notifyDataSetChanged() }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        BadgeVH(LayoutInflater.from(p.context).inflate(R.layout.item_badge, p, false))
    override fun onBindViewHolder(h: BadgeVH, i: Int) = h.bind(rows[i])
    override fun getItemCount() = rows.size
}

class BadgeVH(v: View): RecyclerView.ViewHolder(v) {
    private val title = v.findViewById<TextView>(R.id.tvBadgeTitle)
    private val state = v.findViewById<TextView>(R.id.tvBadgeState)
    private val icon  = v.findViewById<TextView>(R.id.tvBadgeIcon)
    fun bind(row: BadgeRow) {
        title.text = row.def.title
        icon.text = if (row.earned) "🏆" else "🔒"
        state.text = if (row.earned) "Unlocked" else "Locked"
    }
}
