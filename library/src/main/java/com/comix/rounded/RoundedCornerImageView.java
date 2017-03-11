package com.comix.rounded;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RoundedCornerImageView extends AppCompatImageView {

    private static int DEFAULT_CORNER_COLOR = Color.WHITE;
    private static int DEFAULT_CORNER_RADIUS = 6;
    private static int CORNER_COUNT = 6;

    private static class Corner {
        int color = DEFAULT_CORNER_COLOR;
        float radius = 0;
        Bitmap bitmap = null;
    }

    /*
     * cache sequence  left top, right top, left bottom, right bottom
     */
    private static Corner[] mCacheCorners = new Corner[CORNER_COUNT];

    private float mDensityMultiplier;
    private int mCornerDisableFlag = 0;
    // sequence "left, top, right, bottom" 4 bit, 1 means true, 0 is false
    private int mRoundedCornerColor = DEFAULT_CORNER_COLOR;
    private float mRoundedCornerRadius = DEFAULT_CORNER_RADIUS;
    private float mRoundedCornerRadiusPx;
    private Corner[] mCorners = new Corner[CORNER_COUNT];

    public RoundedCornerImageView(Context context) {
        super(context);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDensityMultiplier = context.getResources().getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerImageView,
                                                      defStyle, 0);

        mRoundedCornerColor = a.getColor(R.styleable.RoundedCornerImageView_cornerColor,
                                         Color.WHITE);
        mRoundedCornerRadius = a.getDimension(R.styleable.RoundedCornerImageView_cornerRadius,
                                              DEFAULT_CORNER_RADIUS);

        mCornerDisableFlag = 0;
        mCornerDisableFlag += a.getBoolean(R.styleable.RoundedCornerImageView_cornerLeftDisable,
                                           false) ? 1 << 3 : 0;
        mCornerDisableFlag += a.getBoolean(R.styleable.RoundedCornerImageView_cornerTopDisable,
                                           false) ? 1 << 2 : 0;
        mCornerDisableFlag += a.getBoolean(R.styleable.RoundedCornerImageView_cornerRightDisable,
                                           false) ? 1 << 1 : 0;
        mCornerDisableFlag += a.getBoolean(R.styleable.RoundedCornerImageView_cornerBottomDisable,
                                           false) ? 1 : 0;

        a.recycle();
    }

    public int getRoundedCornerColor() {
        return mRoundedCornerColor;
    }

    public void setRoundedCornerColor(int mRoundedCornerColor) {
        this.mRoundedCornerColor = mRoundedCornerColor;
    }

    public float getRoundedCornerRadius() {
        return mRoundedCornerRadius;
    }

    public void setRoundedCornerRadius(float mRoundedCornerRadius) {
        this.mRoundedCornerRadius = mRoundedCornerRadius;
    }

    public void resetDisableCorner() {
        mCornerDisableFlag = 0;
    }

    public void disableCorner(boolean disable, int position) {
        if (disable) {
            mCornerDisableFlag |= 1 << position;
        } else {
            mCornerDisableFlag &= ~(1 << position);
        }
    }

    public int getDisableCorner() {
        return mCornerDisableFlag;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int smallEdge = viewHeight > viewWidth ? viewWidth / 2 : viewHeight / 2;
        mRoundedCornerRadiusPx = (int) (mRoundedCornerRadius * mDensityMultiplier);
        mRoundedCornerRadiusPx = mRoundedCornerRadiusPx > smallEdge ? smallEdge : mRoundedCornerRadiusPx;
        createCornerBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCorners.length; i++) {
            if (mCorners[i] != null && mCorners[i].bitmap != null
                && !(mCorners[i].bitmap.isRecycled())) {
                switch (i) {
                    case 0: // left top (binary 1100)
                        if ((mCornerDisableFlag & 12) == 0) {
                            canvas.drawBitmap(mCorners[i].bitmap, 0, 0, null);
                        }
                        break;
                    case 1: // right top (binary 0110)
                        if ((mCornerDisableFlag & 6) == 0) {
                            canvas.drawBitmap(mCorners[i].bitmap, getWidth() - mRoundedCornerRadiusPx, 0, null);
                        }
                        break;
                    case 2: // left bottom (binary 1001)
                        if ((mCornerDisableFlag & 9) == 0) {
                            canvas.drawBitmap(mCorners[i].bitmap, 0, getHeight() - mRoundedCornerRadiusPx, null);
                        }
                        break;
                    case 3: // right bottom (binary 0011)
                        if ((mCornerDisableFlag & 3) == 0) {
                            canvas.drawBitmap(mCorners[i].bitmap, getWidth() - mRoundedCornerRadiusPx,
                                              getHeight() - mRoundedCornerRadiusPx, null);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void createCornerBitmap() {
        for (int i = 0; i < mCorners.length; i++) {
            if (mCorners[i] == null && mRoundedCornerRadiusPx > 0) {
                // first check if cache has the corner bitmap
                if (mCacheCorners[i] != null && mCacheCorners[i].bitmap != null
                    && !(mCacheCorners[i].bitmap.isRecycled())
                    && mCacheCorners[i].color == mRoundedCornerColor
                    && mCacheCorners[i].radius == mRoundedCornerRadiusPx) {
                    mCorners[i] = mCacheCorners[i];
                } else {
                    mCorners[i] = new Corner();
                    mCorners[i].color = mRoundedCornerColor;
                    mCorners[i].radius = mRoundedCornerRadiusPx;
                    mCorners[i].bitmap = getRoundedCornerBitmap((int) mRoundedCornerRadiusPx, i);
                    mCacheCorners[i] = mCorners[i];
                }
            }
        }
    }

    public Bitmap getRoundedCornerBitmap(int radiusPx, int whichCorner) {
        // create empty bitmap for drawing
        Bitmap output = Bitmap.createBitmap(radiusPx, radiusPx, Bitmap.Config.ARGB_8888);

        // get canvas for empty bitmap
        Canvas canvas = new Canvas(output);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.TRANSPARENT);

        // fill the canvas with transparency
        canvas.drawARGB(0, 0, 0, 0);

        RectF rectF = new RectF(0, 0, width, height);
        paint.setColor(mRoundedCornerColor);
        canvas.drawRect(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        paint.setColor(Color.BLACK);
        switch (whichCorner) {
            case 0: // left top
                canvas.drawCircle(width, height, radiusPx, paint);
                break;
            case 1: // right top
                canvas.drawCircle(0, height, radiusPx, paint);
                break;
            case 2: // left bottom
                canvas.drawCircle(width, 0, radiusPx, paint);
                break;
            case 3: // right bottom
                canvas.drawCircle(0, 0, radiusPx, paint);
                break;
            default:
                break;
        }
        return output;
    }
}
