package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import androidx.recyclerview.widget.DiffUtil
import com.skaskasian.pdpsandbox.data.model.Content

class AdapterDiffUtil : DiffUtil.ItemCallback<Content>() {

    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem == newItem
    }
}