package com.example.raceme.quotes

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.random.Random

// ---- Triggers you can ask for ----
enum class QuoteTrigger {
    ON_LOGIN,
    MORNING, AFTERNOON, EVENING,
    WEEKEND,
    STREAK_7, STREAK_30,
    ZERO_STEPS_TODAY,
    POST_WORKOUT
}

// ---- WHEN WHERE WHY ----
data class UserState(
    val stepsToday: Int = 0,
    val streakDays: Int = 0,
    val now: LocalDateTime = LocalDateTime.now(),
    val zoneId: ZoneId = ZoneId.systemDefault(),
    val justWorkedOut: Boolean = false,
    val justLoggedIn: Boolean = false
)

// ---- QUOTE MODEL ----
data class Quote(
    val id: String,
    val text: String,
    val author: String = "RaceMe",
    val tags: Set<String> = emptySet(),
    val triggers: Set<QuoteTrigger> = emptySet()
)

// ---- QUOTE REPOSITORY --TEMP--

object QuoteRepo {
    val quotes: List<Quote> = listOf(
        Quote(
            id = "q_morn_1",
            text = "Small steps this morning lead to big wins tonight.",
            triggers = setOf(QuoteTrigger.MORNING)
        ),
        Quote(
            id = "q_morn_2",
            text = "Start soft. Stay steady. Finish strong.",
            triggers = setOf(QuoteTrigger.MORNING)
        ),
        Quote(
            id = "q_noon_1",
            text = "Halfway through the day, not halfway through your power.",
            triggers = setOf(QuoteTrigger.AFTERNOON)
        ),
        Quote(
            id = "q_eve_1",
            text = "Progress > Perfection — wrap the day proud.",
            triggers = setOf(QuoteTrigger.EVENING)
        ),
        Quote(
            id = "q_login_1",
            text = "Welcome back! Today’s steps are waiting for you.",
            triggers = setOf(QuoteTrigger.ON_LOGIN)
        ),
        Quote(
            id = "q_login_2",
            text = "Logged in = locked in. Let’s go.",
            triggers = setOf(QuoteTrig
