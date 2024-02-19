package com.skaskasian.pdpsandbox.presentation.screens.drawableanimations

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skaskasian.pdpsandbox.R
import com.skaskasian.pdpsandbox.databinding.FragmentImageAnimBinding

class ImageAnimFragment : Fragment() {

    private var binding: FragmentImageAnimBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentImageAnimBinding.inflate(layoutInflater).apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startDefaultAnimation()
    }

    private fun startDefaultAnimation() {
        binding?.imageviewAndroid?.apply {
            setBackgroundResource(R.drawable.item_anim_android_rotation)
            (background as AnimatedVectorDrawable).start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}