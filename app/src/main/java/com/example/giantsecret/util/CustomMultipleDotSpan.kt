package com.example.giantsecret.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.LineBackgroundSpan

class CustomMultipleDotSpan(private val radius: Float, private var count: Int, private var colors: IntArray) : LineBackgroundSpan {
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
        val total = count
        var leftMost = (total - 1) * -12
        for(i in 0 until total){
            val oldColor = paint.color
            paint.color = colors[i]
            
            canvas.drawCircle(((left + right) / 2 - leftMost).toFloat(), bottom + radius, radius, paint)
            paint.color = oldColor
            leftMost += 24
        }

        }
}