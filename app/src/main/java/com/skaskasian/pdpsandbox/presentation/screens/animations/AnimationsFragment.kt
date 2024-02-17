package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.skaskasian.pdpsandbox.databinding.FragmentAnimationsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AnimationsFragment : Fragment() {

    private var binding: FragmentAnimationsBinding? = null

    private val viewModel: AnimationsViewModel by lazy {
        ViewModelProvider(this)[AnimationsViewModel::class.java]
    }

    private lateinit var defaultPosition: Pair<Float, Float>

    private var animationJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimationsBinding.inflate(inflater, container, false).run {
            binding = this
            circleviewCircle.doOnLayout {
                defaultPosition = it.x to it.y
            }
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.updateViewState.collectLatest { animData ->
                    when (animData.first) {
                        AnimType.Anim1 -> applyAnim1Value(animData.second as Float)
                        else -> Unit
                    }
                }
            }
        }

        applyChips()
    }

    private fun applyAnim1Value(value: Float) {
        binding?.let { it.circleviewCircle.x += value }
    }

    private fun applyDefaultCircleState() {
        animationJob?.cancel()
        binding?.circleviewCircle?.apply {
            x = defaultPosition.first
            y = defaultPosition.second
            this.requestLayout()
            this.forceLayout()
            this.invalidate()
        }
    }

    private fun applyChips() {
        binding?.apply {
            chipCircleDefault.onClick {}
            chipAnim1.onClick { viewModel.startAnim1() }
        }
    }

    private fun Chip.onClick(action: (View) -> Unit) {
        setOnClickListener {
            viewModel.stopAnimations()
            applyDefaultCircleState()
            action.invoke(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}