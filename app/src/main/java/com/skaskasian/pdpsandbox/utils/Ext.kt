package com.skaskasian.pdpsandbox.utils

import androidx.paging.LoadState

fun LoadState.errorOrNull(): LoadState.Error? {
    return this as? LoadState.Error
}

fun String.cloneTimes(times: Int, separator: String = ", "): String {
    var result = this
    (0..times).forEach { _ -> result += "$separator$this" }
    return result
}