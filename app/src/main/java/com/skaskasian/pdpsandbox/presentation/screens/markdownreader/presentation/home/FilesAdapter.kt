package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.presentation.home

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currentList = mutableListOf<String>()

    fun submitList(list: List<String>) {
        currentList.addAll(list)
        notifyDataSetChanged()
    }

    fun submitFile(file: String) {
        currentList.add(file)
        notifyItemInserted(currentList.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = TextView(parent.context)
        return FilesViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = currentList.getOrNull(position) ?: return
        (holder as FilesViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class FilesViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {

        fun bind(filePath: String) {
            textView.text = filePath
        }
    }
}