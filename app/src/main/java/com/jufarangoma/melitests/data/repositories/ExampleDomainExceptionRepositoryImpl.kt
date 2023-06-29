package com.jufarangoma.melitests.data.repositories

import android.util.Log
import com.jufarangoma.melitests.domain.exceptions.CommonErrors
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.exceptions.HttpErrorCode
import com.jufarangoma.melitests.domain.exceptions.InternalServerError
import com.jufarangoma.melitests.domain.exceptions.Unauthorized
import com.jufarangoma.melitests.domain.repositories.DomainExceptionRepository
import java.net.HttpURLConnection
import retrofit2.HttpException

class ExampleDomainExceptionRepositoryImpl : CommonErrors(), DomainExceptionRepository {

    /**
     * For the example I assume that code 409 is a reserved code to catch business logic exception,
     * I could validate some id to manage the exception
     */

    override fun manageException(exception: Throwable): DomainException {
        return if (exception is HttpException) {
            Log.e(
                "HTTP_EXCEPTION",
                "code: ${exception.code()}",
                exception
            )
            when (exception.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> Unauthorized
                HttpURLConnection.HTTP_CONFLICT -> DomainException("Controlled busines logic")
                HttpURLConnection.HTTP_BAD_GATEWAY -> InternalServerError
                else -> HttpErrorCode(exception.code(), exception.message())
            }
        } else {
            manageCommonException(exception)
        }
    }
}
