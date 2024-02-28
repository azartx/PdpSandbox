package com.skaskasian.pdpsandbox.presentation.screens.lottieanima

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.FragmentLottieAnimsBinding

class LottieAnimFragment : Fragment() {

    private var binding: FragmentLottieAnimsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentLottieAnimsBinding.inflate(layoutInflater).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.chipSnail?.setOnClickListener {
            binding?.lottieanimviewAnimview?.playAnimation()
        }
    }
}