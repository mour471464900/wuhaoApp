package com.anfeng.wuhao.anfengkuaikan.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/18.
 * 描述：
 * =============================
 */

public class RoundLinearLayout extends LinearLayout {

    private RectF mRect;
    private ShapeDrawable mShapeDrawable;
    private float  radius;

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr,float radius) {
        super(context, attrs, defStyleAttr);
        this.radius=radius;
    }

    public RoundLinearLayout(Context context, AttributeSet attrs,float radius) {
        this(context, attrs, 0,radius);
    }

    public RoundLinearLayout(Context context,float radius) {
        this(context, null, 0,radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mRect==null){
            mRect=new RectF(0,0,getWidth(),getHeight());
        }
        Path clipPath=new Path();
        clipPath.addRoundRect(mRect,radius,radius,Path.Direction.CW);
        canvas.clipPath(clipPath);
    }


}