package com.example.giantsecret

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetListView(context: Context, arrayList: ArrayList<String>, isEditable:Boolean) : BottomSheetDialogFragment() {
    private lateinit var onClickListener: onDialogClickListener
    private var adapter = ArrayAdapter<String>(context
        ,android.R.layout.simple_expandable_list_item_1
        ,arrayList
    )
    private var isEditable:Boolean = isEditable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.custom_dialog_spinner_layout, container, false)

        var listView = view.findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
        if(isEditable) {
            var editText:EditText = view.findViewById(R.id.editText)
            editText.visibility = View.VISIBLE
            editText.addTextChangedListener { s ->
                adapter.filter.filter(s);
            }
        }
        listView.setOnItemClickListener{ _, _, position, _ ->
            onClickListener.onClicked(adapter.getItem(position).toString())
            dismiss()
        }
        return view
    }
    fun setOnClickListener(listener: onDialogClickListener) {
        onClickListener = listener
    }



    interface onDialogClickListener
    {
        fun onClicked(clickItem: String)
    }
}


