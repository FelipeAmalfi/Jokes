package com.jokes.api

import com.jokes.model.Joke
import com.jokes.model.SearchedJoke
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

    fun fetchJoke(category: String): Single<Joke> {
        return api.fetchJoke(category);
    }

    fun fetchCategory(): Single<List<String>> {
        return api.fetchCategory();
    }

    fun searchJoke(query: String): Single<SearchedJoke> {
        return api.fetchJokeSearched(query);
    }
}