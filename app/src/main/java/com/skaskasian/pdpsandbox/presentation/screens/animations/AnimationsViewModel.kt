package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModel
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyExtendedViewState
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyTypeEvaluator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimationsViewModel : ViewModel(), ValueAnimator.AnimatorUpdateListener {

    private val _updateViewState = MutableStateFlow<Pair<AnimType, Any>>(AnimType.None to Unit)
    val updateViewState = _updateViewState.asStateFlow()

    private val disposables = mutableListOf<Disposable>()
    private val currentAnimType = MutableStateFlow(AnimType.None)

    fun startAnim1() {
        currentAnimType.value = AnimType.Anim1

        val animator = ValueAnimator.ofFloat(5f)
        disposables += object : Disposable {
            override fun dispose() {
                animator.removeAllUpdateListeners()
                animator.cancel()
            }
        }
        animator.duration = 2000
        animator.addUpdateListener(this)
        animator.start()
    }

    fun startAnim2() {
        currentAnimType.value = AnimType.Anim2

        val animator = ValueAnimator.ofFloat(0.2f)
        disposables += object : Disposable {
            override fun dispose() {
                animator.removeAllUpdateListeners()
                animator.cancel()
            }
        }
        animator.duration = 700
        animator.addUpdateListener(this)
        animator.start()
    }

    fun startAnim3() {
        currentAnimType.value = AnimType.Anim3

        val animator = ValueAnimator.ofObject(
            MyTypeEvaluator(),
            MyExtendedViewState(1f, 0f to 0f, 0f),
            MyExtendedViewState(0.3f, 0.05f to 0.05f, 10f)
        )
        disposables += object : Disposable {
            override fun dispose() {
                animator.removeAllUpdateListeners()
                animator.cancel()
            }
        }
        animator.duration = 2000
        animator.addUpdateListener(this)
        animator.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        _updateViewState.tryEmit(currentAnimType.value to animation.animatedValue)
    }

    fun stopAnimations() {
        disposables
            .onEach { it.dispose() }
            .clear()
    }

    override fun onCleared() {
        stopAnimations()
        super.onCleared()
    }
}

private interface Disposable {

    fun dispose()
}

enum class AnimType {

    None, Anim1, Anim2, Anim3
}