package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.skaskasian.pdpsandbox.data.model.Content
import com.skaskasian.pdpsandbox.databinding.ItemContentBinding

class ContentAdapter : PagingDataAdapter<Content, ContentViewHolder>(AdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            binding = ItemContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }
}