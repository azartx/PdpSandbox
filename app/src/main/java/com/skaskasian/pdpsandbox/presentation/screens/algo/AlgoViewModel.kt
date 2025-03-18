package com.skaskasian.pdpsandbox.presentation.screens.algo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skaskasian.pdpsandbox.presentation.screens.algo.data.MuseumRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AlgoViewModel(private val repository: MuseumRepository) : ViewModel() {

    val content = MutableStateFlow(
        (0..1000).map { it.toString() }
    )

    fun onNewSearchTextInputted(text: CharSequence?) {

    }
}

@Suppress("UNCHECKED_CAST")
class AlgoViewModelFactory(private val repository: MuseumRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlgoViewModel(repository) as T
    }
}