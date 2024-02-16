package com.skaskasian.pdpsandbox.presentation.screens.animations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class AnimationsViewModel : ViewModel() {



    fun shouldStopAnyActions() {

    }

    override fun onCleared() {
       // runCatching { animationScope.cancel() }
        super.onCleared()
    }
}