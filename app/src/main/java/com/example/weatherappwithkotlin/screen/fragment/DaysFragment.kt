package com.example.weatherappwithkotlin.screen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherappwithkotlin.adapter.ViewPagerListAdapter
import com.example.weatherappwithkotlin.databinding.FragmentDaysBinding
import com.example.weatherappwithkotlin.dtoclass.ViewPagerListItem


class DaysFragment(list : List<ViewPagerListItem>) : Fragment() {

    private lateinit var binding : FragmentDaysBinding
    private lateinit var viewPagerListAdapter : ViewPagerListAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val list : List<ViewPagerListItem> = list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillRecycleList()
        swipeRefresh.setOnRefreshListener{
            fillRecycleList()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun fillRecycleList() = with(binding) {
        this@DaysFragment.swipeRefresh = binding.swipeRefresh
        daysSwitchList.layoutManager = LinearLayoutManager(activity)
        viewPagerListAdapter = ViewPagerListAdapter()
        daysSwitchList.adapter = viewPagerListAdapter
        viewPagerListAdapter.submitList(list)
    }

    fun refill(list: List<ViewPagerListItem>) = with(binding) {
        viewPagerListAdapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance(list : List<ViewPagerListItem>) =
            DaysFragment(list)
    }
}