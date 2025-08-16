package com.dosei.games.toybank.analytics

open class AnalyticsFeature(val feature: String) {

    open inner class AnalyticsPage(val page: String) {
        val display = buildEvent(feature, page)
        val error = buildEvent(feature, page, TAG_ERROR)

        fun click(element: String) = buildEvent(feature, page, TAG_CLICK, element)
        fun event(vararg appends: String) = buildEvent(feature, page, *appends)
    }

    val display = buildEvent(feature)
    val error = buildEvent(feature, TAG_ERROR)

    fun click(element: String) = buildEvent(feature, TAG_CLICK, element)
    fun event(vararg appends: String) = buildEvent(feature, *appends)

    companion object {
        const val TAG_CLICK = "click"
        const val TAG_ERROR = "error"
    }
}