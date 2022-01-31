package com.example.giantsecret.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.databinding.ExerciseSetRowItemBinding
import com.example.giantsecret.lib.model.ExerciseSet


class ShowSetListAdapter(setList:List<ExerciseSet>) : RecyclerView.Adapter<ShowSetListAdapter.ViewHolder>() {
    private var setList:List<ExerciseSet> = setList

    class ViewHolder(val binding:ExerciseSetRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ExerciseSetRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = setList[position]

        holder.binding.setNumberTextView.text = (position+1).toString()
        holder.binding.weightTextView.text = currentItem.weight.toString()
        holder.binding.repTextView.text = currentItem.numberOfRep.toString()
    }


    override fun getItemCount(): Int {
        return setList.size
    }





}