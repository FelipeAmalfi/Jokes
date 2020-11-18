package com.jokes.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jokes.R
import com.jokes.databinding.FragmentFavoriteJokesBinding
import com.jokes.view.adapter.JokesAdapter
import com.jokes.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite_jokes.*

class FavoriteJokesFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private val jokesAdapter = JokesAdapter(arrayListOf())
    private lateinit var dataBinding: FragmentFavoriteJokesBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_jokes, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        handleLayout()
        observeViewModel()

    }

    private fun handleLayout() {
        favoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter;
        }

        refreshLayout.setOnRefreshListener {
            viewModel.getFavoriteJokes()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.jokes.observe(viewLifecycleOwner, Observer { jokes ->
            dataBinding.showList = jokes.isNotEmpty()
            dataBinding.empty = jokes.isEmpty()
            jokesAdapter.updateList(jokes)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            dataBinding.loading = loading
        })
    }
//


}