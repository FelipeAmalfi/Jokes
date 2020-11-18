package com.jokes.view.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.jokes.R
import com.jokes.databinding.FragmentJokeBinding
import com.jokes.model.Joke
import com.jokes.view.listeners.JokeClick
import com.jokes.viewmodel.JokeViewModel

class JokeFragment() : Fragment(), JokeClick {

    private lateinit var  viewModel: JokeViewModel
    lateinit var viewModelFactory: JokeViewModel.JokeViewModelFactory
    lateinit var application: Application

    private lateinit var dataBinding: FragmentJokeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_joke, container, false)
        dataBinding.listener = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            activity?.let {
                application = it.application
            }
            arguments?.let {
                val id =  JokeFragmentArgs.fromBundle(it).categoryId
                viewModelFactory = JokeViewModel.JokeViewModelFactory( application,id)
                viewModel = ViewModelProvider(this, viewModelFactory).get(JokeViewModel::class.java)
            }
            observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.joke.observe(viewLifecycleOwner, Observer {joke ->
            joke.let {
                dataBinding.joke = joke
            }
        })

        viewModel.showJoke.observe(viewLifecycleOwner, Observer {
            dataBinding.showJoke = it
        })
        viewModel.jokeLoadError.observe(viewLifecycleOwner, Observer {
            dataBinding.error = it
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            dataBinding.loading = it
        })
        viewModel.favorite.observe(viewLifecycleOwner, Observer {
            dataBinding.favorite = it
        })
    }

    override fun reloadJoke() {
        viewModel.fetchJoke()
    }

    override fun seePage(v: View) {
        val action = viewModel.joke.value?.url?.let { JokeFragmentDirections.sendToWebview(it) }
        action?.let { Navigation.findNavController(v).navigate(it) }
    }

    override fun favoriteJoke(v: View, joke: Joke) {
        if(!viewModel.favorite.value!!) {
            viewModel.favoriteJoke(joke)
            Toast.makeText(activity, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
        }else{
            viewModel.removeFavoriteJoke(joke)
            Toast.makeText(activity, getString(R.string.remove_favorites), Toast.LENGTH_SHORT).show()
        }
    }


}