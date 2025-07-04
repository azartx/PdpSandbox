package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.utils

interface SimpleViewModel<State, ErrorState> {

    fun subscribeOnState(observer: UiStateObserver<State, ErrorState>)

    fun unsubscribe()
}