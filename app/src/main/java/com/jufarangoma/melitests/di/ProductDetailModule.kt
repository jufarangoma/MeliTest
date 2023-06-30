package com.jufarangoma.melitests.di

import androidx.lifecycle.MutableLiveData
import com.jufarangoma.melitests.data.api.ProductDetailApi
import com.jufarangoma.melitests.data.repositories.ProductDetailRepositoryImpl
import com.jufarangoma.melitests.domain.exceptions.DomainExceptionStrategy
import com.jufarangoma.melitests.domain.repositories.ProductDetailRepository
import com.jufarangoma.melitests.presentation.viewmodels.ProductDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ProductDetailModule {

    @Provides
    fun productDetailViewModel(
        productDetailRepository: ProductDetailRepository,
        coroutineDispatcher: CoroutineDispatcher
    ) = ProductDetailViewModel(
        productDetailRepository = productDetailRepository,
        coroutineDispatcher = coroutineDispatcher,
        mutableLiveData = MutableLiveData()
    )

    @Provides
    @ViewModelScoped
    fun productDetailRepository(
        productDetailApi: ProductDetailApi,
        domainExceptionStrategy: DomainExceptionStrategy
    ): ProductDetailRepository = ProductDetailRepositoryImpl(
        productDetailApi = productDetailApi,
        exceptionStrategy = domainExceptionStrategy
    )

    @Provides
    @ViewModelScoped
    fun productDetailApiProvider(
        retrofit: Retrofit
    ): ProductDetailApi = retrofit.create(ProductDetailApi::class.java)
}
