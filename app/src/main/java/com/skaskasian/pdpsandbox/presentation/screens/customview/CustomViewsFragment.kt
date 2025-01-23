package com.skaskasian.pdpsandbox.presentation.screens.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.FragmentCustomViewsBinding

class CustomViewsFragment : Fragment() {

    private var _binding: FragmentCustomViewsBinding? = null
    private val binding: FragmentCustomViewsBinding; get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCustomViewsBinding.inflate(layoutInflater, container, false).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonStartAnim.setOnClickListener {
            onHandWaveStarted()
            binding.viewHuman.sayHello(::onHandWaveEnded)
        }
    }

    private fun onHandWaveStarted() {
        binding.buttonStartAnim.isEnabled = false
    }

    private fun onHandWaveEnded() {
        binding.buttonStartAnim.isEnabled = true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}