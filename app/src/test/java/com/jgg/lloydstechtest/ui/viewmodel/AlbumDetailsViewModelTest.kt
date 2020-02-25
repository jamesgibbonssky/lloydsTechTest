package com.jgg.lloydstechtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.UserSelectionRepository
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.NullPointerException

class AlbumDetailsViewModelTest {

    @Mock
    private lateinit var schedulersProvider : SchedulersProvider
    @Mock
    private lateinit var userSelectionRepository : UserSelectionRepository
    @Mock
    private lateinit var albumRepository : AlbumRepository
    @Mock
    private lateinit var viewModelLiveDataProvider : ViewModelLiveDataProvider
    @Mock
    private lateinit var albumDetailsLiveData : MutableLiveData<AlbumDetails>
    @Mock
    private lateinit var albumDetailsErrorMessage : MutableLiveData<String>

    private lateinit var cut : AlbumDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulersProvider.mainThread()).thenReturn(Schedulers.trampoline())
        whenever(viewModelLiveDataProvider.albumDetailsLiveData).thenReturn(albumDetailsLiveData)
        whenever(viewModelLiveDataProvider.albumDetailsErrorMessage).thenReturn(albumDetailsErrorMessage)

        cut = AlbumDetailsViewModel(schedulersProvider, userSelectionRepository, albumRepository, viewModelLiveDataProvider)
    }

    @Test
    fun `given artist and album when getAlbumDetails then album details returned`() {
        // Given
        val artistName = "artist"
        val albumName = "album"
        val selectedAlbum = Album(albumName, artistName, "url", emptyList(), false, "mbid")
        val albumDetails = mock<AlbumDetails>()
        val albumDetailsSingle = Single.just(albumDetails)
        whenever(albumRepository.getAlbumDetails(artistName, albumName)).thenReturn(albumDetailsSingle)
        whenever(userSelectionRepository.selectedAlbum).thenReturn(selectedAlbum)
        val captor = argumentCaptor<AlbumDetails>()

        // When
        cut.getAlbumDetails()
        val testObserver = albumDetailsSingle.test()

        // Then
        verify(userSelectionRepository, times(2)).selectedAlbum
        verify(albumRepository).getAlbumDetails(artistName, albumName)

        verify(albumDetailsLiveData).postValue(captor.capture())
        assertEquals(albumDetails, captor.firstValue)

        testObserver.assertComplete()
            .assertNoErrors()
            .assertTerminated()

    }

    @Test
    fun `given artist and album when getAlbumDetails returns error then error message returned`() {
        // Given
        val artistName = "artist"
        val albumName = "album"
        val selectedAlbum = Album(albumName, artistName, "url", emptyList(), false, "mbid")
        val error = NullPointerException()
        val albumDetailsSingle = Single.error<AlbumDetails>(error)
        whenever(albumRepository.getAlbumDetails(artistName, albumName)).thenReturn(albumDetailsSingle)
        whenever(userSelectionRepository.selectedAlbum).thenReturn(selectedAlbum)
        val captor = argumentCaptor<String>()

        // When
        cut.getAlbumDetails()
        val testObserver = albumDetailsSingle.test()

        // Then
        verify(userSelectionRepository, times(2)).selectedAlbum
        verify(albumRepository).getAlbumDetails(artistName, albumName)

        verify(albumDetailsErrorMessage).postValue(captor.capture())
        assertEquals("Error getting album details", captor.firstValue)

        testObserver
            .assertError(error)
            .assertTerminated()
    }
}