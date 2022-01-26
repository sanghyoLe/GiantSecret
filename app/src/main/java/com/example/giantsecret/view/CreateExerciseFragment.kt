package com.example.giantsecret.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.giantsecret.BottomSheetListView
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateExerciseBinding
import java.util.*
import kotlin.collections.ArrayList


var SET_SIZE = 15
class CreateExerciseFragment : Fragment() {
//    private lateinit var legacySize: Size
    private lateinit var binding:FragmentCreateExerciseBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getDeviceSize()
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
//        createSearchDialog()
    }

    private fun createBottomSheet(){

        var setArrayList: ArrayList<String> = ArrayList()
        var exerciseList: ArrayList<String> =
            getResources().getStringArray(R.array.exercise_event_list).toList() as ArrayList<String>

        for(i:Int in 0..SET_SIZE) {
            setArrayList.add(i,"${i} SET".toString())
        }

//        var setSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext()
//            ,android.R.layout.simple_expandable_list_item_1
//            ,setArrayList
//        )
//
//        binding.choiceSetSpinner.adapter = setSpinnerAdapter
//        binding.choiceSetSpinner.setSelection(1)

        binding.choiceSetTextView.setOnClickListener {
            var bottomSheetListView:BottomSheetListView = BottomSheetListView(requireContext(),setArrayList,false)

            bottomSheetListView.show(childFragmentManager,"SET_BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object: BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String) {
                    binding.choiceSetTextView.text = clickItem
                }
            })
        }
        Log.d("ex",exerciseList.get(1).toString())
        binding.selectExerciseTextView.setOnClickListener {
            var bottomSheetListView:BottomSheetListView = BottomSheetListView(requireContext(),exerciseList,true)

            bottomSheetListView.show(childFragmentManager,"EXERCISE_BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object: BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String) {
                    binding.exerciseNameEditText.setText(clickItem)
                }
            })
        }
    }

//    private fun createSearchDialog(){
//
//        binding.selectExerciseTextView.setOnClickListener {
//            var dialog: Dialog = Dialog(requireContext())
//            dialog.setContentView(R.layout.dialog_searchable_spinner)
//
//
//            dialog.window?.setLayout((legacySize.width*0.8).toInt(),(legacySize.height*0.8).toInt())
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//            var editText: EditText = dialog.findViewById(R.id.edit_text)
//            var listView: ListView = dialog.findViewById(R.id.list_view)
//
//            var adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext()
//                ,android.R.layout.simple_expandable_list_item_1
//                ,exerciseList
//            )
//
//            listView.adapter = adapter
//
//
//            dialog.findViewById<Button>(R.id.exerciseChoiceCloseBtn).setOnClickListener {
//                dialog.dismiss()
//            }
//            listView.setOnItemClickListener { _, _, position, _ ->
//                binding.exerciseNameEditText.setText(adapter.getItem(position))
//
//                dialog.dismiss()
//            }
//
//        }
//    }
//    @RequiresApi(Build.VERSION_CODES.R)
//    private fun getDeviceSize() {
//        var metrics: WindowMetrics = activity?.windowManager!!.currentWindowMetrics
//        legacySize = Size(metrics.bounds.width(), metrics.bounds.height())
//    }
}