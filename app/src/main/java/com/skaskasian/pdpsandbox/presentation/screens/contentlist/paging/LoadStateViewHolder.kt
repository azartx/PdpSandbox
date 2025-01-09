package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

class LoadStateViewHolder(
    private val view: View,
    private val onAppend: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener { onAppend.invoke() }
    }

    fun bind(loadState: LoadState) {
        view.isVisible = loadState is LoadState.Error
    }
}