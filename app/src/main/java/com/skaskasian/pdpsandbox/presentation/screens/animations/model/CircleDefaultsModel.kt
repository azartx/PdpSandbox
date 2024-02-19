package com.skaskasian.pdpsandbox.presentation.screens.animations.model

data class CircleDefaultsModel(
    val scaleXY: Pair<Float, Float>,
    val positionXY: Pair<Float, Float>,
    val alpha: Float = 1f,
    val rotation: Pair<Float, Float> = 0f to 0f
)