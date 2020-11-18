package com.jokes.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jokes.DAO.JokeDAO
import com.jokes.api.JokesApi
import com.jokes.di.*
import com.jokes.di.DatabaseModule
import com.jokes.model.Joke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class JokeViewModel(application: Application, private val categoryId: String) : BaseViewModel(application) {

    @Inject
    lateinit var jokeService:  JokesApi
    @Inject
    lateinit var dao: JokeDAO
    private val disposable = CompositeDisposable()


    val joke = MutableLiveData<Joke>()

    val jokeLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val showJoke = MutableLiveData<Boolean>()
    val favorited = MutableLiveData<Boolean>()

    init {
        DaggerApiDatabaseComponent.builder()
            .databaseModule(DatabaseModule(application))
            .build()
            .inject(this)

        fetchJoke()
    }


    fun fetchJoke() {
        handleLoading()
        fetchJokes()
    }

    fun favoriteJoke(joke: Joke) {
        launch {
            try{
                dao.insertJoke(joke)
                favorited.value = true
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
    }

    fun removeFavoriteJoke(joke: Joke) {
        launch {
            try {
                dao.deleteJoke(joke)
                favorited.value = false
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun fetchJokes() {
        disposable.add(
                jokeService.fetchJoke(categoryId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Joke>() {
                            override fun onSuccess(result: Joke) {
                                handleJoke(result)
                            }

                            override fun onError(e: Throwable) {
                                handleError()
                            }
                        })
        )
    }


    private fun handleLoading() {
        showJoke.value = false
        loading.value = true
        jokeLoadError.value = false
    }

    private fun handleJoke(result: Joke) {
        launch {
            joke.value = result
            showJoke.value = true
            jokeLoadError.value = false
            loading.value = false
            val exists = dao.existJoke(result.id)
            println("existe? $exists")
            favorited.value = exists
        }

    }

    private fun handleError() {
        jokeLoadError.value = true
        loading.value = false
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear();
    }


    class JokeViewModelFactory (private val application: Application, private val categoryId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return JokeViewModel(application,categoryId) as T
        }
    }


}