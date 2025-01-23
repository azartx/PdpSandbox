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
import kotlin.math.PI

private const val HAND_WAVES_MAX = 3
private const val DESIRED_SPEED_IN_SEC = 200
private const val STANDARD_DENSITY = 160f

interface HumanViewDelegate<V : View> : Choreographer.FrameCallback {

    fun bindDelegate(view: V)
    fun unbindDelegate()

    fun dispatchOnLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) = Unit
    fun dispatchOnDraw(canvas: Canvas) = Unit

    fun dispatchOnSaveInstantState(parcelable: Parcelable?): Parcelable? = parcelable
    fun dispatchOnRestoreInstantState(state: Parcelable?)

    fun dispatchSayHello(onEnd: () -> Unit)
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
    private var isHandWaveActivated: Boolean = false
    private var handWavesCont: Int = 0
    private var onHandWaveEndedCallback: (() -> Unit)? = null

    private var speedPxPerSec: Float = 0f

    private var centerX: Float = 0f
    private var headRadius: Float = 0f
    private var neckStartY: Float = 0f
    private var neckStopY: Float = 0f
    private var handsStopY: Float = 0f
    private var leftHandX: Float = 0f
    private var bodyStopY: Float = 0f
    private var feetStopY: Float = 0f
    private var leftFeetX: Float = 0f
    private var rightFeetX: Float = 0f

    override fun bindDelegate(view: HumanView) {
        this._view = view
    }

    override fun unbindDelegate() {
        onHandWaveEndedCallback = null
        choreographer.removeFrameCallback(this)
        _view = null
    }

    override fun doFrame(frameTimeNanos: Long) {
        if (isHandWaveActivated) {
            changeHandWaveState()
            view.invalidate()
            choreographer.postFrameCallback(this)
        } else {
            choreographer.removeFrameCallback(this)
        }
    }

    override fun dispatchOnLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val minScreenSize = minOf(right, bottom)
        centerX = minScreenSize / 2f
        headRadius = (minScreenSize / 3.5f) / PI.toFloat()
        neckStartY = headRadius * 2f
        neckStopY = neckStartY + (headRadius / 1.5f)
        handsStopY = neckStopY + ((neckStopY - neckStartY) * 3f)
        leftHandX = centerX - headRadius
        bodyStopY = neckStopY + ((neckStopY - neckStartY) * 4f)
        feetStopY = bodyStopY + ((neckStopY - neckStartY) * 3f)
        leftFeetX = centerX - headRadius
        rightFeetX = centerX + headRadius

        val speedDpPerSec = DESIRED_SPEED_IN_SEC / STANDARD_DENSITY
        speedPxPerSec = speedDpPerSec * view.resources.displayMetrics.density
    }

    override fun dispatchOnDraw(canvas: Canvas) {
        val rightHandX = centerX + headRadius + handWaveState.handWaveValue
        val rightHandY = handsStopY - handWaveState.handWaveValue

        // head
        canvas.drawCircle(centerX, headRadius, headRadius, painter)

        // neck
        canvas.drawLine(centerX, neckStartY, centerX, neckStopY, painter)

        // hands
        canvas.drawLine(centerX, neckStopY, leftHandX, handsStopY, painter)
        canvas.drawLine(centerX, neckStopY, rightHandX, rightHandY, painter)

        // body
        canvas.drawLine(centerX, neckStopY, centerX, bodyStopY, painter)

        // feet
        canvas.drawLine(centerX, bodyStopY, leftFeetX, feetStopY, painter)
        canvas.drawLine(centerX, bodyStopY, rightFeetX, feetStopY, painter)
    }

    // todo calculate valid handWave
    private fun changeHandWaveState() {
        if (handWaveState.handWaveDirection == HandWaveDirection.UP) {
            val newValue = handWaveState.handWaveValue + speedPxPerSec
            handWaveState = if (newValue >= 180) {
                handWaveState.copy(handWaveDirection = HandWaveDirection.DOWN)
            } else {
                handWaveState.copy(handWaveValue = newValue)
            }
        } else {
            val newValue = handWaveState.handWaveValue - speedPxPerSec
            handWaveState = if (newValue <= 0) {
                if (shouldEndHandWaving()) {
                    return
                }

                handWaveState.copy(handWaveDirection = HandWaveDirection.UP)
            } else {
                handWaveState.copy(handWaveValue = newValue)
            }
        }
    }

    // ends animation while hand waves reached 3 times
    private fun shouldEndHandWaving(): Boolean {
        ++handWavesCont
        return if (handWavesCont >= HAND_WAVES_MAX) {
            handWavesCont = 0
            isHandWaveActivated = false
            onHandWaveEndedCallback?.invoke()
            onHandWaveEndedCallback = null
            true
        } else {
            false
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

    override fun dispatchSayHello(onEnd: () -> Unit) {
        onHandWaveEndedCallback = onEnd
        isHandWaveActivated = true
        choreographer.postFrameCallback(this)
    }
}