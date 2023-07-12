package com.example.weatherappwithkotlin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(mainFragment : FragmentActivity, private val itemList : List<Fragment>) : FragmentStateAdapter(mainFragment) {
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun createFragment(position: Int): Fragment {
        return itemList[position]
    }

}