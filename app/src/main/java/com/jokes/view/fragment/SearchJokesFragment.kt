package com.jokes.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jokes.R
import com.jokes.databinding.FragmentSearchJokesBinding
import com.jokes.util.hideKeyboard
import com.jokes.view.adapter.JokesAdapter
import com.jokes.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_jokes.*


class SearchJokesFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private val jokesAdapter = JokesAdapter(arrayListOf())
    private lateinit var dataBinding: FragmentSearchJokesBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_jokes, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        handleLayout()
        observeViewModel()

    }

    private fun handleLayout() {
        jokesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter;
        }

        editSearch.setOnEditorActionListener(OnEditorActionListener { search, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchJoke(search.text.toString())
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun observeViewModel() {
        viewModel.searchedJokes.observe(viewLifecycleOwner, Observer { jokes ->
            dataBinding.showList = jokes.isNotEmpty()
            jokesAdapter.updateList(jokes)
        })
        viewModel.searchLoadError.observe(viewLifecycleOwner, Observer { error ->
            dataBinding.error = error
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            dataBinding.loading = loading
        })
    }
}