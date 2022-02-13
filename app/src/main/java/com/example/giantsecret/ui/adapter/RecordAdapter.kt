package com.example.giantsecret.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.data.model.*
import com.example.giantsecret.databinding.RecordCardViewBinding
import java.time.LocalDate


class RecordAdapter(
    private val onModifyCallBack: (Record,Routine) -> (Unit),
    private val onDeleteCallBack: (Record) -> Unit
)  :  RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
    private lateinit var binding: RecordCardViewBinding
    private var recordList: List<Record> = emptyList()
    private var routineList: List<Routine> = emptyList()

    private var selectRecordList = mutableListOf<Record>()
    private var selectRoutineList = mutableListOf<Routine>()

    private var routineWithExercisePart: List<RoutineWithExerciseParts> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        binding = RecordCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        if(!selectRecordList.isEmpty()) {
            var currentItem = selectRecordList[position]
            var routineId:Long? = null
            var partString = ""
            this.routineList.map { routine ->
                if(routine.routineId == currentItem.recordInRoutineId) {
                    holder.binding.routineNameTextView.text = routine.name
                    routineId = routine.routineId
                }
            }
            this.routineWithExercisePart.map {
                if(it.routine.routineId == routineId)
                    it.parts.map { it
                        partString = partString.plus(it.name).plus(" ")
                    }
            }
            holder.binding.exercisePartTextView.text = partString
            holder.binding.memoTextView.text = currentItem.memo


        }


//        holder.binding.exercisePartTextView.text = partString

//        holder.binding.modifyBtn.setOnClickListener {
//            onModifyCallBack(currentItem, currentRoutine)
//        }
//        holder.binding.deleteBtn.setOnClickListener {
//            createAlterDeleteDialog(currentItem)
//        }

    }

    class RecordViewHolder(val binding: RecordCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return selectRecordList.size
    }

    fun setRecord(
        recordList: List<Record>,
    ) {
        this.recordList = recordList
        notifyDataSetChanged()
    }
    fun setRoutine(
        routineList: List<Routine>,
    ) {
        this.routineList = routineList

    }
    fun setSelectDateInRecord(localDate: LocalDate) {
        selectRecordList = mutableListOf()
        selectRoutineList = mutableListOf()
        this.recordList.map{ record ->
            if(record.date == localDate) {
                selectRecordList.add(record)
            }
        }

        notifyDataSetChanged()
    }


    fun setRoutineWithExerciseParts(routineWithExercisePart: List<RoutineWithExerciseParts>) {
        this.routineWithExercisePart = routineWithExercisePart
        notifyDataSetChanged()
    }

    fun createAlterDeleteDialog(currentItem: Record) {
        AlertDialog.Builder(context)
            .setTitle("운동기록 삭제")
            .setMessage("기록을 삭제하시겠습니까?")
            .setPositiveButton("삭제", { dialog, _ ->
                onDeleteCallBack(currentItem)
                dialog.dismiss()
            })
            .setNegativeButton("취소", { dialog, _ ->
                dialog.dismiss()
            }).create().show()


    }
}