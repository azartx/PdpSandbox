package com.skaskasian.pdpsandbox.presentation.screens.algo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.skaskasian.pdpsandbox.databinding.FragmentAlgoBinding
import com.skaskasian.pdpsandbox.di.AppDi
import com.skaskasian.pdpsandbox.di.Injectable
import com.skaskasian.pdpsandbox.di.Scope
import com.skaskasian.pdpsandbox.di.get
import com.skaskasian.pdpsandbox.presentation.screens.algo.di.createAlgoScopeDependencies
import com.skaskasian.pdpsandbox.presentation.screens.algo.utils.colors
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.paging.ContentAdapter

class AlgoFragment : Fragment(), Injectable {

    private var _binding: FragmentAlgoBinding? = null
    private val binding get() = _binding!!

    override fun getScope(): Scope {
        return this::class
    }

    private val viewModel: AlgoViewModel by lazy {
        ViewModelProvider(this, AlgoViewModelFactory(get()))[AlgoViewModel::class.java]
    }

    private val contentAdapter: ContentAdapter by lazy { ContentAdapter({/*TODO*/}) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppDi.initScope(getScope(), createAlgoScopeDependencies())
        _binding = FragmentAlgoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateViews()

        binding.serachviewContent.doOnTextChanged { text, start, before, count ->
            viewModel.onNewSearchTextInputted(text)
        }
        binding.buttonRegenerateColors.setOnClickListener {
            updateColors(binding.frameContainer)
        }
    }

    // Получаем последнего по вложенности чайлда через рекурсию, добавляем ему нового чайлда
    private fun generateViews() {
        colors.forEach { color ->
            val viewGroup = (getLastChild(binding.frameContainer, binding.frameContainer.children)
                ?: binding.frameContainer)

            viewGroup.addView(createColoredFrameLayout(viewGroup.context, color))
        }
    }

    // если в последнем вложенном вью есть еще чайлды - делаем рекурсивный вызов
    // иначе возвращаем либо последнего чайлда, либо налл
    private fun getLastChild(last: ViewGroup?, children: Sequence<View>): ViewGroup? {
        val iterator = children.iterator()
        return if (!iterator.hasNext()) {
            last
        } else {
            val child = iterator.next() as ViewGroup
            return getLastChild(child, child.children)
        }
    }

    private fun createColoredFrameLayout(context: Context, color: Int): FrameLayout {
        return FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(10)
            }
            setBackgroundColor(color)
        }
    }

    // обновляем фон для всех вложенных чайлдов рекурсивно
    private fun updateColors(viewGroup: ViewGroup?) {
        if (viewGroup != null) {
            viewGroup.setBackgroundColor(colors.random())
            updateColors(viewGroup.children.firstOrNull() as? ViewGroup)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        AppDi.closeScope(getScope())
    }
}