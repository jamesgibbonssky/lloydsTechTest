package com.jgg.lloydstechtest.ui.viewmodel

import com.jgg.lloydstechtest.domain.model.Album

data class AlbumSearchViewState(
    val albumList : List<Album>,
    val errorMessage : String,
    val complete : Boolean
)