package com.jokes.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jokes.api.JokeApiService
import com.jokes.database.JokeDatabase
import com.jokes.model.Joke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class JokeViewModel(application: Application): BaseViewModel(application) {
    private val jokeService =  JokeApiService()
    private val disposable = CompositeDisposable()

    val joke = MutableLiveData<Joke>()
    val jokeLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val favorited = MutableLiveData<Boolean>(false)
    val dao = JokeDatabase(getApplication()).jokeDao()
    lateinit var categoryId: String

    fun fetchJoke(){
        fetchJokes()
    }

    fun favoriteJoke(){
        launch {
            joke.value?.let {
                dao.insertJoke(it)
            }
        }

    }

    fun removeFavoriteJoke(){
        launch {
            joke.value?.let {
                dao.deleteJoke(it)
            }
        }
    }

    private fun fetchJokes(){
        loading.value = true
        disposable.add(
            jokeService.fetchJoke(categoryId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Joke>(){
                    override fun onSuccess(result: Joke) {
                        joke.value = result
                        jokeLoadError.value =  false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        jokeLoadError.value =  true
                        loading.value = false
                    }
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear();
    }


}