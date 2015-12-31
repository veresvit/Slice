package io.github.mthli.slice;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

public class CustomRoundRectDrawable extends RoundRectDrawable {
    private boolean leftTopRect = true;
    private boolean rightTopRect = true;
    private boolean leftBottomRect = false;
    private boolean rightBottomRect = false;

    public CustomRoundRectDrawable(int backgroundColor, float radius) {
        super(backgroundColor, radius);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mBoundsF, mRadius, mRadius, mPaint);

        if (leftTopRect) {
            canvas.drawRect(buildLeftTopRect(), mPaint);
        }

        if (rightTopRect) {
            canvas.drawRect(buildRightTopRect(), mPaint);
        }

        if (rightBottomRect) {
            canvas.drawRect(buildRightBottomRect(), mPaint);
        }

        if (leftBottomRect) {
            canvas.drawRect(buildLeftBottomRect(), mPaint);
        }
    }

    @Override
    public void getOutline(Outline outline) {
        if (buildConvexPath().isConvex()) {
            outline.setConvexPath(buildConvexPath());
        } else {
            super.getOutline(outline);
        }
    }

    private RectF buildLeftTopRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.left;
        rectF.top = mBoundsF.top;
        rectF.right = mBoundsF.left + mRadius * 2.0f;
        rectF.bottom = mBoundsF.top + mRadius * 2.0f;

        return rectF;
    }

    private RectF buildRightTopRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.right - mRadius * 2.0f;
        rectF.top = mBoundsF.top;
        rectF.right = mBoundsF.right;
        rectF.bottom = mBoundsF.top + mRadius * 2.0f;

        return rectF;
    }

    private RectF buildRightBottomRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.right - mRadius * 2.0f;
        rectF.top = mBoundsF.bottom - mRadius * 2.0f;
        rectF.right = mBoundsF.right;
        rectF.bottom = mBoundsF.bottom;

        return rectF;
    }

    private RectF buildLeftBottomRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.left;
        rectF.top = mBoundsF.bottom - mRadius * 2.0f;
        rectF.right = mBoundsF.left + mRadius * 2.0f;
        rectF.bottom = mBoundsF.bottom;

        return rectF;
    }

    private Path buildConvexPath() {
        Path path = new Path();

        // 移动到指定的绘制起点
        path.moveTo(mBoundsF.left, (mBoundsF.top + mBoundsF.bottom) / 2.0f);

        // 判断是否需要绘制左上角的圆角轮廓
        path.lineTo(mBoundsF.left, mBoundsF.top + mRadius);
        if (leftTopRect) {
            path.lineTo(mBoundsF.left, mBoundsF.top);
        } else {
            RectF rectF = new RectF(mBoundsF.left, mBoundsF.top, mBoundsF.left + mRadius * 2.0f, mBoundsF.top + mRadius * 2.0f);
            path.arcTo(rectF, 180.0f, 90.0f);
        }

        // 判断是否需要绘制右上角的圆角轮廓
        path.lineTo(mBoundsF.right - mRadius, mBoundsF.top);
        if (rightTopRect) {
            path.lineTo(mBoundsF.right, mBoundsF.top);
        } else {
            RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.top, mBoundsF.right, mBoundsF.top + mRadius * 2.0f);
            path.arcTo(rectF, 270.0f, 90.0f);
        }

        // 判断是否需要绘制右下角的圆角轮廓
        path.lineTo(mBoundsF.right, mBoundsF.bottom - mRadius);
        if (rightBottomRect) {
            path.lineTo(mBoundsF.right, mBoundsF.bottom);
        } else {
            RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.right, mBoundsF.bottom);
            path.arcTo(rectF, 0.0f, 90.0f);
        }

        // 判断是否需要绘制左下脚的圆角轮廓
        path.lineTo(mBoundsF.left + mRadius, mBoundsF.bottom);
        if (leftBottomRect) {
            path.lineTo(mBoundsF.left, mBoundsF.bottom);
        } else {
            RectF rectF = new RectF(mBoundsF.left, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.left + mRadius * 2.0f, mBoundsF.bottom);
            path.arcTo(rectF, 90.0f, 90.0f);
        }

        // 连接成一个整体
        path.close();

        return path;
    }

    public void setLeftTopRect(boolean leftTopRect) {
        this.leftTopRect = leftTopRect;
    }

    public void setRightTopRect(boolean rightTopRect) {
        this.rightTopRect = rightTopRect;
    }

    public void setRightBottomRect(boolean rightBottomRect) {
        this.rightBottomRect = rightBottomRect;
    }

    public void setLeftButtomRect(boolean leftBottomRect) {
        this.leftBottomRect = leftBottomRect;
    }
}
