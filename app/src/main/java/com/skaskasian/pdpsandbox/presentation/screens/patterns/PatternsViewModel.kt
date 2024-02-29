package com.skaskasian.pdpsandbox.presentation.screens.patterns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skaskasian.pdpsandbox.presentation.screens.patterns.factory.LendingModelFactory
import com.skaskasian.pdpsandbox.presentation.screens.patterns.state.PatternsScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PatternsViewModel : ViewModel() {

    // Поведенческий паттерн Состояние
    private val _screenState = MutableStateFlow<PatternsScreenState>(PatternsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val landingFactory = LendingModelFactory()

    init {
        viewModelScope.launch {
            delay(4000)
            updateLanding()
        }
    }

    fun onRefreshLandingClicked() {
        if (_screenState.value == PatternsScreenState.Loading) return

        viewModelScope.launch {
            _screenState.emit(PatternsScreenState.Loading)
            delay(4000)
            updateLanding()
        }
    }

    private suspend fun updateLanding() {
        _screenState.emit(PatternsScreenState.Content(landingFactory.createRandomLanding()))
    }
}