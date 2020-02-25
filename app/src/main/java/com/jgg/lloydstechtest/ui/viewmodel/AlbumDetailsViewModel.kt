package com.jgg.lloydstechtest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.UserSelectionRepository
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AlbumDetailsViewModel
@Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val userSelectionRepository: UserSelectionRepository,
    private val albumRepository: AlbumRepository,
    viewModelLiveDataProvider: ViewModelLiveDataProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val albumDetailsLiveData = viewModelLiveDataProvider.albumDetailsLiveData
    internal val albumDetailsErrorMessageLiveData = viewModelLiveDataProvider.albumDetailsErrorMessage

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    internal fun getAlbumDetails() {
        compositeDisposable.add(
            albumRepository.getAlbumDetails(userSelectionRepository.selectedAlbum.artist, userSelectionRepository.selectedAlbum.name)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    {
                        albumDetailsLiveData.postValue(it)
                    },
                    {
                        Log.e("JGG","Error getting Album details: $it")
                        it.printStackTrace()
                        albumDetailsErrorMessageLiveData.postValue("Error getting album details")
                    }
                )
        )
    }
}
