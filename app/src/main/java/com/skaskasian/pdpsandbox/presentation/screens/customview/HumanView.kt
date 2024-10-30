package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View

class HumanView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), Choreographer.FrameCallback {

    private val painter: Paint by lazy { Paint().apply { color = Color.WHITE } }

    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private var handWaveState: HandWaveState = HandWaveState(handWaveValue = 0f, HandWaveDirection.UP)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        choreographer.postFrameCallback(this)
    }

    override fun doFrame(frameTimeNanos: Long) {
        changeHandWaveState()
        invalidate()
        choreographer.postFrameCallback(this)
    }

    override fun onDetachedFromWindow() {
        choreographer.removeFrameCallback(this)
        super.onDetachedFromWindow()
    }

    // TODO calculate default positions in onMeasure
    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f

        val headRadius = (width / 3.5f) / 3.14f
        canvas.drawCircle(centerX, headRadius, headRadius, painter)

        val neckStartY = headRadius * 2f
        val neckStopY = neckStartY + (headRadius / 1.5f)
        canvas.drawLine(centerX, neckStartY, centerX, neckStopY, painter)

        val handsStopY = neckStopY + ((neckStopY - neckStartY) * 3f)
        val leftHandX = centerX - headRadius
        val rightHandX = centerX + headRadius + handWaveState.handWaveValue
        canvas.drawLine(centerX, neckStopY, leftHandX, handsStopY, painter)
        canvas.drawLine(centerX, neckStopY, rightHandX, handsStopY - handWaveState.handWaveValue, painter)

        val bodyStopY = neckStopY + ((neckStopY - neckStartY) * 4f)
        canvas.drawLine(centerX, neckStopY, centerX, bodyStopY, painter)

        val feetStopY = bodyStopY + ((neckStopY - neckStartY) * 3f)
        val leftFeetX = centerX - headRadius
        val rightFeetX = centerX + headRadius
        canvas.drawLine(centerX, bodyStopY, leftFeetX, feetStopY, painter)
        canvas.drawLine(centerX, bodyStopY, rightFeetX, feetStopY, painter)
    }

    private fun changeHandWaveState() {
        if (handWaveState.handWaveDirection == HandWaveDirection.UP) {
            val newValue = handWaveState.handWaveValue + 1f
            handWaveState = if (newValue >= 120) {
                handWaveState.copy(handWaveDirection = HandWaveDirection.DOWN)
            } else {
                handWaveState.copy(handWaveValue = newValue)
            }
        } else {
            val newValue = handWaveState.handWaveValue - 1f
            handWaveState = if (newValue <= 0) {
                handWaveState.copy(handWaveDirection = HandWaveDirection.UP)
            } else {
                handWaveState.copy(handWaveValue = newValue)
            }
        }
    }
}

private data class HandWaveState(
    val handWaveValue: Float,
    val handWaveDirection: HandWaveDirection
)

private enum class HandWaveDirection {
    UP, DOWN
}