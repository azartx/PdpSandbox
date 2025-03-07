package com.skaskasian.pdpsandbox.presentation.screens.composeanim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class ComposeAnimFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            setContent {
                Content(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun Content(modifier: Modifier) {
    var shouldStartAnimation by remember { mutableStateOf(false) }
    var isClickEnabled by remember { mutableStateOf(true) }

    val animatedSize by animateDpAsState(
        targetValue = if (shouldStartAnimation) 100.dp else 50.dp,
        label = "animatedWidth",
        finishedListener = {
            shouldStartAnimation = false
            isClickEnabled = true
        }
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .size(animatedSize)
                    .clickable(enabled = isClickEnabled) {
                        isClickEnabled = false
                        shouldStartAnimation = true
                    }
            ) {
                drawRect(
                    color = Color.Red
                )
            }
            Text(text = "Tap!", fontWeight = FontWeight.Bold)
        }
    }
}