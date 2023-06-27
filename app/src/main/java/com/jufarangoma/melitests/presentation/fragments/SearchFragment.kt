package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jufarangoma.melitests.databinding.FragmentSearchBinding
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val searchViewModel: SearchViewModel by viewModels()

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
    }

    private fun initListeners() {
        binding?.edtSearch?.setOnEditorActionListener { _, _, event ->
            event?.let {
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (binding?.edtSearch?.text.toString().isNullOrEmpty().not()) {
                        searchViewModel.search(binding?.edtSearch?.text?.toString()!!)
                    } else {
                    }
                }
            }
            false
        }
    }
}
