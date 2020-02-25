package com.jgg.lloydstechtest.domain.model

data class Wiki(
    val published : String,
    val summary : String,
    val content : String
) {
    companion object {
        fun createEmpty() = Wiki(
            published = "",
            summary = "",
            content = ""
        )
    }
}