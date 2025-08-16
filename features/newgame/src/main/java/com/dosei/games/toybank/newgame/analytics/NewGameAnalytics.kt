package com.dosei.games.toybank.newgame.analytics

import com.dosei.games.toybank.analytics.AnalyticsFeature

internal object NewGameAnalytics : AnalyticsFeature("new_game") {

    val clickSettings = click("settings")
    val clickStart = click("start")
    val addPlayer = click("add")
    val removePlayer = event("remove")
}