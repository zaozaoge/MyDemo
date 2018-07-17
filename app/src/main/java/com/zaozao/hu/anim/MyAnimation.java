package com.zaozao.hu.anim;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaozao.hu.R;

/**
 * Created by 胡章孝
 * Date:2018/6/11
 * Describle:自定义动画
 */
public class MyAnimation extends Animation {

    private Camera mCamera;
    private float mWidth;
    private float mHeight;
    private float mRoateX = 270;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        setDuration(1000);
        setFillAfter(false);
        setRepeatCount(-1);
        mWidth = width / 2;
        mHeight = height / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        mCamera.save();
        mCamera.rotateX(mRoateX * interpolatedTime);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        //设置矩阵作用前的偏移量来改变旋转中心
        matrix.preTranslate(mWidth, mHeight);
        matrix.postTranslate(mHeight, -mHeight);
    }

    private void onItemClick(TextView textView, ImageView imageView, Activity activity) {
        ActivityOptionsCompat activityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create((View) textView, "12"), Pair.create((View) imageView, "12"));
    }
}
