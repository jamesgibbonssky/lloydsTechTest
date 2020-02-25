package com.jgg.lloydstechtest.data.repository

import com.jgg.lloydstechtest.data.client.AlbumClient
import com.jgg.lloydstechtest.data.mapper.AlbumDetailsDtoToAlbumDetailsMapper
import com.jgg.lloydstechtest.data.mapper.AlbumDtoToAlbumMapper
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AlbumRepositoryImpl
    @Inject constructor(
        private val albumClient: AlbumClient,
        private val albumDtoToAlbumMapper: AlbumDtoToAlbumMapper,
        private val albumDetailsDtoToAlbumDetailsMapper: AlbumDetailsDtoToAlbumDetailsMapper
): AlbumRepository {
    override fun searchForAlbum(albumName: String): Observable<List<Album>> {
        return albumClient.searchAlbum(searchString = albumName).map {
            albumDtoToAlbumMapper.mapToDomain(it.results.albummatches.album)
        }
    }

    override fun getAlbumDetails(artistName: String, albumName: String): Single<AlbumDetails> {
        return albumClient.albumDetails(artist = artistName, album = albumName).map {
            albumDetailsDtoToAlbumDetailsMapper.mapToDomain(it.album)
        }
    }
}