package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jufarangoma.melitests.databinding.FragmentProductsBinding
import com.jufarangoma.melitests.presentation.adapters.ProductsAdapter
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var binding: FragmentProductsBinding? = null
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val productsAdapter by lazy { ProductsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        if (searchViewModel.listOfProducts.isNotEmpty()) {
            setUpAdapter()
        } else {
            setEmptyView()
        }
    }

    private fun setEmptyView() {
        binding?.viewError?.isVisible = true
        binding?.rcvProducts?.isVisible = false
    }

    private fun setUpAdapter() {
        binding?.viewError?.isVisible = false
        productsAdapter.setList(searchViewModel.listOfProducts)
        binding?.rcvProducts?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
            isVisible = true
        }
    }
}
