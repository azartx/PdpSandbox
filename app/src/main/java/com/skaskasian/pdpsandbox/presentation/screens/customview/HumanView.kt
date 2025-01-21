package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.content.Context
import android.graphics.Canvas
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

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

    override fun onDraw(canvas: Canvas) {
        dispatchOnDraw(canvas)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return dispatchOnSaveInstantState(super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        dispatchOnRestoreInstantState(state)
    }
}