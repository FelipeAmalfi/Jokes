package com.jokes.view.listeners

import android.view.View
import com.jokes.model.Joke

interface RemoveFavoriteClick {
    fun removeFavoriteJoke(v: View, joke: Joke)
}