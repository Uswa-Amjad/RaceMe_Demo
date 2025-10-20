package com.example.raceme.ui
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.raceme.R

class RacesFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View =
        i.inflate(R.layout.fragment_races, c, false)
}
