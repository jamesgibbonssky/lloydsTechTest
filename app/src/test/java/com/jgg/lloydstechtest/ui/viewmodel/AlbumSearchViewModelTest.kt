package com.jgg.lloydstechtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.UserSelectionRepository
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.InterruptedIOException
import java.lang.NullPointerException

class AlbumSearchViewModelTest {

    @Mock
    private lateinit var schedulersProvider : SchedulersProvider
    @Mock
    private lateinit var userSelectionRepository : UserSelectionRepository
    @Mock
    private lateinit var albumRepository : AlbumRepository
    @Mock
    private lateinit var viewModelLiveDataProvider : ViewModelLiveDataProvider
    @Mock
    private lateinit var albumListLiveData : MutableLiveData<AlbumSearchViewState>

    private lateinit var cut : AlbumSearchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulersProvider.mainThread()).thenReturn(Schedulers.trampoline())
        whenever(viewModelLiveDataProvider.albumListLiveData).thenReturn(albumListLiveData)

        cut = AlbumSearchViewModel(schedulersProvider, userSelectionRepository, albumRepository, viewModelLiveDataProvider)
    }

    @Test
    fun `when onAlbumSelected then album set in userSelectionRepository`() {
        // Given
        val album = mock<Album>()

        // When
        cut.onAlbumSelected(album)

        // Then
        verify(userSelectionRepository).selectedAlbum = album
    }

    @Test
    fun `given search string when searchAlbums the list of matching albums returned`() {
        // Given
        val searchString = "abc"
        val searchQueryObservable = Observable.just(searchString)
        val albumList = mock<List<Album>>()
        val searchResultsObservable = Observable.just(albumList)
        whenever(albumRepository.searchForAlbum(searchString)).thenReturn(searchResultsObservable)
        val captor = argumentCaptor<AlbumSearchViewState>()

        // When
        cut.searchAlbums(searchQueryObservable)
        val searchQueryTestObserver = searchQueryObservable.test()
        val searchResultsTestObserver = searchResultsObservable.test()

        // Then
        verify(albumRepository).searchForAlbum(searchString)

        verify(albumListLiveData, times(2)).postValue(captor.capture())
        assertEquals(albumList, captor.firstValue.albumList)
        assertEquals("", captor.firstValue.errorMessage)
        assertEquals(false, captor.firstValue.complete)
        assertEquals(0, captor.secondValue.albumList.size)
        assertEquals("", captor.secondValue.errorMessage)
        assertEquals(true, captor.secondValue.complete)


        searchQueryTestObserver.assertComplete()
            .assertNoErrors()
            .assertTerminated()
        searchResultsTestObserver.assertComplete()
            .assertNoErrors()
            .assertTerminated()
    }

    @Test
    fun `given search string when searchAlbums returns error then view state returned with error state`() {
        // Given
        val error = NullPointerException()
        val captor = argumentCaptor<AlbumSearchViewState>()

        // When
        searchReturnsError(error)

        // Then
        // Then
        verify(albumRepository).searchForAlbum(any())

        verify(albumListLiveData).postValue(captor.capture())
        assertEquals(0, captor.firstValue.albumList.size)
        assertEquals("Error searching albums", captor.firstValue.errorMessage)
        assertEquals(false, captor.firstValue.complete)
    }

    @Test
    fun `given search string when searchAlbums returns InterruptedIoException then error ignored`() {
        // Given
        val error = InterruptedIOException()
        val captor = argumentCaptor<AlbumSearchViewState>()

        // When
        searchReturnsError(error)

        // Then
        // Then
        verify(albumRepository).searchForAlbum(any())

        verify(albumListLiveData, times(2)).postValue(captor.capture())
        assertEquals(0, captor.firstValue.albumList.size)
        assertEquals("", captor.firstValue.errorMessage)
        assertEquals(false, captor.firstValue.complete)
        assertEquals(0, captor.secondValue.albumList.size)
        assertEquals("", captor.secondValue.errorMessage)
        assertEquals(true, captor.secondValue.complete)
    }

    private fun searchReturnsError(error : Throwable) {
        val searchQueryObservable = Observable.just("abc")
        val searchResultsObservable = Observable.error<List<Album>>(error)
        whenever(albumRepository.searchForAlbum(any())).thenReturn(searchResultsObservable)

        // When
        cut.searchAlbums(searchQueryObservable)
        searchQueryObservable.test()
        searchResultsObservable.test()
    }
}