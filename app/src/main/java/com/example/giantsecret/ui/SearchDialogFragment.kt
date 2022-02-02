package com.example.giantsecret.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.R
import com.example.giantsecret.databinding.SearchDialogLayoutBinding
import com.example.giantsecret.dialogFragmentResize
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.generated.callback.OnClickListener
import com.example.giantsecret.viewModel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchDialogFragment(searchAdapter: SearchAdapter)  : DialogFragment() {
    private lateinit var binding:SearchDialogLayoutBinding

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private var searchAdapter = searchAdapter
    private lateinit var listener: OnClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchDialogLayoutBinding.inflate(inflater, container, false)

        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseRecyclerView.adapter = searchAdapter
        binding.exerciseRecyclerView.setHasFixedSize(true)
        return binding.root
    }





    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@SearchDialogFragment,0.9f,0.9f)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
        binding.choiceBtn.setOnClickListener {
            searchAdapter.getCheckedExercise().map {
                exerciseViewModel.addGeneratedExercise(it)
            }
            dismiss()

        }
    }

    class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
        lateinit var exercises:MutableList<ExerciseWithSet>
        private var allExerciseList: List<ExerciseWithSet> = emptyList()
        class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            val checkBox:CheckBox
            val exerciseName:TextView
            init {

                checkBox = view.findViewById(R.id.checkBoxExercise)
                exerciseName = view.findViewById(R.id.exerciseNameTextView)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_exercise_row_item,parent,false)
            exercises = mutableListOf()
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var currentItem = allExerciseList[position]
            holder.exerciseName.text = currentItem.exercise.name
            holder.checkBox.setOnCheckedChangeListener { _ , isChecked ->
                if(isChecked) exercises.add(currentItem)
                else exercises.remove(currentItem)
            }
        }

        override fun getItemCount(): Int {
            return allExerciseList.size
        }


        fun setExercise(exercise: List<ExerciseWithSet>) {
            allExerciseList = exercise
            notifyDataSetChanged()
        }

        fun getCheckedExercise() : List<ExerciseWithSet>{
            return exercises
        }
    }




}
