package com.example.giantsecret.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giantsecret.BottomSheetListView
import com.example.giantsecret.R
import com.example.giantsecret.ui.SET_SIZE

class SetListAdapter(private var setSize:Int, context: Context, fragmentManager: FragmentManager) :
    RecyclerView.Adapter<SetListAdapter.ViewHolder>() {
    private lateinit var bottomSheetListView: BottomSheetListView
    private var context = context
    private var fragmentManager = fragmentManager
    private var countArrayList: ArrayList<String> = ArrayList()

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
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setTitleTextView.text = "${position+1} SET"
        viewHolder.textView.setOnClickListener {
            bottomSheetListView = BottomSheetListView(context,countArrayList)
            bottomSheetListView.show(fragmentManager,"BOTTOM_SHEET")
            bottomSheetListView.setOnClickListener(object:
                BottomSheetListView.onDialogClickListener {
                override fun onClicked(clickItem: String,clickItemPosition:Int) {
                        viewHolder.textView.text = clickItem
                }
            })
        }
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

    }
    fun changeSetSize(changeSetSize:Int){
        setSize = changeSetSize
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = setSize

}

