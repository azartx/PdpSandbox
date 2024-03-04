package com.skaskasian.pdpsandbox.presentation.screens.patterns.state

import com.skaskasian.pdpsandbox.presentation.screens.patterns.model.LandingModel

sealed interface PatternsScreenState {

    data object Loading : PatternsScreenState

    data class Content(val screenModel: LandingModel, val isLiked: Boolean) : PatternsScreenState

    data class Error(val message: String) : PatternsScreenState
}