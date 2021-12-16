package com.jgeniselli.banco.core

import java.util.*

class Now {

    suspend operator fun invoke(): Date = Date()
}