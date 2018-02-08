package com.zqb.vseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by XXX on 2018/2/6.
 */

public class VSeekBar extends View{

    private Drawable mBackGround;
    private int mWidth;
    private int mHeight;
    private Drawable mProgressBarBg;
    private Drawable mProgressBar;
    private Drawable mThumb;
    private float mXPercent;
    private float mYPercent;
    private int mProgressLeft;
    private int mProgressTop;
    private int mProgressRight;
    private int mProgressBottom;
    private int mThumbLeft;
    private int mThumbRight;
    private int mThumbHeight;
    private int mTextBgLeft;
    private int mTextBgRight;
    private int mTextBgTopBottom;
    private int mTextStart;
    private int mTextEnd;
    private float mProgress = 0;
    private Drawable mTextBg;
    private Paint mPaintText;
    private boolean isNoThumb = false;

    public VSeekBar(Context context) {
        this(context, null);
    }

    public VSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mBackGround = getResources().getDrawable(R.drawable.a_03_04aq);
        mProgressBarBg = getResources().getDrawable(R.drawable.a_03_05a);
        mProgressBar = getResources().getDrawable(R.drawable.a_03_05b);
        mThumb = getResources().getDrawable(R.drawable.a_03_02a);
        mTextBg = getResources().getDrawable(R.drawable.a_03_06);
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(22);
        mPaintText.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mXPercent = (float) (mWidth / 270.0);
        mYPercent = (float) (mHeight / 498.0);
        mProgressLeft = (int) (93 * mXPercent);
        mProgressTop = (int) (38 * mYPercent);
        mProgressRight = (int) (204 * mXPercent);
        mProgressBottom = (int) (460 * mYPercent);
        mThumbLeft = (int) (91 * mXPercent);
        mThumbRight = (int) (203 * mXPercent);
        mThumbHeight = (int) (98 * mYPercent);
        mTextBgLeft = (int) (20 * mXPercent);
        mTextBgRight = (int) (94 * mXPercent);
        mTextBgTopBottom = (int) (32 * mYPercent);
        mTextStart = (int) (26 * mXPercent);
        mTextEnd = (int) (58 * mYPercent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackGround.setBounds(0, 0, mWidth, mHeight);
        mBackGround.draw(canvas);
        mProgressBarBg.setBounds(mProgressLeft, mProgressTop, mProgressRight, mProgressBottom);
        mProgressBarBg.draw(canvas);
        mProgressBar.setBounds(mProgressLeft, getProgressTopY(mProgress), mProgressRight, mProgressBottom);
        mProgressBar.draw(canvas);
        if (!isNoThumb) {
            mThumb.setBounds(mThumbLeft, getThumbTopY(mProgress), mThumbRight, getThumbBottomY(mProgress));
            mThumb.draw(canvas);
        }
        mTextBg.setBounds(mTextBgLeft, getTextBgTopY(mProgress), mTextBgRight, getTextBgBottomY(mProgress));
        mTextBg.draw(canvas);
//        canvas.drawText(mProgress + "%", 26, 58, mPaintText);
        canvas.drawText((int)mProgress + "%", mTextStart, getTextEndY(mProgress), mPaintText);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isNoThumb) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (x >= mThumbLeft && x <= mThumbRight) {
                        setPriProgress((1 - y / mHeight) * 100);
                        if (mOnSeekBarChangeListener != null) {
                            mOnSeekBarChangeListener.onStartTrackingTouch(getProgress());
                        }
                    } else {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (y < 0) {
                        y = 0;
                    }
                    if (y > mHeight) {
                        y = mHeight;
                    }
                    setPriProgress((1 - y / mHeight) * 100);
                    if (mOnSeekBarChangeListener != null) {
                        mOnSeekBarChangeListener.onProgressChanged(getProgress());
                    }
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mOnSeekBarChangeListener != null) {
                        mOnSeekBarChangeListener.onStopTrackingTouch(getProgress());
                    }
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private int getProgressTopY(float progress) {
        if (progress < 0 ) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        float i = (float) ((mProgressBottom - mProgressTop) / 100.0);
        return (int) (mProgressBottom - i * progress);
    }

    private int getThumbTopY(float progress) {
        if (progress < 0 ) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        int d = mHeight - mThumbHeight;
        float i = (float) (d / 100.0);
        return (int) (d - i * progress);
    }

    private int getThumbBottomY(float progress) {
        return getThumbTopY(progress) + mThumbHeight;
    }

    private int getTextBgTopY(float progress) {
        return getThumbTopY(progress) + mTextBgTopBottom;
    }

    private int getTextBgBottomY(float progress) {
        return getThumbBottomY(progress) - mTextBgTopBottom;
    }

    private int getTextEndY(float progress) {
        return getThumbTopY(mProgress) + mTextEnd;
    }

    private void setPriProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(getProgress());
        }
    }

    public float getProgress() {
        return mProgress;
    }

    public void invisibleThumb() {
        isNoThumb = true;
        invalidate();
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    public interface OnSeekBarChangeListener {
        void onProgressChanged(float progress);

        void onStartTrackingTouch(float progress);

        void onStopTrackingTouch(float progress);
    }

}
