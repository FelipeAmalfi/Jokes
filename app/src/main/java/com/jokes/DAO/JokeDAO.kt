package com.jokes.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jokes.model.Joke

@Dao
interface JokeDAO {
    @Insert
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM Joke" )
    suspend fun selectAllJokes(): List<Joke>

    @Query("SELECT * FROM Joke WHERE id = :jokeId")
    suspend fun getJoke(jokeId: Int): Joke

    @Query("SELECT EXISTS (SELECT 1 FROM Joke WHERE id = :id)")
    suspend fun existJoke(id: String): Boolean

    @Delete
    suspend fun deleteJoke(joke: Joke)
}