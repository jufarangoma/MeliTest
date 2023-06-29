package com.jufarangoma.melitests.presentation

import com.jufarangoma.melitests.domain.exceptions.DomainException

sealed class RequestState {
    object Empty : RequestState()
    object Loading : RequestState()
    object Success : RequestState()
    data class Error(val exception: DomainException) : RequestState()
}
