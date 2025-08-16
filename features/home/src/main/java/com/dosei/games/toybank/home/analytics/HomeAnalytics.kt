package com.dosei.games.toybank.home.analytics

import com.dosei.games.toybank.analytics.AnalyticsFeature

internal object HomeAnalytics : AnalyticsFeature("home") {
    val clickNewGame = click("new_game")
    val clickContinue = click("continue")
}