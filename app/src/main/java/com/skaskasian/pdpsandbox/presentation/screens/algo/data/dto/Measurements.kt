package com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto

import com.google.gson.annotations.SerializedName


data class Measurements(
    @SerializedName("elementName") val elementName: String? = null,
    @SerializedName("elementDescription") val elementDescription: String? = null,
    @SerializedName("elementMeasurements") val elementMeasurements: ElementMeasurements? = ElementMeasurements()

)