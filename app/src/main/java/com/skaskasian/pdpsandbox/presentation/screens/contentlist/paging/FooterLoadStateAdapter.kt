package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.skaskasian.pdpsandbox.R

class FooterLoadStateAdapter(
    private val onRefreshPageClicked: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_content_reload,
                parent,
                false
            ),
            onRefreshPageClicked
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}