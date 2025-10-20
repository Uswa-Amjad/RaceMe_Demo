package com.example.raceme.ui.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.raceme.R
import com.example.raceme.profile.data.UserProfileRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuotesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tv = view.findViewById<TextView>(R.id.tvQuote)

        // Show something immediately (so it’s never blank)
        tv.text = getString(R.string.quotes_title) + "…"

        viewLifecycleOwner.lifecycleScope.launch {
            val profile = try { UserProfileRepository().load() } catch (_: Exception) { null }

            val unlocked = listOf(
                (profile?.dailyGoal ?: 0) >= 1000,
                !profile?.username.isNullOrBlank(),
                !profile?.photoUrl.isNullOrBlank()
            ).count { it }

            val base = listOf(
                "Every step counts — even tiny ones.",
                "You don’t need to be fast, just consistent.",
                "Future you is already proud."
            )
            val mid = listOf(
                "You’re building momentum — keep stacking wins.",
                "Discipline > motivation. You’re doing it.",
                "Trust the process. Steps become streaks."
            )
            val pro = listOf(
                "You’re the main character. Roll credits on excuses.",
                "Legend status loading… keep stepping.",
                "Consistency is undefeated. You’re proof."
            )

            val pool = when {
                unlocked >= 3 -> pro
                unlocked == 2 -> mid
                else -> base
            }

            tv.text = pool[Random.nextInt(pool.size)]
        }
    }
}
