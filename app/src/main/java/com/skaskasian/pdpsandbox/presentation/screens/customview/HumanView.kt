package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class HumanView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val painter: Paint by lazy { Paint().apply { color = Color.WHITE } }

    private var handWaveValue = 1f

    init {
        applyHandWave()
    }

    private fun applyHandWave() {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.setDuration(2000)
        animator.repeatCount = -1
        animator.addUpdateListener {
            if (handWaveValue > 120) {
                handWaveValue = (-1f)
                return@addUpdateListener
            }
            if (handWaveValue < -120) {
                handWaveValue = 1f

                return@addUpdateListener
            }

            if (handWaveValue > 0f) {
                handWaveValue += 1f
            }
            if (handWaveValue < 0f) {
                handWaveValue -= 1f
            }
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f

        val headRadius = (width / 3.5f) / 3.14f
        canvas.drawCircle(centerX, headRadius, headRadius, painter)

        val neckStartY = headRadius * 2f
        val neckStopY = neckStartY + (headRadius / 1.5f)
        canvas.drawLine(centerX, neckStartY, centerX, neckStopY, painter)

        val handsStopY = neckStopY + ((neckStopY - neckStartY) * 3f)
        val leftHandX = centerX - headRadius
        val rightHandX = centerX + headRadius + handWaveValue
        canvas.drawLine(centerX, neckStopY, leftHandX, handsStopY, painter)
        canvas.drawLine(centerX, neckStopY, rightHandX, handsStopY, painter)

        val bodyStopY = neckStopY + ((neckStopY - neckStartY) * 4f)
        canvas.drawLine(centerX, neckStopY, centerX, bodyStopY, painter)

        val feetStopY = bodyStopY + ((neckStopY - neckStartY) * 3f)
        val leftFeetX = centerX - headRadius
        val rightFeetX = centerX + headRadius
        canvas.drawLine(centerX, bodyStopY, leftFeetX, feetStopY, painter)
        canvas.drawLine(centerX, bodyStopY, rightFeetX, feetStopY, painter)
    }
}