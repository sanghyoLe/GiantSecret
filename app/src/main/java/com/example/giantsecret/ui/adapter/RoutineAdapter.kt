package com.example.giantsecret.ui.adapter



import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.model.RoutineWithExerciseAndSets
import com.example.giantsecret.data.model.RoutineWithExerciseParts
import com.example.giantsecret.databinding.RoutineCardViewBinding


class RoutineAdapter(private val onDeleteCallBack: (Routine) -> Unit,
                     private val onModifyCallBack: (RoutineWithExerciseAndSets) -> Unit,
                     private val onSelectCallBack:(Routine,String) ->  Unit,
                     private val isCreateRoutineAdapter:Boolean
                     ) :  RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>(){
    private lateinit var binding:RoutineCardViewBinding

    private var routineList = emptyList<RoutineWithExerciseAndSets>()
    private var routineWithExercisePart = emptyList<RoutineWithExerciseParts>()
    private var exerciseAdapter = ExerciseAdapter(
        {ExerciseWithSet->},
        {exerciseWithSet, i ->  },
        true
    )

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RoutineViewHolder {
        binding = RoutineCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context

        if(!isCreateRoutineAdapter) initRecordAdapter()
        initExerciseRecyclerView()
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        if(routineList.size == routineWithExercisePart.size) {
            val currentItem = routineList[position]
            val currentExerciseParts = routineWithExercisePart[position]

            var partString = ""

            // 루틴 부위 String

            if(currentItem.routine.routineId == currentExerciseParts.routine.routineId){
                currentExerciseParts.parts.map {
                    partString = partString.plus(it.name).plus(" ")
                }
            }


            holder.binding.cardViewLayout.setOnClickListener {
                exerciseAdapter.setExerciseWithSet(currentItem.exercise)
                holder.binding.exerciseRecyclerView.visibility =
                    if(holder.binding.exerciseRecyclerView.visibility == View.VISIBLE) View.GONE
                    else View.VISIBLE
            }
            holder.binding.routineNameTextView.text = currentItem.routine.name
            holder.binding.exercisePartTextView.text = partString
            holder.binding.startBtn.setOnClickListener {

            }
            holder.binding.deleteBtn.setOnClickListener {
                createAlterDeleteDialog(currentItem)
            }
            holder.binding.modifyBtn.setOnClickListener {
                onModifyCallBack(currentItem)
            }
            holder.binding.startBtn.setOnClickListener {
                onSelectCallBack(currentItem.routine,partString)
            }
        }


    }

    class RoutineViewHolder(val binding: RoutineCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return routineList.size
    }
    fun setRoutine(routineList: List<RoutineWithExerciseAndSets>) {
        this.routineList = routineList
        notifyDataSetChanged()
    }
    fun setRoutineWithExerciseParts(routineWithExercisePart:List<RoutineWithExerciseParts>) {
        this.routineWithExercisePart = routineWithExercisePart
        notifyDataSetChanged()
    }
    fun initRecordAdapter(){
        binding.startBtn.text = "선택"
    }
    fun initExerciseRecyclerView(){
        binding.exerciseRecyclerView.adapter = exerciseAdapter
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

    }
    fun showExerciseRecyclerView(){

    }
    fun createAlterDeleteDialog(currentItem:RoutineWithExerciseAndSets){
        AlertDialog.Builder(context)
            .setTitle("루틴 삭제")
            .setMessage("루틴을 삭제하시겠습니까?")
            .setPositiveButton("삭제", {
                    dialog , _ ->
                onDeleteCallBack(currentItem.routine)
                dialog.dismiss()
            })
            .setNegativeButton("취소",{
                    dialog, _ -> dialog.dismiss()
            }).create().show()
    }

}
