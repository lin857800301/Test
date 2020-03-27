package com.danqiu.myapplication.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * Created by lzj on 2020/3/26
 * Describe ：自定义item动画
 */
public class CustomAnimation3 implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        Animator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        alpha.setDuration(450);

        Animator translationY =
                ObjectAnimator.ofFloat(view, "translationY", view.getRootView().getHeight(), 0f);
        translationY.setDuration(450);
        translationY.setInterpolator(new DecelerateInterpolator(1.2f));

        return new Animator[]{alpha, translationY};
    }
}
