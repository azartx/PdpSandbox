package com.skaskasian.pdpsandbox.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.skaskasian.pdpsandbox.data.database.entity.ContentEntity

@Dao
interface ContentDao {

    @Query("SELECT * FROM content LIMIT :limit OFFSET :offset")
    suspend fun getContent(limit: Int, offset: Int): List<ContentEntity>
}