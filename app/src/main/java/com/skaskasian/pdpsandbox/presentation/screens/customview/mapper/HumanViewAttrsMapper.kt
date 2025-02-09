package com.skaskasian.pdpsandbox.presentation.screens.customview.mapper

import android.content.res.TypedArray
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.presentation.screens.customview.model.HumanViewColors

object HumanViewAttrsMapper {

    fun map(attrs: TypedArray, defColor: Int): HumanViewColors {
        return HumanViewColors(
            body = attrs.getColor(R.styleable.HumanView_headColor, defColor),
            head = attrs.getColor(R.styleable.HumanView_bodyColor, defColor),
            feet = attrs.getColor(R.styleable.HumanView_feetColor, defColor)
        )
    }
}