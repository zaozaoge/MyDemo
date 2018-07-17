package com.zaozao.hu.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by 胡章孝
 * Date:2018/6/15
 * Describle:
 */
public class Custom3DView extends ViewGroup {

    private Camera mCamera;
    private Matrix mMatrix;
    private Scroller mScroller;
    private int mStartScreen = 1;//开始时Item所在位置
    private int mCurScreen = 1;//当前item的位置
    private float angle = 90;//旋转的角度
    private VelocityTracker mTracker;
    private float mDownY = 0;
    private int mHeight = 0;//控件的高
    private float resistance = 1.6f;//滑动阻力
    private static final int standerSpeed = 2000;
    //三种状态
    private static final int STATE_PRE = 0;
    private static final int STATE_NEXT = 1;
    private static final int STATE_NORMAL = 2;
    private int STATE = -1;
    private String TAG = Custom3DView.class.getSimpleName();

    public Custom3DView(Context context) {
        this(context, null);
    }

    public Custom3DView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Custom3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCamera = new Camera();
        mMatrix = new Matrix();
        if (mScroller == null) {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
        }
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

        int resultWidth = widthSize;
        int resultHeight = heightSize;
        int contentWidth = getPaddingLeft() + getPaddingRight();
        int contentHeight = getPaddingBottom() + getPaddingTop();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        MarginLayoutParams layoutParams;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
                continue;
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            contentWidth += layoutParams.leftMargin + layoutParams.rightMargin + child.getMeasuredWidth();
            contentHeight += child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
        }
        if (widthMode == MeasureSpec.AT_MOST)
            resultWidth = contentWidth < widthSize ? contentWidth : widthSize;
        if (heightMode == MeasureSpec.AT_MOST)
            resultHeight = contentHeight < heightSize ? contentHeight : heightSize;
        setMeasuredDimension(resultWidth, resultHeight);
        mHeight = resultHeight;
        scrollTo(0, mStartScreen * mHeight);//将视图中的内容向上平移一个控件的高度
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topStart = getPaddingTop();
        int leftStart = getPaddingLeft();
        int childW, childH;
        MarginLayoutParams layoutParams;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
                continue;
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            childW = child.getMeasuredWidth();
            childH = child.getMeasuredHeight();
            topStart += layoutParams.topMargin;
            child.layout(leftStart + layoutParams.leftMargin, topStart, leftStart + childW, topStart + childH);
            topStart += childH;

        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            drawScreen(getChildAt(i), canvas, i, getDrawingTime());
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTracker == null) {
                    mTracker = VelocityTracker.obtain();
                } else {
                    mTracker.clear();
                }
                mTracker.addMovement(event);
                if (!mScroller.isFinished()) {
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0, getScrollY());
                }
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int disY = (int) (mDownY - y);//手指从按下到滑动的距离
                mDownY = y;
                if (mScroller.isFinished()) {
                    recycleMove(disY);
                }
                break;
            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(1000);
                float velocityY = mTracker.getYVelocity();
                //滑动的速度高于标准的速度或者向上滑动时，上一页页面展现出的高度超过二分之一，则设定状态为STATE_PRE
                if (velocityY > standerSpeed || (getScrollY() + mHeight / 2) / mHeight < mStartScreen) {
                    STATE = STATE_PRE;
                } else if (velocityY < -standerSpeed || (getScrollY() + mHeight / 2) / mHeight > mStartScreen) {
                    //滑动的速度高于标准的速度或者向下滑动时，下一页页面展现出的高度超过二分之一
                    STATE = STATE_NEXT;
                } else {
                    STATE = STATE_NORMAL;
                }
                changeByState();
                if (mTracker != null) {
                    mTracker.recycle();
                    mTracker = null;
                }
                break;
        }
        return true;
    }

    private void drawScreen(View child, Canvas canvas, int screen, long drawingTime) {
        final int height = child.getMeasuredHeight();
        final int scrollHeight = screen * height;
        final int scrollY = getScrollY();//当前视图左上角相对于母视图左上角Y轴的偏移量
        if (scrollHeight > scrollY + height || scrollHeight + height < scrollY) {
            return;
        }
        float currentDegree = getScrollY() * (angle / getMeasuredHeight());
        float faceDegree = currentDegree - screen * angle;
        if (faceDegree > 90 || faceDegree < -90)
            return;
        float centerY = (scrollHeight < scrollY) ? scrollHeight + height : scrollHeight;
        float centerX = getWidth() / 2;
        Camera camera = mCamera;
        Matrix matrix = mMatrix;
        canvas.save();
        camera.save();
        camera.rotateX(faceDegree);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.concat(matrix);
        drawChild(canvas, child, drawingTime);
        canvas.restore();
    }

    private void recycleMove(int delta) {
        delta = delta % mHeight;
        delta = (int) (delta / resistance);
        if (Math.abs(delta) > mHeight / 4) {
            return;
        }
        if (getScrollY() <= 0 && mCurScreen <= 0 && delta <= 0) {
            return;
        }
        if (mCurScreen * mHeight <= getScrollY() && mCurScreen == getChildCount() - 1 && delta >= 0) {
            return;
        }
        scrollBy(0, delta);
        if (getScrollY() < 8 && mCurScreen != 0) {
            setPre();
            scrollBy(0, mHeight);
        } else if (getScrollY() > (getChildCount() - 1) * mHeight - 8) {
            setNext();
            scrollBy(0, -mHeight);
        }
    }

    private void changeByState() {
        switch (STATE) {
            case STATE_NORMAL:
                toNormalPager();
                break;
            case STATE_PRE:
                toPrePager();
                break;
            case STATE_NEXT:
                toNextPager();
                break;
        }
    }

    /**
     * 当前页
     */
    private void toNormalPager() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_NORMAL;
        startY = getScrollY();
        delta = mHeight * mStartScreen - getScrollY();
        duration = Math.abs(delta) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    /**
     * 上一页
     */
    private void toPrePager() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_PRE;
        //增加新的页面
        setPre();
        startY = getScrollY() + mHeight;
        setScrollX(startY);
        delta = -(startY - mStartScreen * mHeight);
        duration = Math.abs(delta) * 2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void setPre() {
        mCurScreen = (mCurScreen - 1 + getChildCount()) / getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
    }

    /**
     * 下一页
     */
    private void toNextPager() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_NEXT;
        setNext();
        startY = getScrollY() - mHeight;
        setScrollY(startY);
        delta = mHeight * mStartScreen - startY;
        duration = Math.abs(delta) * 2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void setNext() {
        int childCount = getChildCount();
        mCurScreen = (mCurScreen + 1) % childCount;
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
