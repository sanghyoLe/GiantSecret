package com.example.giantsecret.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.databinding.RoutineCardViewBinding
import com.example.giantsecret.lib.model.Exercise

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){

    private var exerciseList = emptyList<Exercise>()
    class ViewHolder(val binding:RoutineCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RoutineCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = exerciseList[position]
            holder.binding.routineNameTextView.text = currentItem.name.toString()
            
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setExercise(exercise: List<Exercise>) {
        exerciseList = exercise
        notifyDataSetChanged()
    }

}
