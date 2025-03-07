package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.databinding.FragmentAnimationsBinding
import com.skaskasian.pdpsandbox.presentation.screens.animations.model.CircleDefaultsModel
import com.skaskasian.pdpsandbox.presentation.screens.lottieanima.LottieAnimFragment
import com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim.MotionLayoutAnimationFragment
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
            chipLottie.onClick { openFragment<LottieAnimFragment>() }
            chipMotionLayout.onClick { openFragment<MotionLayoutAnimationFragment>() }
            chipAnim1.onClick { viewModel.startAnim1() }
            chipAnim2.onClick { viewModel.startAnim2() }
            chipAnim3.onClick { viewModel.startAnim3() }
            chipAnim4.onClick { viewModel.startAnim4(circleviewCircle) }
            chipAnim5.onClick { viewModel.startAnim5(circleviewCircle) }
            chipAnim7.onClick { viewModel.startAnim7(circleviewCircle) }
            chipAnim8.onClick { viewModel.startAnim8(circleviewCircle) }
        }
    }

    private inline fun <reified T : Fragment> openFragment() {
        val jClass = T::class.java
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, jClass, null)
            .addToBackStack(jClass.simpleName)
            // Fragment opening transition animation
            // OR .setCustomAnimations() - set enter and exit animation to the fragment using xml
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
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