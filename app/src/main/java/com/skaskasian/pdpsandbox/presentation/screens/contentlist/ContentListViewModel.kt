package com.skaskasian.pdpsandbox.presentation.screens.contentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skaskasian.pdpsandbox.App
import com.skaskasian.pdpsandbox.data.repository.ContentRepository
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging.ContentPagingSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 5

class ContentListViewModel(
    private val contentRepository: ContentRepository
) : ViewModel() {

    val content = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        pagingSourceFactory = { ContentPagingSource(contentRepository) }
    )
        .flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)
}

@Suppress("UNCHECKED_CAST")
class ContentListViewModelFactory(
    private val contentRepository: ContentRepository = ContentRepository(App.contentDao)
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContentListViewModel(contentRepository) as T
    }
}