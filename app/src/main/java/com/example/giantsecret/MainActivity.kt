package com.example.giantsecret

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.giantsecret.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var mViewModel:MviewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GiantSecret)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MviewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        mViewModel.showErrorToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,"모든 값을 입력하세요",Toast.LENGTH_SHORT).show()
            }
        })



        setContentView(binding.root)


    }
}