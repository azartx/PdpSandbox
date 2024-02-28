package com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.fragment.app.Fragment

class SimpleMotionFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            gravity = Gravity.CENTER
            setBackgroundColor(Color.GRAY)
            text = "Fragment ${this.hashCode()}\n\n"
                .plus("Swipe left or right side to change current fragment!")
        }
    }
}