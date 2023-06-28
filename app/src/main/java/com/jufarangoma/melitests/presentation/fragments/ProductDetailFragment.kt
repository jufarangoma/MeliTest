package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jufarangoma.melitests.R
import com.jufarangoma.melitests.databinding.FragmentProductDetailBinding
import com.jufarangoma.melitests.domain.entities.Attribute
import com.jufarangoma.melitests.presentation.RequestState
import com.jufarangoma.melitests.presentation.adapters.AttributesAdapter
import com.jufarangoma.melitests.presentation.viewmodels.ProductDetailViewModel
import com.jufarangoma.melitests.utils.toMoney
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var binding: FragmentProductDetailBinding? = null
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val attributesAdapter by lazy { AttributesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callProductDetail()
        initObservers()
    }

    private fun callProductDetail() {
        val productId = arguments?.getString(PRODUCT_ID)
        productDetailViewModel.productId = productId
        productDetailViewModel.getProductDetail()
    }

    private fun initObservers() {
        productDetailViewModel.liveDataRequestState.observe(viewLifecycleOwner) { requestState ->
            when (requestState) {
                is RequestState.Loading -> {}
                is RequestState.Success -> showProductDetail()
                is RequestState.Error -> showError()
                is RequestState.Empty -> Unit
            }
        }
    }

    private fun showError() {
        TODO("Not yet implemented")
    }

    private fun showProductDetail() {
        val productDetail = productDetailViewModel.productDetail

        if (productDetail != null && binding != null) {
            with(binding!!) {
                txvProductDetailName.text = productDetail.title
                txvProductDetailDeliveryFree.isVisible = productDetail.freeShipping
                txvProductDetailPrice.text = productDetail.price.toMoney()
                txvProductDetailWarranty.text = productDetail.warranty
                setUpAdapter(productDetail.attributes)
            }
        }
    }

    private fun setUpAdapter(attributes: List<Attribute>) {
        attributesAdapter.setList(attributes)
        binding?.rcvProductDetail?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attributesAdapter
            isVisible = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val PRODUCT_ID = "productId"
    }
}
