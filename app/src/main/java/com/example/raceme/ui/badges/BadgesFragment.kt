package com.example.raceme.ui.badges

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.raceme.R
import com.example.raceme.profile.data.UserProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BadgesFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        return i.inflate(R.layout.fragment_badges, c, false)
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        val rv = v.findViewById<RecyclerView>(R.id.rvBadges)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)

        CoroutineScope(Dispatchers.Main).launch {
            val profile = try { UserProfileRepository().load() } catch (_: Exception) { null }
            val hasGoal = (profile?.dailyGoal ?: 0) >= 1000
            val hasUsername = !profile?.username.isNullOrBlank()
            val hasPhoto = !profile?.photoUrl.isNullOrBlank()

            val data = listOf(
                Badge("Set a goal", hasGoal),
                Badge("Picked a username", hasUsername),
                Badge("Profile pic", hasPhoto),
                Badge("First Steps", false),   // placeholders for demo
                Badge("Consistency", false),
                Badge("Champion", false)
            )
            rv.adapter = BadgesAdapter(data)
        }
    }
}
