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
import com.skaskasian.pdpsandbox.presentation.screens.animations.model.CircleDefaultsModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AnimationsFragment : Fragment(), AnimationDelegate by AnimationDelegateImpl() {

    private var binding: FragmentAnimationsBinding? = null

    private val viewModel: AnimationsViewModel by lazy {
        ViewModelProvider(this)[AnimationsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimationsBinding.inflate(inflater, container, false).run {
            binding = this
            circleviewCircle.doOnLayout { circle ->
                bindView(
                    circle,
                    CircleDefaultsModel(
                        positionXY = circle.x to circle.y,
                        scaleXY = circle.scaleX to circle.scaleY,
                        alpha = 1f
                    )
                )
            }
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.updateViewState.collectLatest { animData ->
                    applyAnim(animData.first, animData.second)
                }
            }
        }
        applyChips()
    }

    private fun applyChips() {
        binding?.apply {
            chipCircleDefault.onClick {}
            chipAnim1.onClick { viewModel.startAnim1() }
            chipAnim2.onClick { viewModel.startAnim2() }
            chipAnim3.onClick { viewModel.startAnim3() }
        }
    }

    private fun Chip.onClick(action: (View) -> Unit) {
        setOnClickListener {
            viewModel.stopAnimations()
            setDefaultAnimViewState()
            action.invoke(it)
        }
    }

    override fun onDestroy() {
        unBindView()
        super.onDestroy()
        binding = null
    }
}