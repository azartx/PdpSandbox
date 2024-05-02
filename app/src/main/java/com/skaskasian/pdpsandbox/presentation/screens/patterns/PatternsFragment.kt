package com.skaskasian.pdpsandbox.presentation.screens.patterns

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.skaskasian.pdpsandbox.databinding.FragmentPatternsBinding
import com.skaskasian.pdpsandbox.presentation.screens.patterns.state.PatternsScreenState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Декоратор в виде делегата для анимирования скелетона текста.
class PatternsFragment : Fragment(), StringSkeletonDelegate by StringSkeletonDelegateImpl() {

    private var binding: FragmentPatternsBinding? = null
    private val viewModel by lazy { ViewModelProvider(this)[PatternsViewModel::class.java] }

    private lateinit var glide: RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentPatternsBinding.inflate(layoutInflater)
            .apply {
                binding = this
                glide = Glide.with(this@PatternsFragment)
                bindViews(listOf(textviewTitle, textviewContent))
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.screenState.collectLatest(::onScreenStateReceived)
            }
        }

        binding?.fabUpdatePage?.setOnClickListener { viewModel.onRefreshLandingClicked() }
        binding?.fabLike?.setOnClickListener { viewModel.onLikeButtonClicked() }
    }

    private fun onScreenStateReceived(state: PatternsScreenState) {
        when (state) {
            is PatternsScreenState.Loading -> showLoadingState()
            is PatternsScreenState.Content -> showScreenContent(state)
            is PatternsScreenState.Error -> showErrorState(state.message)
        }
    }

    private fun showLoadingState() {
        applyTextSkeletons()
    }

    private fun showScreenContent(content: PatternsScreenState.Content) {
        dismissTextSkeletons()

        binding?.apply {
            textviewTitle.text = content.screenModel.title
            textviewContent.text = content.screenModel.description
            if (content.isLiked) {
                fabLike.drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY )
            } else {
                fabLike.drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY )
            }

            glide.load(content.screenModel.imageUrl).into(imageviewHeader)
        }
    }

    private fun showErrorState(message: String) {
        dismissTextSkeletons()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindViews()
        binding = null
    }
}