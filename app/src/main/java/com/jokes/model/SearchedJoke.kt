package com.jokes.model

import com.google.gson.annotations.SerializedName

data class SearchedJoke (
        val total: Int,
        @SerializedName("result")
        val jokes: List<Joke>
)