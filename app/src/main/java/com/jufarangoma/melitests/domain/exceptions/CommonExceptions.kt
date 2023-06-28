package com.jufarangoma.melitests.domain.exceptions

import com.google.gson.JsonParseException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class CommonErrors {

    fun manageCommonException(throwable: Throwable): DomainException {
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> DomainException(throwable.message ?: String())
            is JsonParseException -> ParseException
            else -> UnknownException
        }
    }
}
