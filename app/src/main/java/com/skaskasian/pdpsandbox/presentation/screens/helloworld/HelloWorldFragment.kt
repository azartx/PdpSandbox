package com.skaskasian.pdpsandbox.presentation.screens.helloworld

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.presentation.screens.patterns.observer.createPublisher

class HelloWorldFragment : Fragment() {

    companion object {

        val commandsPublisher = createPublisher<Boolean>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LinearLayout(inflater.context).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            orientation = VERTICAL

            applyViews()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun LinearLayout.applyViews() {
        addView(TextView(context).apply {
            layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            text = "This is the initial application fragment based on Main Activity"
            gravity = Gravity.CENTER_HORIZONTAL
        })
        addView(CheckBox(context).apply {
            text = "Toasts spamming"
            isChecked = false

            setOnClickListener {
                checkCommandsState(isChecked)
            }
        })
    }

    private fun checkCommandsState(isChecked: Boolean): Boolean {
        commandsPublisher.notify(isChecked)
        return true
    }
}