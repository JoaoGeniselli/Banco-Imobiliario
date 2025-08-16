package com.dosei.games.toybank.analytics.impl

import com.dosei.games.toybank.analytics.Analytics
import timber.log.Timber

internal object LogCatAnalytics : Analytics {

    private const val TAG = "Analytics"

    override fun log(event: String, properties: Map<String, String>) {
        val message = buildString {
            append("Event: ")
            append(event)

            if (properties.isNotEmpty()) {
                append(" | Properties: ")
                append(properties.toList().joinToString { x -> "${x.first} = ${x.second}" })
            }
        }
        Timber.tag(TAG).d(message)
    }
}