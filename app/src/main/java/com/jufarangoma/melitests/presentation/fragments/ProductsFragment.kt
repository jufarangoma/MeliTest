package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jufarangoma.melitests.R
import com.jufarangoma.melitests.databinding.FragmentProductsBinding
import com.jufarangoma.melitests.presentation.adapters.ProductsAdapter
import com.jufarangoma.melitests.presentation.fragments.ProductDetailFragment.Companion.PRODUCT_ID
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var binding: FragmentProductsBinding? = null
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val productsAdapter by lazy { ProductsAdapter(::onItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun onItemClicked(productId: String) {
        findNavController().navigate(
            R.id.action_productsFragment_to_productDetailFragment,
            bundleOf(PRODUCT_ID to productId)
        )
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
