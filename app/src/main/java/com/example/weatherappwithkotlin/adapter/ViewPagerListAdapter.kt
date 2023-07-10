package com.example.weatherappwithkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.databinding.LayoutItemBinding
import com.example.weatherappwithkotlin.dtoclass.ViewPagerListItem

class ViewPagerListAdapter : ListAdapter<ViewPagerListItem, ViewPagerListAdapter.LayoutItemHolder>(LayoutComparator()) {

    class LayoutItemHolder(view : View) : RecyclerView.ViewHolder(view) {
        val createView = LayoutItemBinding.bind(view)
        fun fillItem(itemDTO: ViewPagerListItem) = with(createView) {
            Date.text = itemDTO.date
            Temperature.text = itemDTO.temperature
            Condition.text = itemDTO.weatherCondition
            Image.setImageDrawable(ContextCompat.getDrawable(createView.root.context, itemDTO.iconIndex))
        }
    }


    class LayoutComparator : DiffUtil.ItemCallback<ViewPagerListItem>() {
        override fun areItemsTheSame(
            oldItem: ViewPagerListItem,
            newItem: ViewPagerListItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewPagerListItem,
            newItem: ViewPagerListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutItemHolder {
        val createView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return LayoutItemHolder(createView)
    }

    override fun onBindViewHolder(holder: LayoutItemHolder, position: Int) {
        holder.fillItem(getItem(position))
    }
}