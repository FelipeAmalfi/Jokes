package com.jokes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jokes.R
import com.jokes.databinding.ItemCategoryBinding
import com.jokes.view.fragment.CategoriesFragmentDirections
import com.jokes.view.listeners.CategoryClick

class CategoriesAdapter(private val categoryList: ArrayList<String>): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>(), CategoryClick{

    class CategoryViewHolder( var view: ItemCategoryBinding): RecyclerView.ViewHolder(view.root)

    fun updateList(categories: List<String>){
        categoryList.clear()
        categoryList.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoryBinding>(inflater, R.layout.item_category, parent, false )
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.view.category = categoryList[position]
        holder.view.listener = this
    }

    override fun getItemCount() = categoryList.size

    override fun onCategoryClick(v: View, category: String) {
        val action = CategoriesFragmentDirections.sendToJoke(category)
        Navigation.findNavController(v).navigate(action)
    }
}