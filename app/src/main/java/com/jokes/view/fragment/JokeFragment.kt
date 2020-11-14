package com.jokes.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jokes.R
import com.jokes.databinding.FragmentJokeBinding
import com.jokes.viewmodel.JokeViewModel

class JokeFragment : Fragment() {

    private lateinit var  viewModel: JokeViewModel
    private lateinit var dataBinding: FragmentJokeBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_joke, container, false)
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id = JokeFragmentArgs.fromBundle(it).categoryId
            viewModel = ViewModelProviders.of(this).get(JokeViewModel(id)::class.java)
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

}