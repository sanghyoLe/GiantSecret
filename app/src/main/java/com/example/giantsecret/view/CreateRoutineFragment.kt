package com.example.giantsecret.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Insets
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentCreateRoutineBinding
import com.google.android.material.chip.Chip


class CreateRoutineFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoutineBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var legacySize: Size
    private var x:Int = 1
    private var y:Int = 1


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDeviceSize()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_create_routine, container, false)
        binding.routineChipGroup.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChange()
            }
        }
        binding.createRoutineBackBtn.setOnClickListener {
            findNavController().navigate(R.id.createRoutineToCloseAction)
        }
        createSearchDialog()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController()?.navigate(R.id.createRoutineToCloseAction)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
    private fun registerFilterChange() {
        val ids = binding.routineChipGroup.checkedChipIds

        val partNames = mutableListOf<CharSequence>()

        ids.forEachIndexed { index, id ->
            partNames.add(index, binding.routineChipGroup.findViewById<Chip>(id).text)
        }


            // 체크된 name 가져오기
//            partNames.forEachIndexed { index, charSequence ->
//                Log.d("partNames",partNames.get(index).toString())
//            }
    }
    private fun createSearchDialog(){
        var exerciseList = getResources().getStringArray(R.array.exercise_event_list);



        binding.selectExerciseTextView.setOnClickListener {
            var dialog:Dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_searchable_spinner)


            dialog.window?.setLayout((legacySize.width*0.8).toInt(),(legacySize.height*0.8).toInt())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            var editText:EditText = dialog.findViewById(R.id.edit_text)
            var listView:ListView = dialog.findViewById(R.id.list_view)

            var adapter:ArrayAdapter<String> = ArrayAdapter<String>(requireContext()
                ,android.R.layout.simple_expandable_list_item_1
                ,exerciseList
                )

            listView.adapter = adapter
            editText.addTextChangedListener { s ->
                adapter.filter.filter(s);
            }

            listView.setOnItemClickListener { adapterView, view, position, l ->
                    binding.selectExerciseTextView.text = adapter.getItem(position)

                dialog.dismiss()
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getDeviceSize() {
        var metrics:WindowMetrics = activity?.windowManager!!.currentWindowMetrics
        legacySize = Size(metrics.bounds.width(), metrics.bounds.height())
    }

}


