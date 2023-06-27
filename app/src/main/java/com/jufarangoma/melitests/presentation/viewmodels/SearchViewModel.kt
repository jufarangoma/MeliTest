package com.jufarangoma.melitests.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.melitests.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun search(query: String) {
        viewModelScope.launch(coroutineDispatcher) {
            searchRepository.search(query).onStart {
                println("+++ Loading")
            }.catch {
                println("+++ Error")
            }.collect {
                println("+++ $it")
            }
        }
    }
}
