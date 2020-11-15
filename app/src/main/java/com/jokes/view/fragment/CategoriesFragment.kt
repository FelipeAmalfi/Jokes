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
import com.jokes.databinding.FragmentCategoriesBinding
import com.jokes.view.adapter.CategoriesAdapter
import com.jokes.viewmodel.CategoriesViewModel
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    private lateinit var viewModel: CategoriesViewModel
    private val categoriesAdapter = CategoriesAdapter(arrayListOf())
    private lateinit var dataBinding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        viewModel.getCategories()

        handleLayout()
        observeViewModel()

    }

    private fun handleLayout() {
        categoriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter;
        }

        refreshLayout.setOnRefreshListener {
            viewModel.getCategories()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            dataBinding.showList = categories.isNotEmpty()
            categoriesAdapter.updateList(categories)
        })
        viewModel.categoryLoadError.observe(viewLifecycleOwner, Observer { error ->
            dataBinding.error = error
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            dataBinding.loading = loading
        })
    }

}