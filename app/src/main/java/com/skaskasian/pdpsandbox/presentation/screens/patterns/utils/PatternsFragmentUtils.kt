package com.skaskasian.pdpsandbox.presentation.screens.patterns.utils

import java.util.Calendar
import java.util.Locale

class PatternsFragmentUtils private constructor() {

    companion object {

        private var _instance: PatternsFragmentUtils? = null

        fun getInstance(): PatternsFragmentUtils {
            return _instance ?: PatternsFragmentUtils().apply { _instance = this }
        }

        /* Or
        *
        * val instance: PatternsFragmentUtils by lazy { PatternsFragmentUtils() }
        *
        *
        * Or
        *
        * object PatternsFragmentUtils {}
        *
        *
        * Либо использовать top-level declarations внутри .kt файла.
        *
        * */
    }

    val calendar: Calendar
        get() = Calendar.getInstance(Locale.getDefault())

    fun isUserGuest(): Boolean {
        return false
    }
}