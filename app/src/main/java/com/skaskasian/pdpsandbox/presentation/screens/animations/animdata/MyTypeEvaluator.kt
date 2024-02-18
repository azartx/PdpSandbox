package com.skaskasian.pdpsandbox.presentation.screens.animations.animdata

import android.animation.TypeEvaluator

class MyTypeEvaluator : TypeEvaluator<MyExtendedViewState> {
    override fun evaluate(
        fraction: Float,
        startValue: MyExtendedViewState,
        endValue: MyExtendedViewState
    ): MyExtendedViewState {
        return MyExtendedViewState(
            alpha = startValue.alpha + fraction * (endValue.alpha - startValue.alpha),
            scaleXY = startValue.scaleXY.first + fraction * (endValue.scaleXY.first - startValue.scaleXY.first) to
                    startValue.scaleXY.second + fraction * (endValue.scaleXY.second - startValue.scaleXY.second),
            positionX = startValue.positionX + fraction * (endValue.positionX - startValue.positionX)
        )
    }
}

data class MyExtendedViewState(
    val alpha: Float,
    val scaleXY: Pair<Float, Float>,
    val positionX: Float
)