package com.example.giantsecret.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.databinding.RoutineCardViewBinding


class RoutineAdapter(private val onDeleteCallBack: (Routine) -> Unit,
                     private val onModifyCallBack: (Routine) -> Unit
                     ) :  RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>(){
    private var routineList = emptyList<Routine>()
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RoutineViewHolder {
        val binding = RoutineCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val currentItem = routineList[position]
        holder.binding.routineNameTextView.text = currentItem.name
//        holder.binding.targetAreaTextView.text = currentItem.exerciseParts
        holder.binding.deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .setTitle("루틴 삭제")
                .setMessage("루틴을 삭제하시겠습니까?")
                .setPositiveButton("삭제", {
                        dialog , _ ->
                        onDeleteCallBack(currentItem)
                    dialog.dismiss()
                })
                .setNegativeButton("취소",{
                        dialog, _ -> dialog.dismiss()
                }).create().show()
        }
        holder.binding.modifyBtn.setOnClickListener {
                onModifyCallBack(currentItem)
        }
    }

    class RoutineViewHolder(val binding: RoutineCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return routineList.size
    }
    fun setRoutine(routineList: List<Routine>) {
        this.routineList = routineList
        notifyDataSetChanged()
    }




}
