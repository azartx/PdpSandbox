package com.skaskasian.pdpsandbox.presentation.screens.contentlist.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.skaskasian.pdpsandbox.databinding.FragmentContentDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContentDetailsFragment : Fragment() {

    companion object {

        const val CONTENT_DETAILS_KEY = "CONTENT_DETAILS_KEY"
    }

    private var _binding: FragmentContentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[ContentDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentContentDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        ).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayHomeAsUpEnabled(true)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.screenState.collectLatest { screenContent ->
                        binding.textviewTitle.text = screenContent.title
                        binding.textviewDescription.text = screenContent.description
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayHomeAsUpEnabled(false)
        _binding = null
        super.onDestroyView()
    }
}