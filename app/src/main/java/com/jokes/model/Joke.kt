package com.jokes.model

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("icon_url")
    val icon: String?,
    val id: String? ,
    val url: String,
    val value: String?
)