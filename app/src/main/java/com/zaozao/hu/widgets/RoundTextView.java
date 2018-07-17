package com.zaozao.hu.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.zaozao.hu.R;

/**
 * Created by 胡章孝
 * Date:2018/5/31
 * Describle:
 */
public class RoundTextView extends AppCompatTextView {

    private String mText;//文本
    private TextPaint mPaint;//文本画笔
    private int mTextSize;//文本字体大小
    private int mBgStrokeColor;//文本背景边框色
    private int mBgStrokeWidth;//文本背景边框宽度
    private int mBgSolidColor;//文本背景填充色
    private int mBgRadius;//文本背景圆角弧度
    private String TAG = RoundTextView.class.getSimpleName();
    private Rect drawRect = new Rect();

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        mText = typedArray.getString(R.styleable.RoundTextView_android_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.RoundTextView_android_textSize, 24);
        mBgStrokeColor = typedArray.getColor(R.styleable.RoundTextView_bgStrokeColor, Color.TRANSPARENT);
        mBgSolidColor = typedArray.getColor(R.styleable.RoundTextView_bgSolidColor, Color.TRANSPARENT);
        mBgRadius = typedArray.getDimensionPixelSize(R.styleable.RoundTextView_bgRadius, 15);
        mBgStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.RoundTextView_bgStrokeWidth, 0);
        typedArray.recycle();
        mPaint = new TextPaint();
        //mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int resultW = width;//最终设置的宽
        int resultH = height;//最终设置的高
        int contentW;//文字内容的宽
        int contentH;//文字内容的高
        if (widthMode == MeasureSpec.AT_MOST) {
            if (!TextUtils.isEmpty(mText)) {
                contentW = (int) mPaint.measureText(mText);
                contentW += getPaddingLeft() + getPaddingRight();
                resultW = contentW < width ? contentW : width;
            }
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            if (!TextUtils.isEmpty(mText)) {
                contentH = mTextSize;
                contentH += getPaddingBottom() + getPaddingTop();
                resultH = contentH < height ? contentH : height;
            }
        }
        setMeasuredDimension(resultW, resultH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(mBgRadius);
        drawable.setStroke(mBgStrokeWidth, mBgStrokeColor);
        drawable.setColor(mBgSolidColor);
        setBackground(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(drawRect);
        int cx = getPaddingLeft() + (getWidth() - getPaddingRight() - getPaddingLeft()) / 2;
        int cy = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        cy += fontMetrics.descent;
        float baseline = (drawRect.top + drawRect.bottom - fontMetrics.bottom - fontMetrics.top) / 2;
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        canvas.drawText(mText, cx, baseline, mPaint);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawLine(0, cy, getWidth(), cy, mPaint);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0, baseline, getWidth(), baseline, mPaint);
    }
}
