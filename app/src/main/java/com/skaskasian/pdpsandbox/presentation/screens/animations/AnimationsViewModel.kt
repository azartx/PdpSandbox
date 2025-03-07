package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.BounceInterpolator
import androidx.lifecycle.ViewModel
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyExtendedViewState
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyTypeEvaluator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

@SuppressLint("Recycle")
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

        val startValue = MyExtendedViewState(alpha = 1f, scaleXY = 0f to 0f, positionX = 0f)
        val endValue = MyExtendedViewState(alpha = 0.3f, scaleXY = 0.05f to 0.05f, positionX = 5f)
        val animator = ValueAnimator.ofObject(MyTypeEvaluator(), startValue, endValue)
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

    fun startAnim4(view: View) {
        currentAnimType.value = AnimType.Anim4

        val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, 90f)

        disposables += object : Disposable {
            override fun dispose() {
                animator.cancel()
                animator.target = null
            }
        }

        _updateViewState.tryEmit(AnimType.Anim4 to animator)
    }

    fun startAnim5(view: View) {
        currentAnimType.value = AnimType.Anim5

        val animator1 = ObjectAnimator.ofFloat(view, "translationY", 0f, 90f).apply {
            duration = 1000
            interpolator = BounceInterpolator()
        }
        val animator2 = ObjectAnimator.ofFloat(view, "scaleX", 5f).apply {
            duration = 2000
        }

        val animator = AnimatorSet().apply {
            play(animator1).after(animator2)
        }

        disposables += object : Disposable {
            override fun dispose() {
                animator.cancel()
                animator.removeAllListeners()
                animator1.target = null
            }
        }

        _updateViewState.tryEmit(AnimType.Anim5 to animator)
    }

    fun startAnim7(view: View) {
        currentAnimType.value = AnimType.Anim7

        val keyframe1 = Keyframe.ofFloat(0f, 0f)
        val keyframe2 = Keyframe.ofFloat(.5f, 360f)
        val keyframe3 = Keyframe.ofFloat(1f, 0f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofKeyframe("rotationX", keyframe1, keyframe2, keyframe3)
        )

        disposables += object : Disposable {
            override fun dispose() {
                animator.cancel()
                animator.removeAllListeners()
                animator.target = null
            }
        }

        _updateViewState.tryEmit(AnimType.Anim7 to animator)
    }

    fun startAnim8(view: View) {
        currentAnimType.value = AnimType.Anim8

        val viewPropertyAnimator = view.animate()

        disposables += object : Disposable {

            private var _viewPropertyAnimator: ViewPropertyAnimator? = viewPropertyAnimator

            override fun dispose() {
                viewPropertyAnimator.cancel()
                _viewPropertyAnimator = null
            }
        }

        _updateViewState.tryEmit(AnimType.Anim8 to Wrapper(viewPropertyAnimator))
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
        _updateViewState.value = AnimType.None to Unit
        super.onCleared()
    }
}

private interface Disposable {

    fun dispose()
}

enum class AnimType {

    None, Anim1, Anim2, Anim3, Anim4, Anim5, Anim7, Anim8
}

data class Wrapper<T : Any>(val value: T, val uuid: String = UUID.randomUUID().toString())