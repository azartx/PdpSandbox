package com.skaskasian.pdpsandbox.presentation.screens.patterns.facade

import android.content.Context
import android.graphics.Color
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

/*Паттерн фасад
* В коде не используется
*
* AmazingViewFacade скрывает сложную реализацию AmazingView за интерфейсом под одним методом createAmazingView
*
* */

interface AmazingViewFacade {

    fun createAmazingView(context: Context): View
}

class AmazingViewFacadeImpl : AmazingViewFacade {

    private companion object {

        private const val WIDTH = 200
        private const val HEIGHT = 200
    }

    override fun createAmazingView(context: Context): View {
        return composeAmazingView(context)
    }

    private fun composeAmazingView(context: Context): View {
        return LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                this.gravity = CENTER_VERTICAL
            }

            addViews(
                createAmazingViewHeader(context),
                createAmazingViewBody(context),
                createAmazingViewFooter(context)
            )
        }
    }

    private fun createAmazingViewHeader(context: Context): View {
        return FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(WIDTH, HEIGHT)
            setBackgroundColor(Color.BLUE)

            addText(this, "Header")
        }
    }

    private fun addText(parent: ViewGroup, text: String) {
        parent.addView(
            TextView(parent.context).apply {
                layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

                this.text = text
                gravity = CENTER_HORIZONTAL
            }
        )
    }

    private fun createAmazingViewBody(context: Context): View {
        return LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(WIDTH, HEIGHT).apply {
                this.gravity = CENTER_VERTICAL
            }
            setBackgroundColor(Color.YELLOW)

            addText(this, "Body")
            addText(this, "Body Description")
        }
    }
    private fun createAmazingViewFooter(context: Context): View {
        return FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(WIDTH, HEIGHT)
            setBackgroundColor(Color.GREEN)

            addText(this, "Footer")
        }
    }

    private fun ViewGroup.addViews(vararg views: View) {
        views.forEach { addView(it) }
    }
}