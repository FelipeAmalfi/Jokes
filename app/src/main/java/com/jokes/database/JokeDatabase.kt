package com.jokes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jokes.DAO.JokeDAO
import com.jokes.model.Joke

@Database(entities = arrayOf(Joke::class), version = 1)
abstract class JokeDatabase: RoomDatabase() {

    abstract  fun jokeDao(): JokeDAO

    companion object {
        @Volatile private var instance: JokeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =  instance ?: synchronized(LOCK) {
            instance?: buildDatabase(context).also {
            instance =  it
        }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            JokeDatabase::class.java,
            "jokeDatabase"
        ).build()
    }

}