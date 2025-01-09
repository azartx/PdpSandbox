package com.skaskasian.pdpsandbox.presentation.screens.contentlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.data.model.Content
import com.skaskasian.pdpsandbox.databinding.FragmentContentListBinding
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.details.ContentDetailsFragment
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging.ContentAdapter
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging.FooterLoadStateAdapter
import com.skaskasian.pdpsandbox.utils.errorOrNull
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContentListFragment : Fragment() {

    private var _binding: FragmentContentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContentListViewModel by lazy {
        ViewModelProvider(this, ContentListViewModelFactory())[ContentListViewModel::class.java]
    }

    private val contentAdapter: ContentAdapter by lazy { ContentAdapter(::onContentItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerContentList.adapter = contentAdapter.withLoadStateFooter(
            FooterLoadStateAdapter(contentAdapter::retry)
        )
        binding.buttonRetry.setOnClickListener { contentAdapter.refresh() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch { viewModel.content.collectLatest(contentAdapter::submitData) }
                launch { contentAdapter.loadStateFlow.collectLatest(::applyState) }
            }
        }
    }

    private fun onContentItemClick(content: Content) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_main,
                ContentDetailsFragment::class.java,
                bundleOf(ContentDetailsFragment.CONTENT_DETAILS_KEY to content)
            )
            .addToBackStack(ContentDetailsFragment::class.java.simpleName)
            .commit()
    }

    private fun applyState(loadStates: CombinedLoadStates) {
        val hasError = loadStates.refresh is LoadState.Error
        binding.recyclerContentList.isVisible = !hasError
        binding.textviewError.isVisible = hasError
        binding.buttonRetry.isVisible = hasError

        if (hasError) {
            val errorMsg = (loadStates.refresh.errorOrNull()
                ?: loadStates.append.errorOrNull())
                ?.error
                ?.message
                ?.takeIf { it.isNotBlank() }
                ?: "Unknown error"
            binding.textviewError.text = errorMsg
        }

        if (loadStates.append.endOfPaginationReached) {
            Toast.makeText(requireContext(), "Content is ended.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}