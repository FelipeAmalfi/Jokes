package com.jokes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jokes.api.JokeApiService
import com.jokes.model.Joke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class JokeViewModel(val categoryId: String): ViewModel() {
    private val jokeService =  JokeApiService()
    private val disposable = CompositeDisposable()

    val joke = MutableLiveData<Joke>()
    val jokeLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchJoke(){
       newJoke()
    }


    private fun newJoke(){
        loading.value = true
        disposable.add(
            jokeService.getJoke(categoryId)
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

}