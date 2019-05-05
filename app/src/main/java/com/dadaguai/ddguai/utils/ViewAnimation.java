package com.dadaguai.ddguai.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Created by GongSS
 * Date: 2018/10/30 10:30
 * _Umbrella
 */
public class ViewAnimation {

    /**
     * 图标点击缩放效果
     *
     * @param view
     */
    public static void setAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.3f, 1, 1.3f, Animation.RELATIVE_TO_SELF,
                1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(500);
        animationSet.addAnimation(scaleAnimation);
        view.startAnimation(animationSet);
    }

    /**
     * 给图标设置闪烁效果
     *
     * @param view
     * @param time
     */
    public static void setFlickerAnimation(View view, int time) {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(time);//闪烁时间间隔
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        view.setAnimation(animation);
    }
}
