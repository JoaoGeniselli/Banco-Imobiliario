package com.dosei.games.toybank.core.toolbox

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date
import java.util.TimeZone

class TimeUtilsTest {

    @Test
    fun `check long to date conversion`() {
        assertEquals(
            Date(5150L),
            5150L.toDate()
        )
    }

    @Test
    fun `check custom pattern format`() {
        assertEquals(
            "12/08/25 18:30",
            Date(1755034215000L).format(
                pattern = "dd/MM/yy HH:mm",
                timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
            )
        )
    }

    @Test
    fun `check default pattern format`() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))
        assertEquals(
            "12/08/2025 18:30:15",
            Date(1755034215000L).format()
        )
    }
}