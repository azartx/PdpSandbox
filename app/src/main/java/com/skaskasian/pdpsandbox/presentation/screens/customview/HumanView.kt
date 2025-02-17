package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.content.Context
import android.graphics.Canvas
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.SavedState

class HumanView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes),
    HumanViewDelegate<HumanView> by HumanViewDelegateImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bindDelegate(view = this)
    }

    override fun onDetachedFromWindow() {
        unbindDelegate()
        super.onDetachedFromWindow()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        dispatchOnLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        dispatchOnDraw(canvas)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return dispatchOnSaveInstantState(super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState((state as? SavedState)?.superState ?: state)
        dispatchOnRestoreInstantState(state)
    }

    fun sayHello(onEnd: () -> Unit) {
        dispatchSayHello(onEnd)
    }
}