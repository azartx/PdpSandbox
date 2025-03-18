package com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto

import com.google.gson.annotations.SerializedName


data class ElementMeasurements(
    @SerializedName("Height") val Height: Double? = null,
    @SerializedName("Width") val Width: Double? = null

)