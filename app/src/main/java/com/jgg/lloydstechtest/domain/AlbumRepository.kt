package com.jgg.lloydstechtest.domain

import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import io.reactivex.Observable
import io.reactivex.Single

interface AlbumRepository {
    fun searchForAlbum(albumName: String) : Observable<List<Album>>
    fun getAlbumDetails(artistName: String, albumName: String) : Single<AlbumDetails>
}