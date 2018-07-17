package com.zaozao.hu.widgets;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zaozao.hu.R;

/**
 * Created by 胡章孝
 * Date:2018/6/14
 * Describle:
 */
public class CameraTestView extends View {

    private Camera mCamera;
    private Matrix mMatrix;
    private Paint mPaint;


    public CameraTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mMatrix = new Matrix();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(60);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        //矩阵作用前先将图片平移到逆向图片中心点Y
        mMatrix.preTranslate(0, -getHeight() / 2);
        //矩阵作用后再将图片平移到图片中心点的位置
        mMatrix.postTranslate(0, getHeight() / 2);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.aa, options);
        options.inSampleSize = calculateInSampleSize(options, getWidth() / 2, getHeight() / 2);
        options.inJustDecodeBounds = false;
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.aa, options), mMatrix, mPaint);
    }

    /**
     * 获取图片缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            if (halfWidth / sampleSize > reqWidth && halfHeight / sampleSize > reqHeight) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }
}
