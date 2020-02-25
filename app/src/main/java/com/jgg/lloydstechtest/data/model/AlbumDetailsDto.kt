package com.jgg.lloydstechtest.data.model

data class AlbumDetailsDto(
    val name : String,
    val artist : String,
    val url : String,
    val image : List<ImageDto>,
    val listeners : String,
    val playcount : String,
    val tracks : TracksDto,
    val tags : TagsDto,
    val wiki : WikiDto?
)