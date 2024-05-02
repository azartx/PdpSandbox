package com.skaskasian.pdpsandbox.presentation.screens.patterns.observer

fun interface Observer<T> {

    fun updateData(data: T)
}