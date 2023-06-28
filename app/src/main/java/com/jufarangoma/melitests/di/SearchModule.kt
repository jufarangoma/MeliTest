package com.jufarangoma.melitests.di

import androidx.lifecycle.MutableLiveData
import com.jufarangoma.melitests.data.api.SearchApi
import com.jufarangoma.melitests.data.repositories.ExampleDomainExceptionRepositoryImpl
import com.jufarangoma.melitests.data.repositories.SearchRepositoryImpl
import com.jufarangoma.melitests.domain.repositories.DomainExceptionRepository
import com.jufarangoma.melitests.domain.repositories.SearchRepository
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class SearchModule {

    @Provides
    fun searchViewModel(
        searchRepository: SearchRepository,
        coroutineDispatcher: CoroutineDispatcher
    ) = SearchViewModel(
        searchRepository = searchRepository,
        coroutineDispatcher = coroutineDispatcher,
        arrayListOfProducts = arrayListOf(),
        mutableLiveData = MutableLiveData()
    )

    @Provides
    @ViewModelScoped
    fun searchRepository(
        searchApi: SearchApi,
        domainExceptionRepository: DomainExceptionRepository
    ): SearchRepository = SearchRepositoryImpl(searchApi, domainExceptionRepository)

    @Provides
    @ViewModelScoped
    fun domainExceptionRepository(): DomainExceptionRepository =
        ExampleDomainExceptionRepositoryImpl()

    @Provides
    @ViewModelScoped
    fun searchApiProvider(
        retrofit: Retrofit
    ): SearchApi = retrofit.create(SearchApi::class.java)
}
