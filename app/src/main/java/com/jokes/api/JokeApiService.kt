package com.jokes.api

import com.jokes.model.Joke
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JokeApiService {
    private val BASE_URL = "https://api.chucknorris.io"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(JokesApi::class.java)

    fun getJoke(category: String): Single<Joke> {
        return api.fetchJoke(category);
    }
}