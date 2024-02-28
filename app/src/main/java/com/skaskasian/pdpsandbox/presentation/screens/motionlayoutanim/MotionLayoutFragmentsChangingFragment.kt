package com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.databinding.FragmentMotionFrChangingBinding
import com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim.utils.SimpleMotionFragment

class MotionLayoutFragmentsChangingFragment : Fragment() {

    private var binding: FragmentMotionFrChangingBinding? = null

    private var motionTransactionAdapter: TransitionAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        motionTransactionAdapter = object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                applyNewFragment(currentId)
            }
        }
        return FragmentMotionFrChangingBinding.inflate(layoutInflater)
            .apply { binding = this }
            .root
    }

    private fun applyNewFragment(currentId: Int) {
        if (currentId == R.id.state_gone_left || currentId == R.id.state_gone_right) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_motion_fragment, SimpleMotionFragment::class.java, null)
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container_motion_fragment, SimpleMotionFragment::class.java, null)
            .commit()
        binding?.motionlayoutRoot?.setTransitionListener(motionTransactionAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.motionlayoutRoot?.removeTransitionListener(motionTransactionAdapter)
        motionTransactionAdapter = null
        binding = null
    }
}