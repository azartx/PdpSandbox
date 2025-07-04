package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.presentation.home

sealed interface HomeErrorState {

    object IncorrectUrlError : HomeErrorState
    object DownloadError : HomeErrorState
}