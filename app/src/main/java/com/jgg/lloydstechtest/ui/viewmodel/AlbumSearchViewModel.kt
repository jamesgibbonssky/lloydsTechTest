package com.jgg.lloydstechtest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.UserSelectionRepository
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AlbumSearchViewModel
@Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val userSelectionRepository: UserSelectionRepository,
    private val albumRepository: AlbumRepository,
    viewModelLiveDataProvider: ViewModelLiveDataProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    internal val albumListLiveData = viewModelLiveDataProvider.albumListLiveData

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    internal fun onAlbumSelected(album: Album) {
        userSelectionRepository.selectedAlbum = album
    }

    internal fun searchAlbums(searchQueryObservable : Observable<String>) {
        compositeDisposable.add(searchQueryObservable.debounce(500, TimeUnit.MILLISECONDS)
                .filter {
                    it.length == 0 || it.length > 2
                }
                .distinctUntilChanged()
                .switchMap {
                    when(it.length) {
                        0 -> Observable.just(emptyList())
                        else -> {
                            albumRepository.searchForAlbum(it)
                                .onErrorResumeNext { it: Throwable ->
                                    when (it) {
                                        // Filter out InterruptedIOExceptions which happen if the previous search is cancelled by switchMap
                                        is InterruptedIOException -> {
                                            Log.d("JGG ", "InterruptedIOException ignoring")
                                            Observable.just(emptyList())
                                        }
                                        else -> {
                                            Log.e("JGG", "search returned other error")
                                            Observable.error<List<Album>>(it)
                                        }
                                    }
                                }
                        }
                    }
                }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
            .subscribe(
                {
                    albumListLiveData.postValue(
                        AlbumSearchViewState(
                            albumList = it,
                            errorMessage = "",
                            complete = false)
                    )
                },
                {
                        Log.e("JGG","Error gettingSearchResults: $it")
                        it.printStackTrace()
                        albumListLiveData.postValue(
                            AlbumSearchViewState(
                                albumList = emptyList(),
                                errorMessage = "Error searching albums",
                                complete = false)
                        )
                },
                {
                    albumListLiveData.postValue(
                        AlbumSearchViewState(
                            albumList = emptyList(),
                            errorMessage = "",
                            complete = true)
                    )
                }
            )
        )
    }
}