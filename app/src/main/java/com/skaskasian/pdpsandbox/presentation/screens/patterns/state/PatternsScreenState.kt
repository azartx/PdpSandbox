package com.skaskasian.pdpsandbox.presentation.screens.patterns.state

import com.skaskasian.pdpsandbox.presentation.screens.patterns.model.LandingModel

/*
* Паттерн State
*
* Служит для хранения состояния программы.
*
* Ниже представлен силд класс, описывающий состояния. Также эти состояния должны где-то храниться.
* Храниться они могут, к примеру, в каком-то стэйт холдере (может быть вью модель, к примеру)
*
* Паттерн решает проблему хранения/обновления состояния программы.
* С помощью паттерна можно удобно обновлять состояние, переиспользовать при необходимости, логгировать.
*
* */
sealed interface PatternsScreenState {

    data object Loading : PatternsScreenState

    data class Content(val screenModel: LandingModel, val isLiked: Boolean) : PatternsScreenState

    data class Error(val message: String) : PatternsScreenState
}