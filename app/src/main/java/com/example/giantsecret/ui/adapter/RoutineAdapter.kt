package com.example.giantsecret.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.R
import com.example.giantsecret.lib.model.Routine


class RoutineAdapter :  ListAdapter<Routine, RoutineAdapter.RoutineViewHolder>(RoutineComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RoutineViewHolder {
        return RoutineViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }
    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routineItemView: TextView = itemView.findViewById(R.id.routineNameTextView)

        fun bind(text: String?) {
            routineItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup) : RoutineViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.routine_card_view, parent, false)

                return RoutineViewHolder(view)
            }
        }

    }

    class RoutineComparator : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem.name == newItem.name
        }
    }
}
