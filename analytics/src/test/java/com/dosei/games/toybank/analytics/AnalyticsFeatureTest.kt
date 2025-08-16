package com.dosei.games.toybank.analytics

import org.junit.Assert.assertEquals
import org.junit.Test

class AnalyticsFeatureTest {

    @Test
    fun `check analytics feature logic`() {
        val feature = AnalyticsFeature("feature")

        assertEquals("feature", feature.display)
        assertEquals("feature_click_button", feature.click("button"))
        assertEquals("feature_error", feature.error)
        assertEquals("feature_build_event", feature.event("build", "event"))
    }

    @Test
    fun `check analytics page logic`() {
        val feature = object : AnalyticsFeature("feature") {
            val page = AnalyticsPage("page")
        }
        assertEquals("feature_page", feature.page.display)
        assertEquals("feature_page_click_button", feature.page.click("button"))
        assertEquals("feature_page_error", feature.page.error)
        assertEquals("feature_page_build_event", feature.page.event("build", "event"))
    }
}