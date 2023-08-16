package com.example.weatherappwithkotlin.screen.fragment

import ViewPagerListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappwithkotlin.databinding.FragmentDaysBinding
import com.example.weatherappwithkotlin.screen.ViewPagerListItem
import java.util.Date

class DaysFragment(private val list: List<ViewPagerListItem>) : Fragment() {

    private lateinit var binding: FragmentDaysBinding
    private lateinit var viewPagerListAdapter: ViewPagerListAdapter
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireActivity()) }

    private var isListRefreshed = true
    private var lastUpdateTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        lastUpdateTime = Date().time
        fillRecycleList()
        return binding.root
    }

    private fun fillRecycleList() {
        with(binding) {
            daysSwitchList.layoutManager = layoutManager
            viewPagerListAdapter = ViewPagerListAdapter()
            daysSwitchList.adapter = viewPagerListAdapter
            viewPagerListAdapter.submitList(list)
            isListRefreshed = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(list: List<ViewPagerListItem>) =
            DaysFragment(list)
    }
}