package com.dosei.games.toybank.core.toolbox

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"

fun Long.toDate() = Date(this)

fun Date.format(
    pattern: String = DATE_TIME_FORMAT,
    locale: Locale = localeBr,
    timeZone: TimeZone = TimeZone.getDefault()
): String =
    SimpleDateFormat(pattern, locale)
        .apply { this.timeZone = timeZone }
        .format(this)