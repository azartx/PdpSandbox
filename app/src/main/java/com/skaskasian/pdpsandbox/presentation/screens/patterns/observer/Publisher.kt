package com.skaskasian.pdpsandbox.presentation.screens.patterns.observer

interface Publisher<T> {

    fun subscribe(observer: Observer<T>)

    fun unsubscribe(observer: Observer<T>)
    fun unsubscribeAll()

    fun notify(data: T)
}

fun <T> createPublisher(): Publisher<T> {
    return object : Publisher<T> {

        private val subscribers: MutableList<Observer<T>> = mutableListOf()

        override fun subscribe(observer: Observer<T>) {
            synchronized(this) {
                if (!subscribers.contains(observer)) subscribers.add(observer)
            }
        }

        override fun unsubscribe(observer: Observer<T>) {
            synchronized(this) {
                subscribers.remove(observer)
            }
        }

        override fun notify(data: T) {
            synchronized(this) {
                subscribers.forEach { it.updateData(data) }
            }
        }

        override fun unsubscribeAll() {
            synchronized(this) {
                subscribers.clear()
            }
        }
    }
}