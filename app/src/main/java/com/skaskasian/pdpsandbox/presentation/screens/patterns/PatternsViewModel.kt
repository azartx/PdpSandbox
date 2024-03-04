package com.skaskasian.pdpsandbox.presentation.screens.patterns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skaskasian.pdpsandbox.data.repository.PatternsRepository
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
    private val landingInfoRepository = PatternsRepository()

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
        val landingModel = landingFactory.createRandomLanding()

        _screenState.emit(PatternsScreenState.Content(landingModel, landingInfoRepository.isLiked(landingModel.id)))
    }

    fun onLikeButtonClicked() {
        val currentLanding = (_screenState.value as? PatternsScreenState.Content) ?: return

        val isLiked = landingInfoRepository.isLiked(currentLanding.screenModel.id)
        if (isLiked) {
            landingInfoRepository.removeLikeFromLanding(currentLanding.screenModel.id)
        } else {
            landingInfoRepository.applyLikeToLanding(currentLanding.screenModel.id)
        }
        _screenState.tryEmit(currentLanding.copy(isLiked = !isLiked))
    }
}