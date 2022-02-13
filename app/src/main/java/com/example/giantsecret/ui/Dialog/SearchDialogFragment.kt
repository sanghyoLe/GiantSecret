package com.example.giantsecret.ui.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.R
import com.example.giantsecret.databinding.SearchDialogLayoutBinding
import com.example.giantsecret.dialogFragmentResize
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.ui.Routine.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchDialogFragment(private var searchAdapter: SearchAdapter)  : DialogFragment() {
    private lateinit var binding:SearchDialogLayoutBinding

    private val routineViewModel: RoutineViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchDialogLayoutBinding.inflate(inflater, container, false)

        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseRecyclerView.adapter = searchAdapter
        binding.exerciseRecyclerView.setHasFixedSize(true)
        binding.exerciseSearchEditText.addTextChangedListener { s ->
            searchAdapter.filter.filter(binding.exerciseSearchEditText.text)
        }

        return binding.root
    }





    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@SearchDialogFragment,0.95f,0.95f)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
        binding.choiceBtn.setOnClickListener {
            searchAdapter.getCheckedExercise().map {
                var addExerciseWithSet = it.copy()
                addExerciseWithSet.exercise.exerciseId = null
                addExerciseWithSet.exerciseSets.map {
                    it.setId = null
                }
                routineViewModel.addExerciseWithSetData(addExerciseWithSet)
            }
            dismiss()
        }

    }

    class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>(),Filterable {

        private lateinit var exercises:MutableList<ExerciseWithSet>

        var mExerciseList:MutableList<ExerciseWithSet> = mutableListOf()
        var filteredExercise :MutableList<ExerciseWithSet> = mutableListOf()

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
            var currentItem = if(filteredExercise.size == 0) mExerciseList[position]
                    else filteredExercise[position]


            holder.exerciseName.text = currentItem.exercise.name
            holder.checkBox.setOnCheckedChangeListener { _ , isChecked ->
                if(isChecked) exercises.add(currentItem)
                else exercises.remove(currentItem)
            }
        }

        override fun getItemCount(): Int {
            return if(filteredExercise.size == 0) mExerciseList.size
                else filteredExercise.size

        }


        fun setExercise(exercise: List<ExerciseWithSet>) {
            mExerciseList = exercise.toMutableList()
            notifyDataSetChanged()
        }

        fun getCheckedExercise() : List<ExerciseWithSet>{
            return exercises
        }



        override fun getFilter(): Filter {
            return object : Filter() {
                override fun publishResults(constraint: CharSequence, results: FilterResults) {
                    filteredExercise = results.values as ArrayList<ExerciseWithSet>
                    notifyDataSetChanged()
                }

                override fun performFiltering(constraint: CharSequence): FilterResults {
                    filteredExercise.clear()

                    if(constraint.isEmpty()) {
                        filteredExercise.addAll(mExerciseList)
                    } else {
                        filteredExercise = getFilteredResults(constraint.toString())
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filteredExercise
                    filterResults.count = filteredExercise.size
                    return filterResults

                }


            }
        }
                fun getFilteredResults(constraint:String)  : ArrayList<ExerciseWithSet>{
                    var results: ArrayList<ExerciseWithSet> = ArrayList()

                    mExerciseList.map {
                        if(it.exercise.name.contains(constraint)) {
                            results.add(it)

                    }
                }
                    return results
            }
    }




}
