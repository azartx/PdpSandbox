package com.skaskasian.pdpsandbox.presentation.screens.patterns

import android.animation.ValueAnimator
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.animation.Animation
import android.widget.TextView

interface StringSkeletonDelegate {

    fun bindViews(views: List<TextView>)

    fun unbindViews()

    fun applyTextSkeletons()

    fun dismissTextSkeletons()
}

class StringSkeletonDelegateImpl : StringSkeletonDelegate, ValueAnimator.AnimatorUpdateListener {

    private val views: MutableList<TextView> = mutableListOf()

    private val skeletonAnimator = ValueAnimator.ofInt(255, 0, 255).apply {
        duration = 1500
        repeatCount = Animation.INFINITE
        addUpdateListener(this@StringSkeletonDelegateImpl)
    }

    private val spannableStringBuilder = SpannableStringBuilder("                                         ")

    override fun bindViews(views: List<TextView>) {
        this.views.addAll(views)
    }

    override fun unbindViews() {
        dismissTextSkeletons()
        views.clear()
    }

    override fun applyTextSkeletons() {
        skeletonAnimator.start()
    }

    override fun dismissTextSkeletons() {
        skeletonAnimator.cancel()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        val alpha = animation.animatedValue as Int
        views.forEach {
            spannableStringBuilder.clearSpans()
            spannableStringBuilder.setSpan(
                BackgroundColorSpan(Color.argb(alpha,128, 128, 128)),
                0,
                spannableStringBuilder.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            it.text = spannableStringBuilder
        }
    }
}