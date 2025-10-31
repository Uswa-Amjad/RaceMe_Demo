package com.example.raceme.data
import com.google.firebase.Timestamp

data class Run(
    val id: String = "",
    val startAt: Timestamp = Timestamp.now(),
    val durationSec: Int = 0,
    val distanceKm: Double = 0.0,
    val paceMinPerKm: Double = 0.0,
    val isRace: Boolean = false
)

data class ChallengeDef(
    val id:String="", val title:String="", val description:String="",
    val type:String="",                // distance_once | daily_streak | weekend_count
    val distanceKm: Double?=null, val days:Int?=null, val weekends:Int?=null
)

data class BadgeDef(
    val id:String="", val title:String="", val condition:String="", // first_run | distance_once | time_of_day
    val distanceKm: Double?=null, val paceLtMinPerKm: Double?=null, val beforeHour:Int?=null
)
