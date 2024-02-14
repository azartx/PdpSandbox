package com.skaskasian.pdpsandbox.data.repository

import com.skaskasian.pdpsandbox.data.database.dao.ContentDao
import com.skaskasian.pdpsandbox.data.mapper.ContentMapper
import com.skaskasian.pdpsandbox.data.model.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentRepository(
    private val contentDao: ContentDao,
    private val contentMapper: ContentMapper = ContentMapper()
) {

    suspend fun getContent(limit: Int, offset: Int): List<Content> {
        return withContext(Dispatchers.IO) {
            contentDao.getContent(limit, offset)
                .map(contentMapper::map)
        }
    }
}