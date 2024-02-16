package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.skaskasian.pdpsandbox.databinding.FragmentAnimationsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnimationsFragment : Fragment() {

    private var binding: FragmentAnimationsBinding? = null

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
                Log.e("ffff", defaultPosition.toString())
            }


            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyChips()


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
            chipAnim1.onClick {
                animationJob = viewLifecycleOwner.lifecycleScope.launch() {
                    ValueAnimator.ofFloat(5f).apply {
                        addUpdateListener { animator ->
                            binding?.circleviewCircle!!.x +=animatedValue as Float
                        }

                        duration = 2000
                        start()
                    }
                }
            }
        }
    }

    private fun Chip.onClick(action: (View) -> Unit) {
        setOnClickListener {
            applyDefaultCircleState()
            action.invoke(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}