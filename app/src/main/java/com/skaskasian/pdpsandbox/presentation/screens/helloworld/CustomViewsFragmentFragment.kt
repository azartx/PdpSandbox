package com.skaskasian.pdpsandbox.presentation.screens.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.databinding.FragmentCustomViewsBinding

class CustomViewsFragmentFragment : Fragment() {

    private var binding: FragmentCustomViewsBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCustomViewsBinding.inflate(layoutInflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.buttonStartAnim?.setOnClickListener {
            binding?.viewHuman
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}