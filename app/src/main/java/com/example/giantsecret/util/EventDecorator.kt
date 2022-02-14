package com.example.giantsecret.util

import android.content.Context
import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class EventDecorator(
    private val context: Context,
    private val stringProductColor: Array<String>,
    private val dates: CalendarDay,
    private val count: Int
    )  : DayViewDecorator {

        private lateinit var colors: IntArray


    override fun shouldDecorate(day: CalendarDay?): Boolean = dates == day
    override fun decorate(view: DayViewFacade) {
        colors = IntArray(stringProductColor.size)
        stringProductColor.forEachIndexed { index, s ->
                colors[index] = Color.parseColor(s)
            }

        view.addSpan(CustomMultipleDotSpan(7f,count,colors))

        }


    }

