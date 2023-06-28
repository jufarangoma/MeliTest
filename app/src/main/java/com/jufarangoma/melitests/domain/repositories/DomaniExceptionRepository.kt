package com.jufarangoma.melitests.domain.repositories

import com.jufarangoma.melitests.domain.exceptions.DomainException

/**
 * Any API can implement this solution of each network exception
 */
interface DomainExceptionRepository {
    fun manageException(exception: Throwable): DomainException
}
