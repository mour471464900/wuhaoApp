package com.anfeng.wuhao.anfengkuaikan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.anfeng.wuhao.anfengkuaikan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/12.
 * 描述： 悬浮球
 * =============================
 */

public class FloatingBall extends RelativeLayout {
    private WindowManager mWindowManger;
    private  WindowManager.LayoutParams  mWmInflater;
    @BindView(R.id.iv_float)
    AppImage mFloatBall;

    public FloatingBall(Context context) {
        super(context);
    }

    public FloatingBall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a =context.obtainStyledAttributes(attrs,R.styleable.FloatingBall);
        View inflate = View.inflate(getContext(), R.layout.view_floating_ball, this);
        ButterKnife.bind(this,inflate);

    }






}
