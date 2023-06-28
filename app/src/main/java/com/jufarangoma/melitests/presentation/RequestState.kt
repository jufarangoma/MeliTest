package com.jufarangoma.melitests.presentation

sealed class RequestState {
    object Empty : RequestState()
    object Loading : RequestState()
    object Success : RequestState()
    object Error : RequestState()
}
