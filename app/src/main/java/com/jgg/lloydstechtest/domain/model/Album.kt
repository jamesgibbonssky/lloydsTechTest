package com.jgg.lloydstechtest.domain.model

data class Album (
    val name : String,
    val artist : String,
    val url : String,
    val image : List<Image>,
    val streamable : Boolean,
    val mbid : String
) {
    fun isEmpty() : Boolean {
        return name == "" &&
                artist == "" &&
                url == "" &&
                image.isEmpty() &&
                streamable == false &&
                mbid == ""
    }

    companion object {
        fun createEmpty() : Album {
            return Album(name = "",
                artist = "",
                url = "",
                image = emptyList(),
                streamable = false,
                mbid = ""
                )
        }
    }
}