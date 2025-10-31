package com.example.raceme.ui.badges

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.raceme.databinding.FragmentBadgesBinding
import com.example.raceme.ui.adapters.*
import com.example.raceme.data.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.ZoneId

class BadgesFragment: Fragment() {
    private var _b: FragmentBadgesBinding? = null
    private val b get() = _b!!
    private val repo = RaceRepo()
    private val adapter = BadgeAdapter()

    override fun onCreateView(i:LayoutInflater, c:ViewGroup?, s:Bundle?): View {
        _b = FragmentBadgesBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(v:View, s:Bundle?) {
        b.rvBadges.layoutManager = GridLayoutManager(requireContext(), 3)
        b.rvBadges.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            combine(repo.badgeDefs(), repo.runs()) { defs, runs ->
                defs.map { def ->
                    val earned = when (def.condition) {
                        "first_run" -> runs.isNotEmpty()
                        "distance_once" -> runs.any { it.distanceKm + 1e-6 >= (def.distanceKm ?: 0.0) }
                        "time_of_day" -> runs.any {
                            val hour = it.startAt.toDate().toInstant().atZone(ZoneId.systemDefault()).hour
                            hour < (def.beforeHour ?: 6)
                        }
                        else -> false
                    }
                    BadgeRow(def, earned)
                }
            }.collect { adapter.submit(it) }
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
