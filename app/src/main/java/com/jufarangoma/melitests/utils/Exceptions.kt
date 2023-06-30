package com.jufarangoma.melitests.utils

import android.util.Log
import com.google.gson.JsonParseException
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.exceptions.ParseException
import com.jufarangoma.melitests.domain.exceptions.TimeOutException
import com.jufarangoma.melitests.domain.exceptions.UnknownException
import java.net.ConnectException
import java.net.SocketTimeoutException

fun Throwable.manageCommonException(): DomainException {
    Log.e(
        "OTHER_EXCEPTION",
        "${this.message}",
        this
    )
    return when (this) {
        is SocketTimeoutException -> TimeOutException
        is ConnectException -> DomainException(this.message ?: String())
        is JsonParseException -> ParseException
        else -> UnknownException
    }
}
