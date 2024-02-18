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
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyExtendedViewState
import com.skaskasian.pdpsandbox.presentation.screens.animations.model.CircleDefaultsModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AnimationsFragment : Fragment() {

    private var binding: FragmentAnimationsBinding? = null

    private val viewModel: AnimationsViewModel by lazy {
        ViewModelProvider(this)[AnimationsViewModel::class.java]
    }

    private lateinit var circleDefaults: CircleDefaultsModel

    private var animationJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimationsBinding.inflate(inflater, container, false).run {
            binding = this
            circleviewCircle.doOnLayout { circle ->
                circleDefaults = CircleDefaultsModel(
                    positionXY = circle.x to circle.y,
                    scaleXY = circle.scaleX to circle.scaleY,
                    alpha = 1f
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
                    when (animData.first) {
                        AnimType.Anim1 -> applyAnim1Value(animData.second as Float)
                        AnimType.Anim2 -> applyAnim2Value(animData.second as Float)
                        AnimType.Anim3 -> applyAnim3Value(animData.second as MyExtendedViewState)
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

    private fun applyAnim2Value(value: Float) {
        binding?.let {
            it.circleviewCircle.scaleX += value
            it.circleviewCircle.scaleY += value
        }
    }

    private fun applyAnim3Value(myExtendedViewState: MyExtendedViewState) {
        binding?.let {
            it.circleviewCircle.alpha = myExtendedViewState.alpha
            it.circleviewCircle.x += myExtendedViewState.positionX
            it.circleviewCircle.scaleX += myExtendedViewState.scaleXY.first
            it.circleviewCircle.scaleY += myExtendedViewState.scaleXY.second
        }
    }

    private fun setDefaultCircleState() {
        animationJob?.cancel()
        binding?.circleviewCircle?.apply {
            x = circleDefaults.positionXY.first
            y = circleDefaults.positionXY.second
            scaleX = circleDefaults.scaleXY.first
            scaleY = circleDefaults.scaleXY.second
            alpha = circleDefaults.alpha
        }
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
            setDefaultCircleState()
            action.invoke(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}