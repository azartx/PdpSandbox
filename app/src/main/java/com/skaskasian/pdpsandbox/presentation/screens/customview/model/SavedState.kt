package com.skaskasian.pdpsandbox.presentation.screens.customview.model

import android.os.Parcelable
import android.view.View.BaseSavedState
import kotlin.properties.Delegates

class SavedState(superState: Parcelable) : BaseSavedState(superState) {

    var humanWaveState: HandWaveState by Delegates.notNull()
}