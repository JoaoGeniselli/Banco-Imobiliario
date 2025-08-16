package com.dosei.games.toybank.analytics.impl

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test
import timber.log.Timber

class LogCatAnalyticsTest {


    @Test
    fun `check event logs`() {
        val testTree = TestTree()
        Timber.plant(testTree)

        LogCatAnalytics.log(event = "my_event")
        assertEquals(
            TestTree.Log(
                priority = Log.DEBUG,
                tag = "Analytics",
                message = "Event: my_event",
                t = null
            ),
            testTree.logs.last()
        )

        LogCatAnalytics.log(
            event = "add_to_cart",
            properties = mapOf("product" to "15", "amount" to "3")
        )
        assertEquals(
            TestTree.Log(
                priority = Log.DEBUG,
                tag = "Analytics",
                message = "Event: add_to_cart | Properties: product = 15, amount = 3",
                t = null
            ),
            testTree.logs.last()
        )
    }
}