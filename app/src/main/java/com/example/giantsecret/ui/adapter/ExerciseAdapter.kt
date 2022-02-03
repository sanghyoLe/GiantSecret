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

import javax.inject.Inject
import javax.inject.Singleton


class ExerciseAdapter()
: RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){


    private var exerciseWithSetList = emptyList<ExerciseWithSet>()
    private lateinit var context: Context

    class ViewHolder(val binding:ExerciseCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExerciseCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val currentItem = exerciseWithSetList[position]
            holder.binding.nameTextView.text = currentItem.exercise.name
            holder.binding.numberOfSetTextView.text = currentItem.exercise.numberOfSet.toString()
            holder.binding.showAllSetRecyclerView.layoutManager = LinearLayoutManager(context)
            holder.binding.showAllSetRecyclerView.adapter = ShowSetListAdapter(exerciseWithSetList[position].sets)

            holder.binding.cardViewLayout.setOnClickListener {
                setLayoutShowHide(holder.binding.showAllSetLayout)
            }
    }

    override fun getItemCount(): Int {
        return exerciseWithSetList.size
    }
    fun setLayoutShowHide(view:View) {
        view.visibility =
            if(view.visibility == View.GONE) {

                View.VISIBLE
            } else {

                View.GONE
            }
    }

    fun setExerciseWithSet(exerciseWithSet: List<ExerciseWithSet>) {
        exerciseWithSetList = exerciseWithSet
        notifyDataSetChanged()
    }


}
