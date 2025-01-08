package com.skaskasian.pdpsandbox.presentation.screens.contentlist.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.skaskasian.pdpsandbox.data.model.Content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContentDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenContent = MutableStateFlow<Content>(
        savedStateHandle[ContentDetailsFragment.CONTENT_DETAILS_KEY]!!
    )
    val screenState = _screenContent.asStateFlow()
}