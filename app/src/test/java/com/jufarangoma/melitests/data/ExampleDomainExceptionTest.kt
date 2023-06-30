package com.jufarangoma.melitests.data

import com.google.gson.JsonParseException
import com.jufarangoma.melitests.data.repositories.ExampleDomainExceptionStrategy
import com.jufarangoma.melitests.domain.exceptions.HttpErrorCode
import com.jufarangoma.melitests.domain.exceptions.InternalServerError
import com.jufarangoma.melitests.domain.exceptions.ParseException
import com.jufarangoma.melitests.domain.exceptions.TimeOutException
import com.jufarangoma.melitests.domain.exceptions.Unauthorized
import com.jufarangoma.melitests.domain.exceptions.UnknownException
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.ConnectException
import java.net.SocketTimeoutException
import org.junit.After
import org.junit.Test
import retrofit2.HttpException

class ExampleDomainExceptionTest {

    private val httpException = mockk<HttpException>(relaxed = true)
    private val exampleException = ExampleDomainExceptionStrategy()

    @Test
    fun testUnauthorized() {
        every { httpException.code() } returns 401

        val domainException = exampleException.manageException(httpException)
        assert(domainException is Unauthorized)

        verify {
            httpException.code()
        }
    }

    @Test
    fun testConflict() {
        every { httpException.code() } returns 409

        val domainException = exampleException.manageException(httpException)
        assert(domainException.message == "Controlled busines logic")

        verify {
            httpException.code()
        }
    }

    @Test
    fun testBadGateway() {
        every { httpException.code() } returns 502

        val domainException = exampleException.manageException(httpException)
        assert(domainException is InternalServerError)

        verify {
            httpException.code()
        }
    }

    @Test
    fun testOtherHttpCode() {
        every { httpException.code() } returns 411

        val domainException = exampleException.manageException(httpException)
        assert(domainException is HttpErrorCode)

        verify(exactly = 3) {
            httpException.code()
        }
        verify {
            httpException.message()
        }
    }

    @Test
    fun testTimeout() {
        val throwable = SocketTimeoutException()

        val domainException = exampleException.manageException(throwable)
        assert(domainException is TimeOutException)
    }

    @Test
    fun testConnectException() {
        val throwable = ConnectException("Server Error")

        val domainException = exampleException.manageException(throwable)
        assert(domainException.message == "Server Error")
    }

    @Test
    fun testJsonParseException() {
        val throwable = JsonParseException("")

        val domainException = exampleException.manageException(throwable)
        assert(domainException is ParseException)
    }

    @Test
    fun otherException() {
        val throwable = Throwable("Unknow")

        val domainException = exampleException.manageException(throwable)
        assert(domainException is UnknownException)
    }

    @After
    fun tearDown() {
        confirmVerified(httpException)
    }
}
