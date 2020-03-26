package com.danqiu.myapplication.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import com.chad.library.adapter.base.animation.BaseAnimation;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * Created by lzj on 2020/3/26
 * Describe ：自定义item动画
 */
public class CustomAnimation2 implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
         Animator translationX = ObjectAnimator.ofFloat(view, "translationX",
                 -view.getRootView().getWidth(), 0f);

        translationX.setDuration(800);
        translationX.setInterpolator(new MyInterpolator2());

        return new Animator[]{translationX};
    }
    class MyInterpolator2 implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            float factor = 0.7f;
            return (float) (pow(2.0, -10.0 * input) * sin((input - factor / 4) * (2 * PI) / factor) + 1);
        }
    }
}
