package com.skaskasian.pdpsandbox.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skaskasian.pdpsandbox.data.database.dao.ContentDao
import com.skaskasian.pdpsandbox.data.database.entity.ContentEntity

@Database(
    entities = [ContentEntity::class],
    version = 1
)
abstract class ContentDatabase : RoomDatabase() {

    abstract val contentDao: ContentDao

    companion object {

        fun create(context: Context): ContentDatabase {
            return Room.databaseBuilder(
                context,
                ContentDatabase::class.java,
                "content_db"
            )
                .createFromAsset("content_db")
                .build()
        }
    }
}