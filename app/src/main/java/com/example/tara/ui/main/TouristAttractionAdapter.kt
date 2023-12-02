package com.example.tara.ui.main

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tara.R
import com.example.tara.data.TouristAttraction

class TouristAttractionAdapter (
    private val onClick: (TouristAttraction) -> Unit
) : RecyclerView.Adapter<TouristAttractionAdapter.TouristAttractionViewHolder>(DIFF_CALLBACK) {

    //TODO 8 : Create and initialize ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristAttractionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return TouristAttractionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TouristAttractionViewHolder, position: Int) {
        //TODO 9 : Get data and bind them to ViewHolder
        val touristAttraction = getItem(position) as Habit

        val drawable: Drawable? = when (habit.priorityLevel) {
            "High" -> ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_priority_high)
            "Medium" -> ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_priority_medium)
            "Low" -> ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_priority_low)
            else -> null
        }

        drawable?.let {
            holder.ivPriority.setImageDrawable(it)
        }

        holder.bind(habit)
    }

    inner class TouristAttractionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.item_tv_title)
        val ivPriority: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val tvStartTime: TextView = itemView.findViewById(R.id.item_tv_start_time)
        private val tvMinutes: TextView = itemView.findViewById(R.id.item_tv_minutes)

        lateinit var getHabit: Habit
        fun bind(habit: Habit) {
            getHabit = habit
            tvTitle.text = habit.title
            tvStartTime.text = habit.startTime
            tvMinutes.text = habit.minutesFocus.toString()
            itemView.setOnClickListener {
                onClick(habit)
            }
        }

    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem == newItem
            }
        }

    }

}