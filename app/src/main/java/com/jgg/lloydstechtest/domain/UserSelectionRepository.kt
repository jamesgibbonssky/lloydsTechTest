package com.jgg.lloydstechtest.domain


import com.jgg.lloydstechtest.domain.model.Album
import javax.inject.Inject
import javax.inject.Singleton

// A shared object that stores the users selected album - used for sharing selections between ViewModels
@Singleton
class UserSelectionRepository
@Inject constructor() {
    internal var selectedAlbum: Album = Album.createEmpty()

    internal fun clearSelectedAlbum() {
        selectedAlbum = Album.createEmpty()
    }
}