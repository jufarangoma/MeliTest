package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.jufarangoma.melitests.R
import com.jufarangoma.melitests.databinding.FragmentProductDetailBinding
import com.jufarangoma.melitests.domain.entities.Attribute
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.exceptions.InternalServerError
import com.jufarangoma.melitests.domain.exceptions.Unauthorized
import com.jufarangoma.melitests.presentation.RequestState
import com.jufarangoma.melitests.presentation.adapters.AttributesAdapter
import com.jufarangoma.melitests.presentation.adapters.PicturesAdapter
import com.jufarangoma.melitests.presentation.viewmodels.ProductDetailViewModel
import com.jufarangoma.melitests.utils.toMoney
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var binding: FragmentProductDetailBinding? = null
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val attributesAdapter by lazy { AttributesAdapter() }
    private val picturesAdapter by lazy { PicturesAdapter(childFragmentManager) }

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
        initListeners()
    }

    private fun initListeners() {
        binding?.viewPagerProductdetail?.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding?.chipCountPicture?.text =
                    getString(
                        R.string.counter_pictures,
                        (position + 1),
                        productDetailViewModel.productDetail?.pictures?.size
                    )
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun callProductDetail() {
        val productId = arguments?.getString(PRODUCT_ID)
        productDetailViewModel.productId = productId
        productDetailViewModel.getProductDetail()
    }

    private fun initObservers() {
        productDetailViewModel.liveDataRequestState.observe(viewLifecycleOwner) { requestState ->
            when (requestState) {
                is RequestState.Loading -> binding?.loadingProductDetail?.isVisible = true
                is RequestState.Success -> showProductDetail()
                is RequestState.Error -> showError(requestState.exception)
                is RequestState.Empty -> Unit
            }
        }
    }

    private fun showError(exception: DomainException) {
        val title = when (exception) {
            is Unauthorized -> R.string.exception_title_unauthorized
            is InternalServerError -> R.string.exception_title_internal_sever_error
            else -> R.string.exception_title_product_detail
        }
        binding?.loadingProductDetail?.isVisible = false
        binding?.exceptionProductDetail?.setView(
            title = getString(title),
            description = getString(R.string.exception_description_check_connection)
        )
    }

    private fun showProductDetail() {
        binding?.loadingProductDetail?.isVisible = false
        binding?.nscviewProductDetail?.isVisible = true

        val productDetail = productDetailViewModel.productDetail

        if (productDetail != null && binding != null) {
            with(binding!!) {
                setViewPager(productDetail.pictures)
                txvProductDetailName.text = productDetail.title
                txvProductDetailDeliveryFree.isVisible = productDetail.freeShipping
                txvProductDetailPrice.text = productDetail.price.toMoney()
                txvProductDetailWarranty.text = productDetail.warranty
                setUpAdapter(productDetail.attributes)
            }
        }
    }

    private fun setViewPager(pictures: List<String>) {
        picturesAdapter.setPictures(pictures)
        binding?.viewPagerProductdetail?.run {
            adapter = picturesAdapter
        }
        binding?.chipCountPicture?.text = getString(
            R.string.counter_pictures,
            1,
            productDetailViewModel.productDetail?.pictures?.size
        )
    }

    private fun setUpAdapter(attributes: List<Attribute>) {
        attributesAdapter.setList(attributes)
        binding?.rcvProductDetail?.run {
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
