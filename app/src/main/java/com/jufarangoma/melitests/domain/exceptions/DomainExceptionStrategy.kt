package com.jufarangoma.melitests.domain.exceptions

interface DomainExceptionStrategy {
    fun manageException(exception: Throwable): DomainException
}
