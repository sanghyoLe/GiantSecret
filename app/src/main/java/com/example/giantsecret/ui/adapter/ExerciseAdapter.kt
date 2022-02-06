package com.example.giantsecret.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.databinding.ExerciseCardViewBinding

import com.example.giantsecret.data.model.ExerciseWithSet


class ExerciseAdapter(
    private val onDeleteCallBack: (ExerciseWithSet) -> Unit,
    private val onModifyCallBack: (ExerciseWithSet) -> Unit,
)
: RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){

    private var exerciseWithSetList = mutableListOf<ExerciseWithSet>()
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
            holder.binding.showAllSetRecyclerView.adapter = ShowSetListAdapter(exerciseWithSetList[position].exerciseSets)
            holder.binding.cardViewLayout.setOnClickListener {
                setLayoutShowHide(holder.binding.showAllSetLayout)
            }
            holder.binding.deleteBtn.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("운동 삭제")
                    .setMessage("루틴에서 해당 운동을 삭제하시겠습니까?")
                    .setPositiveButton("삭제", {
                            dialog , _ ->
                        exerciseWithSetList.remove(currentItem)
                        onDeleteCallBack(currentItem)
                        notifyDataSetChanged()
                        dialog.dismiss()
                    })
                    .setNegativeButton("취소",{
                            dialog, _ -> dialog.dismiss()
                    }).create().show()
            }
            holder.binding.modifyBtn.setOnClickListener {
                onModifyCallBack(currentItem)
                notifyDataSetChanged()
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
        exerciseWithSetList = exerciseWithSet.toMutableList()
        notifyDataSetChanged()
    }


}
