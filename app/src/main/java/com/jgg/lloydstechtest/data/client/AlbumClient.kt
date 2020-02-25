package com.jgg.lloydstechtest.data.client

import com.jgg.lloydstechtest.data.model.GetInfoResponse
import com.jgg.lloydstechtest.data.model.SearchDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal const val API_KEY = "320431072e2fa964a41121526f69c7c1"

interface AlbumClient {
    @GET("2.0/")
    fun searchAlbum(
        @Query("method") method: String  = "album.search",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("album") searchString: String
    ) : Observable<SearchDto>

    @GET("2.0/")
    fun albumDetails(
        @Query("method") method: String  = "album.getinfo",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("artist") artist: String,
        @Query("album") album: String
    ) : Single<GetInfoResponse>
}