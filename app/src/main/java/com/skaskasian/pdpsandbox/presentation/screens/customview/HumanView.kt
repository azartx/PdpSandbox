package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.content.Context
import android.graphics.Canvas
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.presentation.screens.customview.mapper.HumanViewAttrsMapper
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HumanViewColors

class HumanView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes),
    HumanViewDelegate<HumanView> by HumanViewDelegateImpl() {

    private val colors: HumanViewColors

    init {
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.HumanView,
            defStyleAttr,
            defStyleRes
        )
        colors = HumanViewAttrsMapper.map(
            attributes,
            ResourcesCompat.getColor(context.resources, R.color.colorSecondary, context.theme)
        )
        attributes.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bindDelegate(view = this, colors)
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
        super.onRestoreInstanceState(state)
        dispatchOnRestoreInstantState(state)
    }

    fun sayHello(onEnd: () -> Unit) {
        dispatchSayHello(onEnd)
    }
}