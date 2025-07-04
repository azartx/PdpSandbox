package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.LayoutMarkdownReaderBinding
import com.skaskasian.pdpsandbox.presentation.screens.markdownreader.utils.UiStateObserver

class MarkdownReaderFragment : Fragment(), UiStateObserver<HomeState, HomeErrorState> {

    private var binding: LayoutMarkdownReaderBinding? = null

    private val adapter by lazy { FilesAdapter() }
    private val viewModel by lazy { MarkdownReaderViewModel() }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.path?.let { adapter.submitFile(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutMarkdownReaderBinding.inflate(layoutInflater).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run { setupViews() }
        viewModel.subscribeOnState(this)
    }

    private fun LayoutMarkdownReaderBinding.setupViews() {
        buttonDownload.setOnClickListener {
            viewModel.onDownloadFileClick(edittextUrl.text.toString(), requireActivity().mainLooper)
        }
        buttonDownloadFromFile.setOnClickListener {
            activityResultLauncher.launch("text/markdown")
        }
        recyclerFiles.adapter = adapter
    }

    override fun onChanged(state: HomeState) {
        adapter.submitList(state.files)
        binding?.apply {
            buttonDownload.isEnabled = !state.isLoading
            buttonDownloadFromFile.isEnabled = !state.isLoading
            progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onError(errorState: HomeErrorState) {
        when(errorState) {
            HomeErrorState.IncorrectUrlError ->
                Toast.makeText(requireContext(), "Incorrect url", Toast.LENGTH_SHORT).show()
            HomeErrorState.DownloadError ->
                Toast.makeText(requireContext(), "Error while downloading the file", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        viewModel.unsubscribe()
        super.onDestroyView()
    }
}