package com.jokes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jokes.api.JokesApi
import com.jokes.di.DaggerApiComponent
import com.jokes.model.Joke
import com.jokes.model.SearchedJoke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    @Inject
    lateinit var jokeService: JokesApi
    private val disposable = CompositeDisposable()

    val searchedJokes = MutableLiveData<List<Joke>>()
    val searchLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun searchJoke(query: String) {
        searchedJokes.value = arrayListOf()
        loading.value = true
        searchLoadError.value = false
        fetchSearchJoke(query)
    }

    init {
        DaggerApiComponent.create().inject(this)
    }


    private fun fetchSearchJoke(query: String) {
        disposable.add(
                jokeService.fetchJokeSearched(query)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<SearchedJoke>() {
                            override fun onSuccess(result: SearchedJoke) {
                                searchedJokes.value = result.jokes
                                searchLoadError.value = false
                                loading.value = false
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                                searchLoadError.value = true
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