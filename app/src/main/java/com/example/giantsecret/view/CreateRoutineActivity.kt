package com.example.giantsecret.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.giantsecret.R

private lateinit var routineNameEditText: EditText
class CreateRoutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_routine)
        routineNameEditText = findViewById(R.id.routineNameEditText)
        val btn = findViewById<Button>(R.id.addRoutineBtn)

        btn.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(routineNameEditText.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = routineNameEditText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY,name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.routinelistsql.REPLY"
    }
}