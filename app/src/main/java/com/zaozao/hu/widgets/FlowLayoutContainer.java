package com.zaozao.hu.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zaozao.hu.R;

/**
 * Created by 胡章孝
 * Date:2018/6/1
 * Describle:
 */
public class FlowLayoutContainer extends ViewGroup implements View.OnClickListener {

    private OnItemClickListener listener;
    private int defaultHorizontalPadding = 10;
    private int defaultVerticalPadding = 10;

    public FlowLayoutContainer(Context context) {
        this(context, null);
    }

    public FlowLayoutContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayoutContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayoutContainer);
        defaultHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.FlowLayoutContainer_horizontal_padding, defaultHorizontalPadding);
        defaultVerticalPadding = typedArray.getDimensionPixelSize(R.styleable.FlowLayoutContainer_vertical_padding, defaultVerticalPadding);
        typedArray.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultW = widthSize;//最终设置的宽
        int resultH = heightSize;//最终设置的高
        int contentW = getPaddingLeft() + getPaddingRight();
        int contentH = getPaddingBottom() + getPaddingTop();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        MarginLayoutParams layoutParams;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (child.getVisibility() == View.GONE)
                continue;
            contentW += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            contentH += child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
        }
        if (widthMode == MeasureSpec.AT_MOST)
            resultW = contentW < widthSize ? contentW : widthSize;
        if (heightMode == MeasureSpec.AT_MOST)
            resultH = contentH < heightSize ? contentH : heightSize;
        setMeasuredDimension(resultW, resultH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topStart = getPaddingTop();
        int leftStart = getPaddingLeft();
        int childW, childH;
        MarginLayoutParams layoutParams;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(this);
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (child.getVisibility() == View.GONE)
                continue;
            childW = child.getMeasuredWidth();
            childH = child.getMeasuredHeight();
            leftStart += layoutParams.leftMargin;
            topStart += layoutParams.topMargin;
            if (leftStart + childW > getWidth()) {//换行
                leftStart = getPaddingLeft();
                topStart += childH + layoutParams.topMargin + defaultVerticalPadding;
            }
            if (leftStart != getPaddingLeft())
                leftStart += defaultHorizontalPadding;
            child.layout(leftStart, topStart, leftStart + childW, topStart + childH);
            leftStart += childW + layoutParams.rightMargin;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(v);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View child);
    }
}
