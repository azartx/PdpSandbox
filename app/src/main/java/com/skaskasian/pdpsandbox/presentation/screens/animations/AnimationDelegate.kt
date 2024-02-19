package com.skaskasian.pdpsandbox.presentation.screens.animations

import android.view.View
import com.skaskasian.pdpsandbox.presentation.screens.animations.animdata.MyExtendedViewState
import com.skaskasian.pdpsandbox.presentation.screens.animations.model.CircleDefaultsModel

interface AnimationDelegate {

    fun bindView(view: View, defaults: CircleDefaultsModel)
    fun unBindView()
    fun applyAnim(type: AnimType, data: Any)
    fun setDefaultAnimViewState()
}

class AnimationDelegateImpl : AnimationDelegate {

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
}