package com.example.giantsecret.view


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.BottomSheetListView
import com.example.giantsecret.ExerciseAdapter


import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateExerciseBinding
import com.example.giantsecret.hideKeyboard

import kotlin.collections.ArrayList


var SET_SIZE = 100
class CreateExerciseFragment : Fragment() {
    private lateinit var binding:FragmentCreateExerciseBinding
    private lateinit var bottomSheetListView:BottomSheetListView
    private lateinit var exerciseAdapter:ExerciseAdapter



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_create_exercise,container,false)
        initView()
        return binding.root

    }

    private fun initView(){
        createBottomSheet()
        createSearchExerciseListView()
        selectExerciseRadioGroup()
        binding.createExerciseBackBtn.setOnClickListener {
            findNavController().navigate(R.id.createExerciseToCloseAction)
        }
        exerciseAdapter = ExerciseAdapter(1,requireContext(),childFragmentManager)
    }

    private fun createBottomSheet(){
        var setArrayList: ArrayList<String> = ArrayList()
        var countArrayList: ArrayList<String> = ArrayList()

        for(i:Int in 0..SET_SIZE) {
            setArrayList.add(i,"${i}")
            countArrayList.add(i, "${i}")
        }

        // set 개수 BottomSheet
        binding.choiceSetTextView.setOnClickListener {
            bottomSheetListView = BottomSheetListView(requireContext(),setArrayList)
            bottomSheetListView.show(childFragmentManager,"SET_BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object: BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String,clickItemPosition:Int) {
                    binding.choiceSetTextView.text = clickItem
                    exerciseAdapter.changeSetSize(clickItemPosition)
                    exerciseAdapter.notifyDataSetChanged()
                }
            })
        }

        // 운동 개수 BottomSheet
        binding.choiceExerciseCount.setOnClickListener {
            bottomSheetListView = BottomSheetListView(requireContext(),countArrayList)
            bottomSheetListView.show(childFragmentManager,"SET_BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object: BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String,clickItemPosition:Int) {
                    binding.choiceExerciseCount.text = clickItem

                }
            })
        }

    }
    // 기존 운동 이름 선택
    private fun createSearchExerciseListView(){
        var exerciseList: ArrayList<String> =
            resources.getStringArray(R.array.exercise_event_list).toList() as ArrayList<String>

        var adapter = ArrayAdapter<String>(
            requireContext()
            ,android.R.layout.simple_expandable_list_item_1
            ,exerciseList
            )
        binding.exerciseListView.adapter = adapter

        binding.selectExerciseTextView.setOnClickListener {
            binding.searchExerciseLayout.visibility = View.VISIBLE

        }
        binding.exerciseSearchEditText.addTextChangedListener { s ->
            adapter.filter.filter(s)
        }

        binding.exerciseListView.setOnItemClickListener{ _, _, position, _ ->
            binding.exerciseNameEditText.setText(adapter.getItem(position))
            binding.searchExerciseLayout.visibility = View.GONE
            hideKeyboard()
        }
       
        binding.searchExerciseCloseBtn.setOnClickListener{
            binding.searchExerciseLayout.visibility = View.GONE
            hideKeyboard()
        }


    }
    private fun selectExerciseRadioGroup(){
        binding.setWeightEqualBtn.setOnClickListener{
            binding.setWeightDifferentRecyclerView.visibility = View.GONE
            binding.setWeightEqualLayout.visibility = View.VISIBLE
        }
        binding.setWeightDifferentBtn.setOnClickListener{
            var layoutManager = LinearLayoutManager(requireContext());


            binding.setWeightDifferentRecyclerView.layoutManager = layoutManager;
            binding.setWeightDifferentRecyclerView.adapter = exerciseAdapter
            binding.setWeightEqualLayout.visibility = View.GONE
            binding.setWeightDifferentRecyclerView.visibility = View.VISIBLE
        }

    }
}