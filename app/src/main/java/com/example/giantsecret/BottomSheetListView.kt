package com.example.giantsecret

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetListView(context: Context, arrayList: ArrayList<String>) : BottomSheetDialogFragment() {
    private lateinit var onClickListener: onDialogClickListener
    private var adapter = ArrayAdapter<String>(context
        ,android.R.layout.simple_expandable_list_item_1
        ,arrayList
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.custom_bottom_sheet, container, false)

        var listView = view.findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener{ _, _, position, _ ->
            onClickListener.onClicked(adapter.getItem(position).toString(),position)
            dismiss()
        }
        return view
    }

    fun setOnClickListener(listener: onDialogClickListener) {
        onClickListener = listener
    }



    interface onDialogClickListener
    {
        fun onClicked(clickItem: String, clickItemPosition:Int)
    }
}


