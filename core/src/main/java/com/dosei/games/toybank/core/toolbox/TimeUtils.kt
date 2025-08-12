package com.dosei.games.toybank.core.toolbox

import java.text.SimpleDateFormat
import java.util.Date

const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"

fun now() = Date().time

fun Long.toDate() = Date(this)

fun Date.format(pattern: String = DATE_TIME_FORMAT) =
    SimpleDateFormat(pattern, localeBr).format(this)