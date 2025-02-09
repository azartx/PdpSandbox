package com.skaskasian.pdpsandbox.presentation.screens.customview.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState
import kotlinx.parcelize.Parcelize

@Parcelize
class SavedState(
    @Suppress("CanBeParameter")
    private val superState: Parcelable?
) : BaseSavedState(superState) {

    @Suppress("unused") constructor(parcel: Parcel) : this(
        superState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcel.readParcelable(SavedState::class.java.classLoader, SavedState::class.java)
        } else {
            parcel.readParcelable(SavedState::class.java.classLoader)
        }
    )
}