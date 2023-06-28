package com.jufarangoma.melitests.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.melitests.domain.entities.ProductDetail
import com.jufarangoma.melitests.domain.repositories.ProductDetailRepository
import com.jufarangoma.melitests.presentation.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mutableLiveData: MutableLiveData<RequestState>
) : ViewModel() {

    val liveDataRequestState: LiveData<RequestState> = mutableLiveData
    var productId: String? = String()
    var productDetail: ProductDetail? = null
        private set

    fun getProductDetail() {
        viewModelScope.launch(coroutineDispatcher) {
            productId?.let { productIdNotNull ->
                productDetailRepository.getProductDetail(productIdNotNull).onStart {
                    mutableLiveData.postValue(RequestState.Loading)
                }.collect {
                    it.fold(
                        onSuccess = { productDetailResponse ->
                            productDetail = productDetailResponse
                            mutableLiveData.postValue(RequestState.Success)
                        },
                        onFailure = {
                            mutableLiveData.postValue(RequestState.Error)
                        }
                    )
                }
            } ?: kotlin.run {
                mutableLiveData.postValue(RequestState.Error)
            }
        }
    }
}
