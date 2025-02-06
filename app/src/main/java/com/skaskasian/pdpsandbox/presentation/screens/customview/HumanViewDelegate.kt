package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Picture
import android.os.Parcelable
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.record
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HandWaveDirection
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HandWaveState
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.SavedState
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val HAND_WAVES_DURATION_MILLIS = 800L
private const val HAND_WAVES_MAX_THREE = 2
private const val DESIRED_SPEED_IN_SEC = 200
private const val STANDARD_DENSITY = 160f

interface HumanViewDelegate<V : View> {

    fun bindDelegate(view: V)
    fun unbindDelegate()

    fun dispatchOnLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) = Unit
    fun dispatchOnDraw(canvas: Canvas) = Unit

    fun dispatchOnSaveInstantState(parcelable: Parcelable?): Parcelable? = parcelable
    fun dispatchOnRestoreInstantState(state: Parcelable?)

    fun dispatchSayHello(onEnd: () -> Unit)
}

class HumanViewDelegateImpl : HumanViewDelegate<HumanView>, ValueAnimator.AnimatorUpdateListener {

    private var _view: HumanView? = null
    private val view: HumanView; get() = _view!!

    private var animator: ValueAnimator? = null
    private var humanPictureWithoutRightHand: Picture? = null

    private val headPaint: Paint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(
                view.resources,
                R.color.gray,
                view.context.theme
            )
        }
    }

    private val bodyPaint: Paint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(
                view.resources,
                R.color.blue,
                view.context.theme
            )
            strokeWidth = 3f
        }
    }

    private val handsFeetNeckPaint: Paint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(
                view.resources,
                R.color.body,
                view.context.theme
            )
        }
    }

    private var handWaveState = HandWaveState(handWaveValue = 0f, HandWaveDirection.UP)
    private var onHandWaveEndedCallback: (() -> Unit)? = null

    private var speedPxPerSec: Float = 0f

    private var centerX: Float = 0f
    private var headRadius: Float = 0f
    private var neckStartY: Float = 0f
    private var neckStopY: Float = 0f
    private var handsStopY: Float = 0f
    private var leftHandX: Float = 0f
    private var rightHandY: Float = 0f
    private var bodyStopY: Float = 0f
    private var feetStopY: Float = 0f
    private var leftFeetX: Float = 0f
    private var rightFeetX: Float = 0f
    private var armAngle = 0f
    private val rightAngle: Float; get() = 0 - armAngle

    override fun bindDelegate(view: HumanView) {
        this._view = view
    }

    override fun unbindDelegate() {
        onHandWaveEndedCallback = null
        animator?.removeUpdateListener(this)
        animator?.cancel()
        animator = null
        _view = null
    }

    override fun dispatchOnLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val minScreenSize = minOf(right, bottom)
        centerX = minScreenSize / 2f
        headRadius = (minScreenSize / 3.5f) / PI.toFloat()
        neckStartY = headRadius * 2f
        neckStopY = neckStartY + (headRadius / 1.5f)
        handsStopY = neckStopY + ((neckStopY - neckStartY) * 3f)
        leftHandX = centerX - headRadius
        rightHandY = handsStopY
        bodyStopY = neckStopY + ((neckStopY - neckStartY) * 4f)
        feetStopY = bodyStopY + ((neckStopY - neckStartY) * 3f)
        leftFeetX = centerX - headRadius
        rightFeetX = centerX + headRadius

        val speedDpPerSec = DESIRED_SPEED_IN_SEC / STANDARD_DENSITY
        speedPxPerSec = speedDpPerSec * view.resources.displayMetrics.density
    }

    override fun dispatchOnDraw(canvas: Canvas) {
        humanPictureWithoutRightHand?.draw(canvas) ?: apply {
            humanPictureWithoutRightHand = Picture().record(view.width, view.height) {
                // head
                drawCircle(centerX, headRadius, headRadius, headPaint)

                // neck
                drawLine(centerX, neckStartY, centerX, neckStopY, handsFeetNeckPaint)

                // left hand
                drawLine(centerX, neckStopY, leftHandX, handsStopY, handsFeetNeckPaint)

                // body
                drawLine(centerX, neckStopY, centerX, bodyStopY, bodyPaint)

                // feet
                drawLine(centerX, bodyStopY, leftFeetX, feetStopY, handsFeetNeckPaint)
                drawLine(centerX, bodyStopY, rightFeetX, feetStopY, handsFeetNeckPaint)
            }.apply { draw(canvas) }
        }

        // right hand
        val rightHandX = centerX + headRadius * cos(Math.toRadians(rightAngle.toDouble())).toFloat()
        canvas.drawLine(centerX, neckStopY, rightHandX, rightHandY, handsFeetNeckPaint)
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
        startHandWaveAnimation()

    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        armAngle = animation.animatedValue as Float
        rightHandY = handsStopY * sin(Math.toRadians(rightAngle.toDouble())).toFloat()
        _view?.invalidate()
    }

    private fun startHandWaveAnimation() {
        animator = ValueAnimator.ofFloat(0f, 60f, 0f).apply {
            duration = HAND_WAVES_DURATION_MILLIS
            repeatCount = HAND_WAVES_MAX_THREE
            addUpdateListener(this@HumanViewDelegateImpl)
            doOnEnd {
                rightHandY = handsStopY
                _view?.invalidate()
                onHandWaveEndedCallback?.invoke()
            }
            start()
        }
    }
}