package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.FragmentAnimationsBinding

class AnimationsFragment : Fragment() {

    private var binding: FragmentAnimationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimationsBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyChips()


    }

    private fun applyChips() {
        binding?.apply {
            chipAnim1.setOnClickListener {
                ValueAnimator.ofFloat(5f).apply {
                    addUpdateListener { animator ->
                        binding?.circleviewCircle?.offsetLeftAndRight((animator.animatedValue as Float).toInt())
                    }

                    duration = 2000
                    start()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}