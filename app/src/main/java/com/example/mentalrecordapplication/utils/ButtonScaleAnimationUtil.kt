package com.example.mentalrecordapplication.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.widget.Button

object ButtonScaleAnimationUtil {
    fun simpleScaleAnimation(button: Button) {
        // 縮小アニメーション
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            button,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0.9f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0.9f)
        ).apply {
            duration = 100
            repeatCount = 0
        }

        // 元に戻るアニメーション
        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            button,
            PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f)
        ).apply {
            duration = 100
            repeatCount = 0
        }

        // アニメーションを連続して実行
        AnimatorSet().apply {
            playSequentially(scaleDown, scaleUp)
            start()
        }
    }
}