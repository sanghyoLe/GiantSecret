package com.example.giantsecret.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.spans.DotSpan.DEFAULT_RADIUS
import java.lang.Integer.parseInt
import java.util.*

class CustomMultipleDotSpan(private val radius: Float, private var color: Int) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        bottom: Int,
        baseline: Int,
        charsequence: CharSequence,
        start: Int,
        end: Int,
        lineNum: Int
    ) {
        val total = color
        var leftMost = (total - 1) * -12
        for(i in 0 until total){

            canvas.drawCircle(((left + right) / 2 - leftMost).toFloat(), bottom + radius, radius, paint)
            leftMost += 24
        }

        }
}