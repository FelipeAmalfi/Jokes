package com.jokes.api

import com.jokes.model.Joke
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {

    @GET("jokes/random")
    fun fetchJoke(@Query("category") category: String): Single<Joke>

    @GET("DevTides/jokes/categories")
    fun fetchCategory(): Single<List<String>>

    @GET("jokes/search")
    fun fetchJokeSearched(@Query("query") query: String): Single<List<Joke>>

}