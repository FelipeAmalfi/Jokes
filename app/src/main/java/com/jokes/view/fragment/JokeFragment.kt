package com.jokes.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.jokes.R
import com.jokes.databinding.FragmentJokeBinding
import com.jokes.view.util.JokeClick
import com.jokes.viewmodel.JokeViewModel

class JokeFragment : Fragment(), JokeClick {

    private lateinit var  viewModel: JokeViewModel
    private lateinit var dataBinding: FragmentJokeBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_joke, container, false)
        dataBinding.listener = this
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel = ViewModelProviders.of(this).get(JokeViewModel::class.java)
            viewModel.categoryId =  JokeFragmentArgs.fromBundle(it).categoryId
        }
        viewModel.fetchJoke();
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.joke.observe(viewLifecycleOwner, Observer {joke ->
            joke.let {
                dataBinding.joke = joke
            }
        })
    }

    override fun reloadJoke() {
        viewModel.fetchJoke()
    }

    override fun seePage(v: View) {
        val action = viewModel.joke.value?.url?.let { JokeFragmentDirections.sendToWebview(it) }
        action?.let { Navigation.findNavController(v).navigate(it) }
    }


}