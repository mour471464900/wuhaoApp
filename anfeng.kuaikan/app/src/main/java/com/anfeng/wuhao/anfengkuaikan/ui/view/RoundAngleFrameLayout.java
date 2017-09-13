package com.anfeng.wuhao.anfengkuaikan.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * 圆角FrameLayout
 * 将任意视图view放入其中后能完成圆角的需求
 * @author ybao
 */
public class RoundAngleFrameLayout extends FrameLayout {

    public static final String TAG="RoundAngleFrameLayout";

    private float mRadius =1f;
    private Paint roundPaint;
    private Paint imagePaint;


    public RoundAngleFrameLayout(Context context) {
        this(context, null);
    }

    public RoundAngleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


//      attrs 文件示例
//    <declare-styleable name="RoundAngleFrameLayout">
//    <attr name="radius" format="dimension" />
//    <attr name="mRadius" format="dimension" />
//    <attr name="topRightRadius" format="dimension" />
//    <attr name="bottomLeftRadius" format="dimension" />
//    <attr name="bottomRightRadius" format="dimension" />
//    </declare-styleable>

    public RoundAngleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        if (attrs != null) {
//              int [] attrsArray={
//                      AFResourceUtil.getAttrsId(context,"radius"),
//                      AFResourceUtil.getAttrsId(context,"mRadius"),
//                      AFResourceUtil.getAttrsId(context,"topRightRadius"),
//                      AFResourceUtil.getAttrsId(context,"bottomLeftRadius"),
//                      AFResourceUtil.getAttrsId(context,"bottomRightRadius")
//              };
//            for (int attr: attrsArray) {
//                LogUtil.e(TAG,attr+"");
//            }
////           由于R文件的原因，目前只 支持app：radius 属性
//            TypedArray ta = context.obtainStyledAttributes(attrs,attrsArray);
//            float radius = ta.getDimension(AFResourceUtil.getStyleableId
//                    (context,"RoundAngleFrameLayout_radius"), 0);
//            mRadius = ta.getDimension(AFResourceUtil.getStyleableId
//                    (context,"RoundAngleFrameLayout_topLeftRadius"), radius);
//            topRightRadius = ta.getDimension(AFResourceUtil.getStyleableId
//                    (context,"RoundAngleFrameLayout_topRightRadius"), radius);
//            bottomLeftRadius = ta.getDimension(AFResourceUtil.getStyleableId
//                    (context,"RoundAngleFrameLayout_bottomLeftRadius"), radius);
//            bottomRightRadius = ta.getDimension(AFResourceUtil.getStyleableId
//                    (context,"RoundAngleFrameLayout_bottomRightRadius"), radius);
//            ta.recycle();
//        }
        roundPaint = new Paint();
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        imagePaint = new Paint();
        imagePaint.setXfermode(null);
    }

    //实现4
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        if (mRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mRadius);
            path.lineTo(0, 0);
            path.lineTo(mRadius, 0);
            path.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, 0, width,
                    mRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - mRadius);
            path.lineTo(0, height);
            path.lineTo(mRadius, height);
            path.arcTo(new RectF(0, height - 2 * mRadius,
                    mRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, height - 2
                    * mRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

}
