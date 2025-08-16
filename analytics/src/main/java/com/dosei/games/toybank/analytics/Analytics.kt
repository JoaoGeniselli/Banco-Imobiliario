package com.dosei.games.toybank.analytics

import com.dosei.games.toybank.analytics.impl.LogCatAnalytics

interface Analytics {

    fun log(event: String, properties: Map<String, String> = emptyMap())

    companion object {
        fun getInstance(): Analytics = LogCatAnalytics
    }
}