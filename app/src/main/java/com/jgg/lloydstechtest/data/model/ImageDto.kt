package com.jgg.lloydstechtest.data.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("#text") val url : String,
    val size : String
)
