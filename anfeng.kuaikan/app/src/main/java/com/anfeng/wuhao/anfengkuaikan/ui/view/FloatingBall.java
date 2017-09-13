package com.anfeng.wuhao.anfengkuaikan.ui.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.utils.AppUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/12.
 * 描述： 悬浮球,点击悬浮球可以跟随，手指的活动来跟这屏幕来移动
 * <p>目前只适配竖屏</p>
 * =============================
 */

public class FloatingBall implements View.OnTouchListener {

    private WindowManager mWindowManger;              // 窗口管理器
    private WindowManager.LayoutParams mWmInflater;   // 窗口的布局参数

    AppImage mFloatBall;     // 悬浮球的图像视图


    public final String TAG = FloatingBall.this.getClass().getSimpleName();  // 标识为类名

    float mWidthPixels;              // 屏幕宽度(px)
    float mHeightPixels;             // 屏幕高度(px)

    float startX;
    float startY;
    float tempX;
    float tempY;

    /**
     * @param context
     */
    public FloatingBall(Context context) {
        mFloatBall = new AppImage(context);
        mFloatBall.setImageResource(R.drawable.ic_float_ball_nor);
        mFloatBall.setOnTouchListener(this);
        initWindow(context);
    }

    private void initWindow(Context context) {
        mWindowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);                // 获取当前视图的windowManager
        mWmInflater = new WindowManager.LayoutParams(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);// 设置悬浮球的系统层
        mWmInflater.format = PixelFormat.RGBA_8888;                                                // 表示透明，下面可以看见
        mWmInflater.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWmInflater.gravity = Gravity.LEFT | Gravity.TOP;                                         // 设置重新在左边顶部
        mWidthPixels = Float.valueOf(AppUtil.getDisplayWidth(context));                            //  屏幕的宽度
        mHeightPixels = Float.valueOf(AppUtil.getDisplayHeight(context));                          //  屏幕的高度
        LogUtil.e(TAG, "当前屏幕的大小：" + mWidthPixels + "*" + mHeightPixels);                            //  屏幕的大小
        // 先把坐标写死放在屏幕中间
        mWmInflater.x = 100;
        mWmInflater.y = 200;
        mWmInflater.width=150;
        mWmInflater.height=150;
        try {
            mWindowManger.addView(mFloatBall, mWmInflater);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不响应onTouchEvent ,onClick事件
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e(TAG, "悬浮球被按下");
                mFloatBall.setImageResource(R.drawable.ic_float_ball_pre);
                startX = event.getRawX();
                startY = event.getRawY();

                tempX = event.getRawX();
                tempY = event.getRawY();
                LogUtil.e(TAG, "当前按下的位置：" + event.getRawX() + "*" + event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e(TAG, "悬浮球按下后抬起");
                mFloatBall.setImageResource(R.drawable.ic_float_ball_nor);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e(TAG, "悬浮球在移动");
                mFloatBall.setImageResource(R.drawable.ic_float_ball_pre);
                float x = event.getRawX() - startX;
                float y = event.getRawY() - startY;
                //计算偏移量，刷新视图
                mWmInflater.x += x;
                mWmInflater.y += y;
                mWindowManger.updateViewLayout(mFloatBall, mWmInflater);
                startX = event.getRawX();
                startY = event.getRawY();
                if (mFloatBall != null) {
                    mWindowManger.updateViewLayout(mFloatBall, mWmInflater);
                }
                break;
        }
        return true;
    }


    public void remove(){
        if(mWindowManger!=null){
            mWindowManger.removeView(mFloatBall);
        }
    }

}
