package com.dosei.games.toybank.test

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows

inline fun <reified T: Throwable> coAssertThrows(
    noinline executable: suspend () -> Unit
) = assertThrows(T::class.java) {
    runBlocking { executable() }
}

inline fun <reified T: Throwable> coAssertThrows(
    message: String,
    noinline executable: suspend () -> Unit
) = assertThrows(message, T::class.java) {
    runBlocking { executable() }
}