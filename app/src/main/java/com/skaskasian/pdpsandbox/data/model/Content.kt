package com.skaskasian.pdpsandbox.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val title: String,
    val description: String
) : Parcelable