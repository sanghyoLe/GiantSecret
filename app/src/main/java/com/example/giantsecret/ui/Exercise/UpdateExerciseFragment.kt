package com.example.giantsecret.ui.Exercise

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giantsecret.R
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.databinding.FragmentCreateExerciseBinding
import com.example.giantsecret.hideKeyboard
import com.example.giantsecret.ui.Dialog.BottomSheetListView
import com.example.giantsecret.ui.adapter.SetListAdapter
import com.example.giantsecret.ui.RoutineViewModel

import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class UpdateExercise : Fragment() {
    private lateinit var binding: FragmentCreateExerciseBinding
    private lateinit var bottomSheetListView: BottomSheetListView
    private lateinit var setListAdapter: SetListAdapter

    private val routineViewModel: RoutineViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateExerciseBinding.inflate(layoutInflater,container,false)
        initView()
        binding.exerciseSaveTextView.setText("수정")
        binding.topAppBarTitle.setText("운동 수정하기")
        binding.exerciseNameEditText.setText(
            routineViewModel.clickedExerciseSetData.exercise.name
        )

        binding.choiceSetTextView.text = routineViewModel.clickedExerciseSetData.exercise.numberOfSet.toString()
        setListAdapter.changeSetSize(routineViewModel.clickedExerciseSetData.exercise.numberOfSet)
        setListAdapter.notifyDataSetChanged()

        return binding.root
    }

    private fun initView(){
        createBottomSheet()
        createSearchExerciseListView()
        selectExerciseRadioGroup()


        // 세트 마다 무게 다름 RecyclerView Adapter 설정정
        setListAdapter = SetListAdapter(1,requireContext(),childFragmentManager)
        createExercise()
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
                    setListAdapter.changeSetSize(clickItemPosition)
                    setListAdapter.notifyDataSetChanged()
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
    // 기존 운동 선택
    private fun createSearchExerciseListView(){
        var exerciseList: ArrayList<String> =
            resources.getStringArray(R.array.exercise_event_list).toList() as ArrayList<String>

        var adapter = ArrayAdapter<String>(
            requireContext()
            ,android.R.layout.simple_expandable_list_item_1
            ,exerciseList
        )
        // 운동 목록 Adapter 설정
        binding.exerciseListView.adapter = adapter

        // 운동 선택 클릭시
        binding.selectExerciseTextView.setOnClickListener {
            binding.searchExerciseLayout.visibility = View.VISIBLE
        }

        // 운동 선택 Search
        binding.exerciseSearchEditText.addTextChangedListener { s ->
            adapter.filter.filter(s)
        }

        // 운동 선택 시
        binding.exerciseListView.setOnItemClickListener{ _, _, position, _ ->
            binding.exerciseNameEditText.setText(adapter.getItem(position))
            binding.searchExerciseLayout.visibility = View.GONE
            hideKeyboard()
        }

        // 운동 선택 View 닫기
        binding.searchExerciseCloseBtn.setOnClickListener{
            binding.searchExerciseLayout.visibility = View.GONE
            hideKeyboard()
        }

        binding.createExerciseBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }
    // 세트 간 무게 동일 or 무게 다름
    private fun selectExerciseRadioGroup(){
        binding.setWeightEqualBtn.setOnClickListener{
            binding.setWeightDifferentRecyclerView.visibility = View.GONE
            binding.setWeightEqualLayout.visibility = View.VISIBLE
        }
        binding.setWeightDifferentBtn.setOnClickListener{
            var layoutManager = LinearLayoutManager(requireContext());


            binding.setWeightDifferentRecyclerView.layoutManager = layoutManager;
            binding.setWeightDifferentRecyclerView.adapter = setListAdapter
            binding.setWeightEqualLayout.visibility = View.GONE
            binding.setWeightDifferentRecyclerView.visibility = View.VISIBLE
        }
    }

    // 저장 눌렀을 때
    private fun createExercise(){
        binding.exerciseSaveTextView.setOnClickListener {
            var exerciseName:String
            var numberOfSet:Int
            // 운동 이름 미입력
            if(TextUtils.isEmpty(binding.exerciseNameEditText.text)) {
                Toast.makeText(requireContext(),"운동 제목을 입력해주세요", Toast.LENGTH_LONG).show()
            } // 중량 미입력
            else {
                exerciseName = binding.exerciseNameEditText.text.toString()
                numberOfSet = Integer.parseInt(binding.choiceSetTextView.text.toString())

                var exercise = Exercise(null, null, name = exerciseName, numberOfSet = numberOfSet)
                // 세트 간 무게 동일 시
                if(binding.setWeightEqualBtn.isChecked) {
                    if(TextUtils.isEmpty(binding.weightEditText.text)) {
                        Toast.makeText(requireContext(), "중량을 입력해주세요", Toast.LENGTH_LONG).show()
                    } else {
                        var numberOfRep:Int = Integer.parseInt(binding.choiceExerciseCount.text.toString())
                        var weight:Double =  binding.weightEditText.text.toString().toDouble()


                        var exerciseSet = ExerciseSet(null,null,numberOfRep,weight)
                        var exerciseSetList:List<ExerciseSet>  = List(numberOfSet) { exerciseSet }

                        routineViewModel.updateExerciseWithSet(ExerciseWithSet(exercise,exerciseSetList))

                        findNavController().popBackStack()
                    }
                }
                // 세트 마다 무게 다를 시
                else if(binding.setWeightDifferentBtn.isChecked) {
                    var weightList = setListAdapter.getWeightArrayList()
                    var repList = setListAdapter.getRepArrayList()
                    var exerciseSetList: ArrayList<ExerciseSet> = ArrayList()

                    for(i:Int in 0..numberOfSet-1) {
                        exerciseSetList.add(i, ExerciseSet(null,null,
                            repList.get(i),weightList.get(i)
                        )
                        )
                    }

                    routineViewModel.updateExerciseWithSet(ExerciseWithSet(exercise,exerciseSetList))

                    findNavController().popBackStack()
                }
            }
        }
    }

}