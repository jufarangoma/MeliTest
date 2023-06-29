package com.jufarangoma.melitests.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.melitests.domain.entities.ProductEntity
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.repositories.SearchRepository
import com.jufarangoma.melitests.presentation.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val arrayListOfProducts: ArrayList<ProductEntity>,
    private val mutableLiveData: MutableLiveData<RequestState>
) : ViewModel() {

    val liveDataRequestState: LiveData<RequestState> = mutableLiveData
    val listOfProducts: List<ProductEntity> = arrayListOfProducts
    fun search(query: String) {
        viewModelScope.launch(coroutineDispatcher) {
            searchRepository.search(query).onStart {
                mutableLiveData.postValue(RequestState.Loading)
            }.collect {
                it.fold(
                    onSuccess = { productsList ->
                        successFlow(productsList)
                    },
                    onFailure = { throwable ->
                        mutableLiveData.postValue(RequestState.Error(throwable as DomainException))
                    }
                )
            }
        }
    }

    private fun successFlow(productsList: List<ProductEntity>) {
        arrayListOfProducts.clear()
        arrayListOfProducts.addAll(productsList)
        mutableLiveData.postValue(RequestState.Success)
    }

    fun clearStates() {
        mutableLiveData.postValue(RequestState.Empty)
    }
}
