package com.jgg.lloydstechtest.data.model

data class AlbumDto (
    val name : String,
    val artist : String,
    val url : String,
    val image : List<ImageDto>,
    val streamable : String,
    val mbid : String
)