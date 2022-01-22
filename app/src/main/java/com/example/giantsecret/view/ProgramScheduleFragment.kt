package com.example.giantsecret.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.viewpager2.widget.ViewPager2
import com.example.giantsecret.PagerFragmentStateAdapter
import com.example.giantsecret.R
import com.example.giantsecret.databinding.FragmentProgramScheduleBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProgramScheduleFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentProgramScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program_schedule, container, false)
        binding.lifecycleOwner = this
        onActivityCreate()
        return binding.root


    }

    // Activity onCreate 완료 후 실행될 Fragment 로직

    fun onActivityCreate(){
        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())

        pagerAdapter.addFragment(ProgramFragment.newInstance(1))
        pagerAdapter.addFragment(ProgramFragment.newInstance(2))
        pagerAdapter.addFragment(ProgramFragment.newInstance(3))
        pagerAdapter.addFragment(ProgramFragment.newInstance(4))
        pagerAdapter.addFragment(ProgramFragment.newInstance(5))
        pagerAdapter.addFragment(ProgramFragment.newInstance(6))

        binding.programViewPager2.adapter = pagerAdapter
        binding.programViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })

        TabLayoutMediator(binding.programTabLayout, binding.programViewPager2) { tab, position ->
            tab.text = "${position+1}주차"
        }.attach()
        // 로직 수행 후 Observer 제거
        activity?.lifecycle?.removeObserver(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)

    }




}