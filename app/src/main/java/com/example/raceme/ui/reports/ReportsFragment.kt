package com.example.raceme.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.raceme.R

class ReportsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvTitle = view.findViewById<TextView>(R.id.tvReportTitle)
        val tvSteps = view.findViewById<TextView>(R.id.tvSteps)
        val tvStreak = view.findViewById<TextView>(R.id.tvStreak)
        val btnWeekly = view.findViewById<Button>(R.id.btnWeekly)
        val btnMonthly = view.findViewById<Button>(R.id.btnMonthly)

        fun showWeekly() {
            tvTitle.text = getString(R.string.weekly_report)
            tvSteps.text = "Steps: 42,350"
            tvStreak.text = "Active days: 5 / 7"
        }

        fun showMonthly() {
            tvTitle.text = getString(R.string.monthly_report)
            tvSteps.text = "Steps: 176,420"
            tvStreak.text = "Active days: 19 / 30"
        }

        btnWeekly.setOnClickListener { showWeekly() }
        btnMonthly.setOnClickListener { showMonthly() }

        // default view
        showWeekly()
    }
}
