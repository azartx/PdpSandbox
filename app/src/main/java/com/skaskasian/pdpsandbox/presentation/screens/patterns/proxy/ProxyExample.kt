package com.skaskasian.pdpsandbox.presentation.screens.patterns.proxy

import java.util.UUID

/*
* Паттерн Заместитель
*
* Позволяет подменять реальные объекты бьъектами-заместителями для обработки дополнительной логики
*
* Пример - дополнительное логгирование запросов в сеть через класс-заместитель
*
* */

interface NetworkService {

    fun getArticles(): List<String>
}

object DiContainer {

    fun getNetworkService(): NetworkService {
        return NetworkServiceProxy()
    }
}

class NetworkServiceImpl : NetworkService {

    override fun getArticles(): List<String> {
        return authenticatedCall {
            List(100) { UUID.randomUUID().toString() }
        }
    }

    private fun <T> authenticatedCall(call: () -> T): T {
        return call.invoke()
    }
}

/*
* Класс имплементит NetworkService и оборачивает реальные вызовы дополнительной логикой
*
* Может помочь если нужно обернуть дополнительный логикой какой-то класс без вмешательства в него,
* если нужно подменить реализацию на иную
*
*  */
class NetworkServiceProxy : NetworkService {

    private val realService by lazy { NetworkServiceImpl() }

    override fun getArticles(): List<String> {
        if (hasCallApiPermission()) {
            logRequest()
            return realService.getArticles()
        }
        throw ApiCallPermissionDenied()
    }

    private fun logRequest() {
        println("Get articles request")
    }

    private fun hasCallApiPermission(): Boolean {
        return true
    }
}

class ApiCallPermissionDenied : Exception()