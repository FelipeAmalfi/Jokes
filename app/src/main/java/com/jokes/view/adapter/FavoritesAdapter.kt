package com.jokes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jokes.R
import com.jokes.databinding.ItemJokeFavoriteBinding
import com.jokes.databinding.ItemJokeFavoriteBindingImpl
import com.jokes.model.Joke
import com.jokes.view.listeners.RemoveFavoriteClick
import com.jokes.viewmodel.FavoriteViewModel

class FavoritesAdapter(private val jokesList: ArrayList<Joke>, private val favoriteViewModel: FavoriteViewModel): RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>(), RemoveFavoriteClick{

    class FavoriteViewHolder( var view: ItemJokeFavoriteBinding): RecyclerView.ViewHolder(view.root)

    fun updateList(jokes: List<Joke>){
        jokesList.clear()
        jokesList.addAll(jokes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemJokeFavoriteBinding>(inflater, R.layout.item_joke_favorite, parent, false )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.view.joke = jokesList[position]
        holder.view.listener = this
    }

    override fun getItemCount() = jokesList.size

    override fun removeFavoriteJoke(v: View, joke: Joke) {
        favoriteViewModel.removeFavoriteJoke(joke)
    }

}