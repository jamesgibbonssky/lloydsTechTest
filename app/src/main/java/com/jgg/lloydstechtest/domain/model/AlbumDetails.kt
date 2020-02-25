package com.jgg.lloydstechtest.domain.model

data class AlbumDetails(
    val name : String,
    val artist : String,
    val url : String,
    val image : List<Image>,
    val listeners : Int,
    val playcount : Int,
    val tracks : List<Track>,
    val tags : List<Tag>,
    val wiki : Wiki
)