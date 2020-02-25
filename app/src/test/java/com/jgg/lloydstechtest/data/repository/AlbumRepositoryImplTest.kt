package com.jgg.lloydstechtest.data.repository

import com.jgg.lloydstechtest.data.client.AlbumClient
import com.jgg.lloydstechtest.data.mapper.AlbumDetailsDtoToAlbumDetailsMapper
import com.jgg.lloydstechtest.data.mapper.AlbumDtoToAlbumMapper
import com.jgg.lloydstechtest.data.model.*
import com.jgg.lloydstechtest.domain.model.Album
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AlbumRepositoryImplTest {

    @Mock
    private lateinit var albumClient : AlbumClient
    @Mock
    private lateinit var albumDtoToAlbumMapper : AlbumDtoToAlbumMapper
    @Mock
    private lateinit var albumDetailsDtoToAlbumDetailsMapper : AlbumDetailsDtoToAlbumDetailsMapper

    private lateinit var cut : AlbumRepositoryImpl
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = AlbumRepositoryImpl(albumClient, albumDtoToAlbumMapper, albumDetailsDtoToAlbumDetailsMapper)
    }

    @Test
    fun `given search string when searchForAlbum then search results returned`() {
        // Given
        val searchString = "search"
        val albumDtoList = mock<List<AlbumDto>>()
        val albumsDto = mock<AlbumsDto> { on {it.album} doReturn albumDtoList}
        val searchResponse = mock<SearchResponse> { on {it.albummatches} doReturn albumsDto}
        val searchDto = mock<SearchDto> { on {it.results} doReturn searchResponse}
        val searchResultsObservable = Observable.just(searchDto)
        whenever(albumClient.searchAlbum(searchString = searchString)).thenReturn(searchResultsObservable)
        val albumList = mock<List<Album>>()
        whenever(albumDtoToAlbumMapper.mapToDomain(albumDtoList)).thenReturn(albumList)

        // When
        val testObserver = cut.searchForAlbum(searchString).test()

        // Then
        val inOrder = inOrder(albumClient, albumDtoToAlbumMapper)
        inOrder.verify(albumClient).searchAlbum(any(), any(), any(), eq(searchString))
        inOrder.verify(albumDtoToAlbumMapper).mapToDomain(albumDtoList)

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertTerminated()
    }

    @Test
    fun `given album and artist when getAlbumDetails then AlbumDetails returned`() {
        // Given
        val artistName = "artistName"
        val albumName = "albumName"
        val albumDetailsDto = mock<AlbumDetailsDto>()
        val getInfoResponse = mock<GetInfoResponse> { on {it.album} doReturn albumDetailsDto}
        val response = Single.just(getInfoResponse)
        whenever(albumClient.albumDetails(artist = artistName, album = albumName)).thenReturn(response)
        whenever(albumDetailsDtoToAlbumDetailsMapper.mapToDomain(albumDetailsDto)).thenReturn(mock())

        //When
        val testObserver = cut.getAlbumDetails(artistName, albumName).test()

        // Then
        val inOrder = inOrder(albumClient, albumDetailsDtoToAlbumDetailsMapper)
        inOrder.verify(albumClient).albumDetails(any(), any(), any(), eq(artistName), eq(albumName))
        inOrder.verify(albumDetailsDtoToAlbumDetailsMapper).mapToDomain(albumDetailsDto)

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertTerminated()
    }
}