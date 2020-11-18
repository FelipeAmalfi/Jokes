package com.jokes.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jokes.DAO.JokeDAO
import com.jokes.di.DaggerDatabaseComponent
import com.jokes.di.DatabaseModule
import com.jokes.model.Joke
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel(application: Application): BaseViewModel(application) {

    @Inject
    lateinit var dao: JokeDAO

    val jokes = MutableLiveData<List<Joke>>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(application))
            .build()
            .injectDatabase(this)
        getFavoriteJokes()
    }

    fun getFavoriteJokes(){
        loading.value = true
        getJokes()
    }

    private fun getJokes() {
        launch {
            jokes.value = dao.getAllJokes()
            loading.value = false
        }
    }
}