package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.utils

interface UiStateObserver<State, ErrorState> {

    fun onChanged(state: State)

    fun onError(errorState: ErrorState)
}