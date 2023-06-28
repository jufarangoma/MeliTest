package com.jufarangoma.melitests.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jufarangoma.melitests.presentation.fragments.PictureFragment

class PicturesAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val arrayPictures: ArrayList<String> = arrayListOf()

    fun setPictures(pictures: List<String>) {
        arrayPictures.clear()
        arrayPictures.addAll(pictures)
    }

    override fun getCount(): Int {
        return arrayPictures.size
    }

    override fun getItem(position: Int): Fragment {
        return PictureFragment.newInstance(arrayPictures[position])
    }
}
