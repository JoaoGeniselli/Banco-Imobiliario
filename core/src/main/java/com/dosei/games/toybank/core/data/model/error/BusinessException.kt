package com.dosei.games.toybank.core.data.model.error

open class BusinessException(
    val code: String,
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)

val Throwable.businessMessage get() = (this as? BusinessException)?.message
val Throwable.businessCode get() = (this as? BusinessException)?.code
