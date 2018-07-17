package com.zaozao.hu.tools;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by 胡章孝
 * Date:2018/6/8
 * Describle:动画制造工厂
 */
public class AnimationFactory {

    /**
     * 工厂实例对象
     */
    private static AnimationFactory mAnimInstance;
    /**
     * 动画默认执行时间
     */
    private long mDefaultDuration = 1000;
    /**
     * 动画执行的监听对象
     */
    private Animator.AnimatorListener mAnimatorListener;

    private AnimationFactory() {

    }

    public synchronized static AnimationFactory getInstance() {
        if (mAnimInstance == null) {
            mAnimInstance = new AnimationFactory();
        }
        return mAnimInstance;
    }

    public void startAnim(Animator animator) {
        if (animator != null) {
            animator.setDuration(mDefaultDuration);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.addListener(mAnimatorListener);
            animator.start();
        }
    }

    public void startAnim(Animator animator, long duration) {
        this.mDefaultDuration = duration;
        startAnim(animator);
    }

    public void startAnim(Animator animator, long duration, Animator.AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
        startAnim(animator, duration);
    }

    /**
     * 水波纹扩散效果/揭露效果
     *
     * @param view        动画作用的View
     * @param centerX     扩散的中心点的X坐标
     * @param centerY     扩散的中心点的Y坐标
     * @param startRadius 开始扩散的初始半径
     * @param endRadius   扩散结束半径
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
        return ViewAnimationUtils.createCircularReveal(view, centerX,
                centerY, startRadius, endRadius);
    }

    /**
     * 位移动画
     *
     * @param toXType    X轴位移的参考标准 若是以自身为标准，则最终的位移距离就是toXDelta的值乘以自身的宽度，若以父控件为标准则乘以父控件宽度
     * @param toYType    Y轴位移的参考标准
     * @param fromXDelta X起始位置
     * @param toXDelta   X终止位置
     * @param fromYDelta Y起始位置
     * @param toYDelta   Y终止位置
     * @param duration   动画执行的时间
     * @param fillAfter  动画结束时是否保持结束时的状态
     */
    public Animation getTranslate(int fromXType, float fromXDelta,
                                  int toXType, float toXDelta,
                                  int fromYType, float fromYDelta,
                                  int toYType, float toYDelta,
                                  long duration, boolean fillAfter) {
        TranslateAnimation translateAnimation = new TranslateAnimation
                (fromXType, fromXDelta, toXType, toXDelta, fromYType, fromYDelta, toYType, toYDelta);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(fillAfter);
        return translateAnimation;
    }

    /**
     * 位移动画
     *
     * @param fromXDelta x轴起始位置
     * @param toXDelta   x轴最终位置
     * @param fromYDelta y轴起始位置
     * @param toYDelta   y轴最终位置
     * @param duration   动画执行时间
     * @return
     */
    public Animation getTranslate(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(false);
        return translateAnimation;
    }

    /**
     * 旋转动画
     *
     * @param fromDegrees 起始角度
     * @param toDegrees   最终角度
     * @return
     */
    public Animation getRotate(float fromDegrees, float toDegrees) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(false);
        return rotateAnimation;
    }

    /**
     * 旋转动画
     *
     * @param fromDegrees 开始角度
     * @param toDegrees   结束角度
     * @param pivotXType  旋转中心点x坐标的参考的型（以自身为参考类型或者以父控件为参考类型）
     * @param pivotXValue 相对于自身或者父控件的倍数值（0~1）
     * @param pivotYType  旋转中心点y坐标的参考类型
     * @param pivotYValue 同x
     * @return
     */
    public Animation getRotate(float fromDegrees, float toDegrees,
                               int pivotXType, float pivotXValue,
                               int pivotYType, float pivotYValue) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees,
                pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(mDefaultDuration);
        rotateAnimation.setFillAfter(false);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        return rotateAnimation;
    }

    /**
     * 缩放动画
     *
     * @param fromX       x轴上相对与自身的缩放比例
     * @param toX
     * @param fromY       y轴上相对于自身的缩放比例
     * @param toY
     * @param pivotXType  缩放中心点x轴的参考类型
     * @param pivotXValue
     * @param pivotYType  缩放中心点y轴的参考类型
     * @param pivotYValue
     * @return
     */
    public Animation getScaleAnim(float fromX, float toX, float fromY,
                                  float toY, int pivotXType, float pivotXValue,
                                  int pivotYType, float pivotYValue) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY,
                pivotXType, pivotXValue, pivotYType, pivotYValue);
        scaleAnimation.setDuration(mDefaultDuration);
        scaleAnimation.setFillAfter(false);
        scaleAnimation.setRepeatCount(-1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        return scaleAnimation;
    }

    /**
     * 透明度改变动画
     *
     * @param fromAlpha 初始透明值
     * @param toAlpha   最终透明值
     * @return
     */
    public Animation getAlpha(float fromAlpha, float toAlpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(mDefaultDuration);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    /**
     * 动画集合 此处只是示例
     *
     * @return
     */
    public AnimationSet getAnimSet() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = (TranslateAnimation) getTranslate
                (0f, 100f, 0f, 100f, mDefaultDuration);
        RotateAnimation rotateAnimation = (RotateAnimation) getRotate(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scaleAnimation = (ScaleAnimation) getScaleAnim(0.4f, 1f,
                0.4f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(5000);
        return animationSet;
    }

    /**
     * 属性动画，平移
     *
     * @param view
     * @param duration
     * @return
     */
    public void getTranslate(View view, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, 200, 0);
        animator.setDuration(duration);
        animator.setRepeatCount(-1);
        animator.start();
    }

    /**
     * 同时执行多个属性动画
     *
     * @param view
     * @param duration
     */
    public void getMultiAnim(View view, long duration) {
        PropertyValuesHolder px = PropertyValuesHolder.ofFloat("scaleX", 1, 0, 1);
        PropertyValuesHolder py = PropertyValuesHolder.ofFloat("scaleY", 1, 0, 1);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, px, py);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0.0f, 360.0f, -360.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        // set.playTogether(animator, scale);//同时执行
        //set.play(animator).after(scale);//顺序执行
        set.playSequentially(rotate, animator);//顺序执行
        set.start();
    }

    public void getFadingAnim(TextView view, long duration, Animator.AnimatorListener listener) {
        PropertyValuesHolder pa = PropertyValuesHolder.ofFloat("alpha", 0f, 1f, 1f, 1f, 0.8f, 0.8f, 0.7f, 0f);
        PropertyValuesHolder px = PropertyValuesHolder.ofFloat("scaleX", 0.98f, 1f);
        PropertyValuesHolder py = PropertyValuesHolder.ofFloat("scaleY", 0.98f, 1f);
        ObjectAnimator scaleAnim = ObjectAnimator.ofPropertyValuesHolder(view, pa, px, py);
        scaleAnim.setRepeatCount(1);
        scaleAnim.addListener(listener);
        scaleAnim.setDuration(duration);
        scaleAnim.start();
        //   enterAnim.addListener(listener);
        // enterAnim.start();
        AnimatorSet set = new AnimatorSet();

        // set.play(animator).after(scale);//顺序执行
    }
}
