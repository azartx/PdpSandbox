package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skaskasian.pdpsandbox.data.model.Content
import com.skaskasian.pdpsandbox.data.repository.ContentRepository

class ContentPagingSource(
    private val contentRepository: ContentRepository
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        val offset = params.key ?: 0

        val result = runCatching { contentRepository.getContent(params.loadSize, offset) }

        return result.getOrNull()
            ?.let { content ->
                LoadResult.Page(
                    data = content,
                    prevKey = getPreviousKey(offset, params),
                    nextKey = getNextKey(content, params, offset)
                )
            } ?: getErrorLoadResult(result.exceptionOrNull())
    }

    private fun getNextKey(
        content: List<Content>,
        params: LoadParams<Int>,
        offset: Int
    ): Int? {
        return if (content.isEmpty() || content.size < params.loadSize)
            null else offset + params.loadSize
    }

    private fun getPreviousKey(
        offset: Int,
        params: LoadParams<Int>
    ): Int? {
        return if (offset == 0) null else offset - params.loadSize
    }

    private fun getErrorLoadResult(throwable: Throwable?): LoadResult.Error<Int, Content> {
        return LoadResult.Error(throwable ?: Exception("Somethings went wrong"))
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return state.closestPageToPosition(anchorPosition)?.let { anchorPage ->
            anchorPage.prevKey?.plus(state.config.initialLoadSize)
                ?: anchorPage.nextKey?.minus(state.config.initialLoadSize)
        }
    }
}