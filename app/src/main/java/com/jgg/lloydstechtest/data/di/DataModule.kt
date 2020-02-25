package com.jgg.lloydstechtest.data.di

import com.jgg.lloydstechtest.data.client.AlbumClient
import com.jgg.lloydstechtest.data.repository.AlbumRepositoryImpl
import com.jgg.lloydstechtest.domain.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class DataModule {

    @Binds
    abstract fun providesAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository

    @Module
    companion object {

        private const val BASE_URL = "https://ws.audioscrobbler.com/"

        @JvmStatic
        @Provides
        @Reusable
        fun provideAlbumClient(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
        ): AlbumClient {

            val client = okHttpClient.newBuilder()
                .build()

            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(BASE_URL)
                .build()
                .create(AlbumClient::class.java)
        }
    }
}