package com.skaskasian.pdpsandbox.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.skaskasian.pdpsandbox.R

class CircleView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint: Paint = Paint().apply {
        color = ResourcesCompat.getColor(context.resources, R.color.colorSecondary, context.theme)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            (width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2).toFloat(), paint
        )
    }
}