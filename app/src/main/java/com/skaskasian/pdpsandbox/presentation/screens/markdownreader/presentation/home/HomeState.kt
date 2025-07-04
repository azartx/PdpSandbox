package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.presentation.home

data class HomeState(
    val files: List<String> = emptyList(),
    val isLoading: Boolean = false,
)
