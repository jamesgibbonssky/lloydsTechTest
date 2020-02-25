package com.jgg.lloydstechtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import javax.inject.Inject

// A Provider method that provides the live data objects - this can then be injected into the ViewModel and thus enables mocking
class ViewModelLiveDataProvider
@Inject constructor() {
    internal val albumListLiveData = MutableLiveData<AlbumSearchViewState>()
    internal val albumDetailsLiveData = MutableLiveData<AlbumDetails>()
    internal val albumDetailsErrorMessage = MutableLiveData<String>()
}