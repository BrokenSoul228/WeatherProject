import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappwithkotlin.databinding.LayoutItemBinding
import com.example.weatherappwithkotlin.view.ViewPagerListItem

class ViewPagerListAdapter : ListAdapter<ViewPagerListItem, ViewPagerListAdapter.LayoutItemHolder>(LayoutComparator()) {

    inner class LayoutItemHolder(private val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewPagerListItem) {
            binding.apply {
                Date.text = item.date
                Temperature.text = item.temperature
                Condition.text = item.weatherCondition
                Image.setImageDrawable(ContextCompat.getDrawable(root.context, item.iconIndex))
            }
        }
    }

    class LayoutComparator : DiffUtil.ItemCallback<ViewPagerListItem>() {
        override fun areItemsTheSame(oldItem: ViewPagerListItem, newItem: ViewPagerListItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ViewPagerListItem, newItem: ViewPagerListItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutItemHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LayoutItemHolder(binding)
    }

    override fun onBindViewHolder(holder: LayoutItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}