package com.jufarangoma.melitests.presentation.states

sealed class SearchState {
    object Empty : SearchState()
    object Loading : SearchState()
    object Success : SearchState()
    object Error : SearchState()
}
