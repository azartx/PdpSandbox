package com.skaskasian.pdpsandbox.presentation.screens.contentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skaskasian.pdpsandbox.App
import com.skaskasian.pdpsandbox.data.repository.ContentRepository
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging.ContentPagingSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ContentListViewModel(
    private val contentRepository: ContentRepository
) : ViewModel() {

    val content = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { ContentPagingSource(contentRepository) }
    )
        .flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}

@Suppress("UNCHECKED_CAST")
class ContentListViewModelFactory(
    private val contentRepository: ContentRepository = ContentRepository(App.contentDao)
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContentListViewModel(contentRepository) as T
    }
}