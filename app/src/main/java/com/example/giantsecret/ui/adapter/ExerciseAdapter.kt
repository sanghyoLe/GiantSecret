package com.example.giantsecret.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.databinding.ExerciseCardViewBinding

import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseWithSet

class ExerciseAdapter(context:Context ) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){
    private var context = context
    private var exerciseList = emptyList<Exercise>()
    private var exerciseWithSetList = emptyList<ExerciseWithSet>()



    class ViewHolder(val binding:ExerciseCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExerciseCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

            return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val currentItem = exerciseWithSetList[position]
            Log.d("1",position.toString())

            holder.binding.nameTextView.text = currentItem.exercise.name
            holder.binding.numberOfSetTextView.text = currentItem.exercise.numberOfSet.toString()
            holder.binding.showAllSetRecyclerView.layoutManager = LinearLayoutManager(context)
            holder.binding.showAllSetRecyclerView.adapter = ShowSetListAdapter(exerciseWithSetList[position].sets)

            holder.binding.cardViewLayout.setOnClickListener {
                setLayoutShowHide(holder.binding.showAllSetLayout)
            }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
    fun setLayoutShowHide(view:View) {
        view.visibility =
            if(view.visibility == View.GONE) {

                View.VISIBLE
            } else {

                View.GONE
            }
    }
    fun setExercise(exercise: List<Exercise>) {
        exerciseList = exercise
        notifyDataSetChanged()
    }
    fun setExerciseWithSet(exerciseWithSet: List<ExerciseWithSet>) {
        exerciseWithSetList = exerciseWithSet
        notifyDataSetChanged()
    }


}
