package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.BounceInterpolator
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyExtendedViewState
import com.skaskasian.pdpsandbox.presentation.screens.animations.model.CircleDefaultsModel

interface AnimationDelegate {

    fun bindView(view: View, defaults: CircleDefaultsModel)
    fun unBindView()
    fun applyAnim(type: AnimType, data: Any)
    fun setDefaultAnimViewState()
}

@Suppress("UNCHECKED_CAST") class AnimationDelegateImpl : AnimationDelegate {

    private var _view: View? = null
    private var viewDefaults: CircleDefaultsModel? = null

    private val view: View
        get() {
            return requireNotNull(_view)
        }

    override fun bindView(view: View, defaults: CircleDefaultsModel) {
        this._view = view
        viewDefaults = defaults
    }

    override fun unBindView() {
        _view = null
        viewDefaults = null
    }

    override fun applyAnim(type: AnimType, data: Any) {
        when (type) {
            AnimType.Anim1 -> applyAnim1Value(data as Float)
            AnimType.Anim2 -> applyAnim2Value(data as Float)
            AnimType.Anim3 -> applyAnim3Value(data as MyExtendedViewState)
            AnimType.Anim4 -> applyAnim4Value(data as ObjectAnimator)
            AnimType.Anim5 -> applyAnim5Value(data as AnimatorSet)
            AnimType.Anim7 -> applyAnim7Value(data as ObjectAnimator)
            AnimType.Anim8 -> applyAnim8Value(data as Wrapper<ViewPropertyAnimator>)
            else -> Unit
        }
    }

    override fun setDefaultAnimViewState() {
        viewDefaults?.let { defaultViewState ->
            view.x = defaultViewState.positionXY.first
            view.y = defaultViewState.positionXY.second
            view.scaleX = defaultViewState.scaleXY.first
            view.scaleY = defaultViewState.scaleXY.second
            view.alpha = defaultViewState.alpha
            view.rotationX = defaultViewState.rotation.first
            view.rotationY = defaultViewState.rotation.second
        }
    }

    private fun applyAnim1Value(value: Float) {
        view.x += value
    }

    private fun applyAnim2Value(value: Float) {
        view.scaleX += value
        view.scaleY += value
    }

    private fun applyAnim3Value(myExtendedViewState: MyExtendedViewState) {
        view.alpha = myExtendedViewState.alpha
        view.x += myExtendedViewState.positionX
        view.scaleX += myExtendedViewState.scaleXY.first
        view.scaleY += myExtendedViewState.scaleXY.second
    }

    private fun applyAnim4Value(objectAnimator: ObjectAnimator) {
        objectAnimator.duration = 1000
        objectAnimator.interpolator = BounceInterpolator()
        objectAnimator.start()
    }

    private fun applyAnim5Value(animationSet: AnimatorSet) {
        animationSet.start()
    }

    private fun applyAnim7Value(objectAnimator: ObjectAnimator) {
        objectAnimator.duration = 5000
        objectAnimator.start()
    }

    private fun applyAnim8Value(viewPropertyAnimator: Wrapper<ViewPropertyAnimator>) {
        viewDefaults?.let { def ->
            viewPropertyAnimator.value
                .y(def.positionXY.second + (-200..200).random())
                .x(def.positionXY.second + (-200..200).random())
                .rotationX(360f)
                .setDuration(2000)
                .start()
        }
    }
}