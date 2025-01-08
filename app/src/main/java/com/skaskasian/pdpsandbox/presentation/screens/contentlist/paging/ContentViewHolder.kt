package com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skaskasian.pdpsandbox.data.model.Content
import com.skaskasian.pdpsandbox.databinding.ItemContentBinding

class ContentViewHolder(
    private val binding: ItemContentBinding
) : ViewHolder(binding.root) {

    fun bind(
        contentItem: Content,
        onClick: (Content) -> Unit
    ) {
        binding.textviewTitle.text = contentItem.title
        binding.textviewDescription.text = contentItem.description

        binding.root.setOnClickListener { onClick.invoke(contentItem) }
    }
}