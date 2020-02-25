package com.jgg.lloydstechtest.ui.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val TIMEOUT_IN_SECONDS = "timeoutInSeconds"
private const val TIMEOUT_IN_SEC: Int = 15

@Module
open class NetworkModule {

    @Provides
    @Named(TIMEOUT_IN_SECONDS)
    open fun provideNetworkTimeout(): Int {
        return TIMEOUT_IN_SEC
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client.build()
    }

    @Provides
    @Singleton
    internal fun provideGson() = GsonBuilder().setLenient().create()

    @Provides
    internal fun providesDispatcher(): Dispatcher {
        return Dispatcher()
    }

    @Reusable
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Reusable
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
}
