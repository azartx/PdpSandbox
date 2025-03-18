package com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto

import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("term") val term: String? = null,
    @SerializedName("AAT_URL") val AATURL: String? = null,
    @SerializedName("Wikidata_URL") val WikidataURL: String? = null

)