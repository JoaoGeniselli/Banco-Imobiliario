package com.dosei.games.toybank.analytics

import org.junit.Assert.assertEquals
import org.junit.Test

class AnalyticsEventBuilderTest {

    @Test
    fun `check events build`() {
        assertEquals(
            "login",
            buildEvent("login")
        )
        assertEquals(
            "login_success",
            buildEvent("login", "success")
        )
        assertEquals(
            "login_click_access",
            buildEvent("login", "click", "access")
        )
        assertEquals(
            "login_access",
            buildEvent("login", "", "access")
        )
    }
}