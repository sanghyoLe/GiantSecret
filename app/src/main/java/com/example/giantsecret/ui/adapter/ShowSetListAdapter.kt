package com.example.giantsecret.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.databinding.ExerciseSetRowItemBinding
import com.example.giantsecret.data.model.ExerciseSet


class ShowSetListAdapter(exerciseSetList:List<ExerciseSet>) : RecyclerView.Adapter<ShowSetListAdapter.ViewHolder>() {
    private var exerciseSetList:List<ExerciseSet> = exerciseSetList

    class ViewHolder(val binding:ExerciseSetRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ExerciseSetRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = exerciseSetList[position]

        holder.binding.setNumberTextView.text = (position+1).toString()
        holder.binding.weightTextView.text = currentItem.weight.toString()
        holder.binding.repTextView.text = currentItem.numberOfRep.toString()
    }


    override fun getItemCount(): Int {
        return exerciseSetList.size
    }





}