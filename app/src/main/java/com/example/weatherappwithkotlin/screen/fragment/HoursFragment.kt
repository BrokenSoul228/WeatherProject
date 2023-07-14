package com.example.weatherappwithkotlin.screen.fragment

import ViewPagerListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherappwithkotlin.databinding.FragmentHoursBinding
import com.example.weatherappwithkotlin.dto.ViewPagerListItem

class HoursFragment(private val list: List<ViewPagerListItem>) : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var viewPagerListAdapter: ViewPagerListAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = binding.swipeRefresh
        fillRecycleList()
        swipeRefresh.setOnRefreshListener {
            fillRecycleList()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun fillRecycleList() {
        with(binding) {
            hoursSwitchList.layoutManager = layoutManager
            viewPagerListAdapter = ViewPagerListAdapter()
            hoursSwitchList.adapter = viewPagerListAdapter
            viewPagerListAdapter.submitList(list)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(list: List<ViewPagerListItem>) =
            HoursFragment(list)
    }
}