package com.jokes.view.util

import android.view.View
import com.jokes.model.Joke

interface JokeClick {
    fun reloadJoke()
    fun seePage(v: View)
    fun favoriteJoke(v: View, joke: Joke)
}