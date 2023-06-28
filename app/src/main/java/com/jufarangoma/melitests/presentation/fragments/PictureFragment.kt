package com.jufarangoma.melitests.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jufarangoma.melitests.databinding.ItemPictureBinding
import com.jufarangoma.melitests.utils.setImage

class PictureFragment : Fragment() {

    private var binding: ItemPictureBinding? = null
    private val url: String
        get() = arguments?.getString(URL, String()) ?: String()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemPictureBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        binding?.imvPicture?.setImage(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {

        const val URL = "url"

        fun newInstance(url: String): PictureFragment {
            val argumentsBundle = Bundle().apply {
                putString(URL, url)
            }

            return PictureFragment().apply {
                arguments = argumentsBundle
            }
        }
    }
}
