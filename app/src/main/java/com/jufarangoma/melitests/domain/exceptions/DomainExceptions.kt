package com.jufarangoma.melitests.domain.exceptions

open class DomainException(
    override val message: String = ""
) : Throwable(message)

object TimeOutException : DomainException()
object ParseException : DomainException()
object UnknownException : DomainException()
object Unauthorized : DomainException()

data class HttpErrorCode(
    val code: Int,
    override val message: String
) : DomainException()