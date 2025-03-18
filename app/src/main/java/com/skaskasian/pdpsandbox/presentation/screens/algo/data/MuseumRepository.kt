package com.skaskasian.pdpsandbox.presentation.screens.algo.data

import com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto.MuseumObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MuseumRepository(private val service: MetropolitanMuseumService) {

    suspend fun getMuseumObjects(fromId: Int, toId: Int): List<MuseumObject> {
        return withContext(Dispatchers.IO) {
            buildList {
                for (id in fromId..toId) {
                    add(service.getObject(id))
                }
            }
        }
    }
}