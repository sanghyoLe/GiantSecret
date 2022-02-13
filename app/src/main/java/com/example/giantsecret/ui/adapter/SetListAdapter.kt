package com.example.giantsecret.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.ui.Dialog.BottomSheetListView
import com.example.giantsecret.R
import com.example.giantsecret.ui.Routine.SET_SIZE


class SetListAdapter(private var setSize:Int = 1, context: Context, fragmentManager: FragmentManager) :
    RecyclerView.Adapter<SetListAdapter.ViewHolder>() {

    private lateinit var bottomSheetListView: BottomSheetListView
    private var context = context
    private var fragmentManager = fragmentManager
    private var countArrayList: ArrayList<String> = ArrayList()
    private var weightArrayList: ArrayList<Double> = ArrayList(setSize-1)
    private var repArrayList: ArrayList<Int> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val setTitleTextView:TextView
        val textView: TextView
        val editText: EditText

        init {
            textView = view.findViewById(R.id.choiceExerciseCount)
            editText = view.findViewById(R.id.weightEditText)
            setTitleTextView = view.findViewById(R.id.setTitleTextView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.exercise_input_row_item,viewGroup,false)
        for(i:Int in 0..SET_SIZE) {
            countArrayList.add(i, "${i}")
        }
        for(i:Int in 0..setSize-1) {
            weightArrayList.add(i, 60.0)
            repArrayList.add(i,12)
        }


        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        viewHolder.setTitleTextView.text = "${position+1} SET"
        viewHolder.textView.setOnClickListener {
            bottomSheetListView = BottomSheetListView(context,countArrayList)
            bottomSheetListView.show(fragmentManager,"BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object:
                BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String,clickItemPosition:Int) {
                        viewHolder.textView.text = clickItem
                        repArrayList.add(position,Integer.parseInt(clickItem))
                }
            })
        }


            viewHolder.editText.addTextChangedListener {
                if(TextUtils.isEmpty(viewHolder.editText.text.toString())){
                    weightArrayList.removeAt(position)
                    weightArrayList.add(position,60.0)
                } else {
                    weightArrayList.removeAt(position)
                    weightArrayList.add(position,(viewHolder.editText.text.toString().toDouble()))
                }

            }
    }
    fun changeSetSize(changeSetSize:Int){
        setSize = changeSetSize
        weightArrayList = arrayListOf()
        repArrayList = arrayListOf()
        for(i:Int in 0..setSize-1) {
            weightArrayList.add(i, 60.0)
            repArrayList.add(i,12)
        }
        notifyDataSetChanged()
    }
    fun getWeightArrayList():ArrayList<Double>{
        return weightArrayList
    }
    fun getRepArrayList():ArrayList<Int>{
        return repArrayList
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = setSize


}

