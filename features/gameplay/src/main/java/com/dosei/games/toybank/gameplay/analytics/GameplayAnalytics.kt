package com.dosei.games.toybank.gameplay.analytics

import com.dosei.games.toybank.analytics.AnalyticsFeature

internal object GameplayAnalytics : AnalyticsFeature("gameplay") {

    object Winner : AnalyticsPage("winner") {
        val back = click("home")
    }

    val clickHistory = click("history")
    val clickPlayer = click("player")
}