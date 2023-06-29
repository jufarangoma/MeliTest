package com.jufarangoma.melitests.domain.exceptions

import android.util.Log
import com.google.gson.JsonParseException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class CommonErrors {

    fun manageCommonException(throwable: Throwable): DomainException {
        Log.e(
            "OTHER_EXCEPTION",
            "${throwable.message}",
            throwable
        )
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> DomainException(throwable.message ?: String())
            is JsonParseException -> ParseException
            else -> UnknownException
        }
    }
}
