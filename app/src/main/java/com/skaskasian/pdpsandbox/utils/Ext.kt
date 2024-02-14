package com.skaskasian.pdpsandbox.utils

import androidx.paging.LoadState

fun LoadState.errorOrNull(): LoadState.Error? {
    return this as? LoadState.Error
}