package com.skaskasian.pdpsandbox.data.mapper

import com.skaskasian.pdpsandbox.data.database.entity.ContentEntity
import com.skaskasian.pdpsandbox.data.model.Content

class ContentMapper {

    fun map(contentEntity: ContentEntity): Content {
        return Content(
            title = contentEntity.title,
            description = contentEntity.description
        )
    }
}
