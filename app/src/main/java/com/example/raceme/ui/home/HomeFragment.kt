if (stats.cumulativeSteps >= 1_000 && "first_1k" !in earned) {
    toAward += BadgeAward(BadgeCatalog.FIRST_1K)
}

// 2) 7-day and 30-day streaks
if (stats.currentStreakDays >= 7 && "streak_7" !in earned) {
    toAward += BadgeAward(BadgeCatalog.STREAK_7)
}
if (stats.currentStreakDays >= 30 && "streak_30" !in earned) {
    toAward += BadgeAward(BadgeCatalog.STREAK_30)
}

// 3) Early Riser: first steps before 09:00 local time
if (stats.firstStepsAt != null &&
stats.firstStepsAt.isBefore(LocalTime.of(9, 0)) &&
"early_riser" !in earned
) {
    toAward += BadgeAward(BadgeCatalog.EARLY_RISER)
}

val dow = stats.timestamp.dayOfWeek
val isWeekend = (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY)
if (isWeekend && stats.stepsToday >= 8_000 && "weekend_warrior" !in earned) {
    toAward += BadgeAward(BadgeCatalog.WEEKEND_WARRIOR)
}

// 5) Marathon Mindset: 42,195 lifetime steps
if (stats.cumulativeSteps >= 42_195 && "marathon_mindset" !in earned) {
    toAward += BadgeAward(BadgeCatalog.MARATHON_MINDSET)
}

// 6) Profile Complete
if (stats.hasDisplayName && stats.hasBio && stats.hasPhoto && "profile_complete" !in earned) {
    toAward += BadgeAward(BadgeCatalog.PROFILE_COMPLETE)
}

// Persist any newly earned
if (toAward.isNotEmpty()) {
    val updated = earned + toAward.map { it.badge.id }
    BadgeStore.saveEarnedIds(context, stats.userId, updated)
}

return toAward
}