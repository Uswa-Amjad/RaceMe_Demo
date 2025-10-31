package com.example.raceme.ui.Challenges   // <- make sure this matches your folder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raceme.data.ChallengeDef
import com.example.raceme.data.ChallengeRow
import com.example.raceme.databinding.FragmentChallengesBinding
import com.example.raceme.ui.adapters.ChallengeAdapter

class ChallengesFragment : Fragment() {

    private var _b: FragmentChallengesBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _b = FragmentChallengesBinding.inflate(inflater, container, false)   // <-- correct
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temporary mock data so you can see multiple rows
        val mock = listOf(
            ChallengeRow(
                ChallengeDef("run5k", "Run 5K", "Finish a 5 km run", "distance_once", distanceKm = 5.0),
                "3/5 km", "4/7 days", 60
            ),
            ChallengeRow(
                ChallengeDef("streak7", "7-Day Streak", "Run daily for a week", "daily_streak", days = 7),
                "4/7 days", "", 57
            ),
            ChallengeRow(
                ChallengeDef("weekendWarrior", "Weekend Warrior", "Run on weekends", "weekend_count", weekends = 4),
                "1/4 weekends", "", 25
            ),
            ChallengeRow(
                ChallengeDef("friendRun", "Run with a Friend", "Complete a social run", "distance_once", distanceKm = 3.0),
                "0/3 km", "", 0
            )
        )

        b.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        b.rvChallenges.adapter = ChallengeAdapter(mock)  // mock has 4 items
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}


