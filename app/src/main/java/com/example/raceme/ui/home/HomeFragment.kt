package com.example.raceme.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.raceme.R
import com.example.raceme.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHomeBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(v: View, s: Bundle?) {
        val nav = findNavController()
        b.btnChallenges.setOnClickListener { nav.navigate(R.id.challengesFragment) }
        b.btnBadges.setOnClickListener { nav.navigate(R.id.badgesFragment) }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
