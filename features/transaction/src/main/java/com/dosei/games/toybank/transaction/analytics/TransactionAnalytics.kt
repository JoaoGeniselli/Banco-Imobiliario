package com.dosei.games.toybank.transaction.analytics

import com.dosei.games.toybank.analytics.AnalyticsFeature

object TransactionAnalytics : AnalyticsFeature("transaction") {

    object Beneficiary : AnalyticsPage("beneficiary") {
        val select = event("select")
    }

    object Amount : AnalyticsPage("amount") {
        val confirm = click("confirm")
    }

    object Type : AnalyticsPage("type") {
        fun selectType(type: Char) = click("$type")
    }
}