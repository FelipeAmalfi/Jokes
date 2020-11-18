package com.jokes.di

import android.app.Application
import com.jokes.DAO.JokeDAO
import com.jokes.database.JokeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Inject


@Module
class DatabaseModule (private val application: Application) {

    @Provides
    fun provideJokeDao(): JokeDAO {
        return  JokeDatabase(application).jokeDao()
    }


}