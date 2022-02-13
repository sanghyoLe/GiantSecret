package com.example.giantsecret.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.data.model.Record
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets
import com.example.giantsecret.data.model.RoutineWithExerciseParts
import com.example.giantsecret.databinding.RecordCardViewBinding
import java.time.LocalDate


class RecordAdapter() :  RecyclerView.Adapter<RecordAdapter.RecordViewHolder>(){
    private lateinit var binding: RecordCardViewBinding
    private var recordList:MutableList<Record> = mutableListOf()
    private var routineList:MutableList<Routine> = mutableListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecordViewHolder {
        binding = RecordCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        var currentItem = recordList[position]
        var currentRoutine = routineList[position]
        holder.binding.memoTextView.text = currentItem.memo
        holder.binding.routineNameTextView.text = currentRoutine.name

    }

    class RecordViewHolder(val binding: RecordCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return recordList.size
    }
    fun setRecord(recordList: List<Record>,selectLocalDate:LocalDate,routineList: List<Routine>) {
        this.recordList = mutableListOf()
        this.routineList = mutableListOf()
        recordList.map {
            if(it.date == selectLocalDate)
                this.recordList.add(it)
        }
        this.recordList.map { record ->
            routineList.map { routine ->
                if(record.recordInRoutineId == routine.routineId) {
                    this.routineList.add(routine)
                }
            }
        }
        notifyDataSetChanged()
    }

}