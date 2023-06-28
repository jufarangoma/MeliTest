package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.jufarangoma.melitests.R
import com.jufarangoma.melitests.databinding.FragmentSearchBinding
import com.jufarangoma.melitests.presentation.RequestState
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        searchViewModel.liveDataRequestState.observe(viewLifecycleOwner) { searchState ->
            when (searchState) {
                is RequestState.Loading -> showLoading()
                is RequestState.Success -> navigateToListProducts()
                is RequestState.Error -> showException()
                else -> Unit
            }
        }
    }

    private fun showLoading() {
        binding?.let { searchBinding ->
            with(searchBinding) {
                loadingProducts.isVisible = true
                viewException.isVisible = false
            }
        }
    }

    private fun showException() {
        binding?.let { searchBinding ->
            with(searchBinding) {
                loadingProducts.isVisible = false
                viewException.setView(
                    title = getString(R.string.exception_title_check_internet),
                    description = getString(R.string.exception_description_check_connection)
                )
            }
        }
    }

    private fun navigateToListProducts() {
        binding?.let { searchBinding ->
            with(searchBinding) {
                loadingProducts.isVisible = false
                viewException.isVisible = false
            }
        }
        findNavController(this).navigate(
            R.id.action_frgSearch_to_productsFragment
        )
    }

    private fun initListeners() {
        binding?.edtSearch?.setOnEditorActionListener { _, _, event ->
            event?.let {
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (binding?.edtSearch?.text.toString().isEmpty().not()) {
                        searchViewModel.search(binding?.edtSearch?.text?.toString()!!)
                    }
                }
            }
            false
        }
    }

    override fun onStop() {
        super.onStop()
        searchViewModel.clearStates()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
