package com.jokes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jokes.R
import com.jokes.databinding.ItemCategoryBinding
import com.jokes.databinding.ItemJokeBinding
import com.jokes.model.Joke
import com.jokes.view.fragment.CategoriesFragmentDirections
import com.jokes.view.util.CategoryClick

class JokesAdapter(val jokesList: ArrayList<Joke>): RecyclerView.Adapter<JokesAdapter.JokeViewHolder>(){

    class JokeViewHolder( var view: ItemJokeBinding): RecyclerView.ViewHolder(view.root)

    fun updateList(jokes: List<Joke>){
        jokesList.clear()
        jokesList.addAll(jokes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemJokeBinding>(inflater, R.layout.item_joke, parent, false )
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.view.joke = jokesList[position]
    }

    override fun getItemCount() = jokesList.size

}