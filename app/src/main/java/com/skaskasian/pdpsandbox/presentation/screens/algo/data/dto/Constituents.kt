package com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto

import com.google.gson.annotations.SerializedName

data class Constituents(
    @SerializedName("constituentID") val constituentID: Int? = null,
    @SerializedName("role") val role: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("constituentULAN_URL") val constituentULANURL: String? = null,
    @SerializedName("constituentWikidata_URL") val constituentWikidataURL: String? = null,
    @SerializedName("gender") val gender: String? = null

)