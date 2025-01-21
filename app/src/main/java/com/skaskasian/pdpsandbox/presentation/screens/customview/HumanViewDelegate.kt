package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcelable
import android.view.Choreographer
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HandWaveDirection
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HandWaveState
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.SavedState

interface HumanViewDelegate<V : View> : Choreographer.FrameCallback {

    fun bindDelegate(view: V)
    fun unbindDelegate()

    fun dispatchOnDraw(canvas: Canvas) = Unit

    fun dispatchOnSaveInstantState(parcelable: Parcelable?): Parcelable? = parcelable
    fun dispatchOnRestoreInstantState(state: Parcelable?)
}

class HumanViewDelegateImpl : HumanViewDelegate<HumanView> {

    private var _view: HumanView? = null
    private val view: HumanView; get() = _view!!

    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private val painter: Paint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(
                view.resources,
                R.color.colorSecondary,
                view.context.theme
            )
        }
    }

    private var handWaveState = HandWaveState(handWaveValue = 0f, HandWaveDirection.UP)

    override fun bindDelegate(view: HumanView) {
        choreographer.postFrameCallback(this)
        this._view = view
    }

    override fun unbindDelegate() {
        choreographer.removeFrameCallback(this)
        _view = null
    }

    override fun doFrame(frameTimeNanos: Long) {
        changeHandWaveState()
        view.invalidate()
        choreographer.postFrameCallback(this)
    }

    override fun dispatchOnDraw(canvas: Canvas) {
        val centerX = view.width / 2f
        val headRadius = (view.width / 3.5f) / 3.14f
        val neckStartY = headRadius * 2f
        val neckStopY = neckStartY + (headRadius / 1.5f)
        val handsStopY = neckStopY + ((neckStopY - neckStartY) * 3f)
        val leftHandX = centerX - headRadius
        val rightHandX = centerX + headRadius + handWaveState.handWaveValue
        val bodyStopY = neckStopY + ((neckStopY - neckStartY) * 4f)
        val feetStopY = bodyStopY + ((neckStopY - neckStartY) * 3f)
        val leftFeetX = centerX - headRadius
        val rightFeetX = centerX + headRadius
        val handWavePositionY = handsStopY - handWaveState.handWaveValue

        // head
        canvas.drawCircle(centerX, headRadius, headRadius, painter)

        // neck
        canvas.drawLine(centerX, neckStartY, centerX, neckStopY, painter)

        // hands
        canvas.drawLine(centerX, neckStopY, leftHandX, handsStopY, painter)
        canvas.drawLine(centerX, neckStopY, rightHandX, handWavePositionY, painter)

        // body
        canvas.drawLine(centerX, neckStopY, centerX, bodyStopY, painter)

        // feet
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

    override fun dispatchOnSaveInstantState(parcelable: Parcelable?): Parcelable? {
        return parcelable?.let {
            SavedState(it).apply {
                this.humanWaveState = handWaveState
            }
        }
    }

    override fun dispatchOnRestoreInstantState(state: Parcelable?) {
        (state as? SavedState)?.let {
            handWaveState = it.humanWaveState
        }
    }
}