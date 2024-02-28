package com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.FragmentMotionLayoutAnimBinding

class MotionLayoutAnimationFragment : Fragment() {

    private var binding: FragmentMotionLayoutAnimBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentMotionLayoutAnimBinding.inflate(layoutInflater)
            .apply { binding = this }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}