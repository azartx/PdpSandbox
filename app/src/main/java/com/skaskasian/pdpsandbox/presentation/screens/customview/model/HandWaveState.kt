package com.skaskasian.pdpsandbox.presentation.screens.customview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HandWaveState(
    val handWaveValue: Float,
    val handWaveDirection: HandWaveDirection
): Parcelable