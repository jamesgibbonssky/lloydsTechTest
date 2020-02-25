package com.jgg.lloydstechtest.data.model

import com.google.gson.annotations.SerializedName

data class SearchDto(
    val results : SearchResponse
)

data class SearchResponse(
    @SerializedName("opensearch:totalResults") val totalResults: String,
    @SerializedName("opensearch:startIndex") val startIndex: String,
    @SerializedName("opensearch:itemsPerPage") val itemsPerPage: String,
    val albummatches: AlbumsDto
)

data class AlbumsDto(
    val album: List<AlbumDto>
)