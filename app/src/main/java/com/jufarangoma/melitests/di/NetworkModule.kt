package com.jufarangoma.melitests.di

import com.jufarangoma.melitests.BuildConfig
import com.jufarangoma.melitests.data.repositories.ExampleDomainExceptionRepositoryImpl
import com.jufarangoma.melitests.domain.repositories.DomainExceptionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun coroutineDispatcherProvider() = Dispatchers.IO

    @Provides
    @Singleton
    fun domainExceptionRepository(): DomainExceptionRepository =
        ExampleDomainExceptionRepositoryImpl()

    @Provides
    @Singleton
    fun httpInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun okHttpProvider(
        interceptor: Interceptor
    ): OkHttpClient {
        val timeout = 5L
        return OkHttpClient.Builder()
            .dispatcher(Dispatcher().apply { maxRequests = 1 })
            .addInterceptor(interceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun retrofitProvider(
        client: OkHttpClient
    ): Retrofit {
        val urlBase = "https://api.mercadolibre.com/"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(urlBase)
            .build()
    }
}
