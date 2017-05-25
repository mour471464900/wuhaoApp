package com.anfeng.wuhao.anfengkuaikan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.utils.AppUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/12.
 * 描述： 悬浮球,点击悬浮球可以跟随，手指的活动来跟这屏幕来移动
 * <p>目前只适配竖屏</p>
 * =============================
 */

public class FloatingBall extends RelativeLayout implements View.OnTouchListener {
    private final View inflate;
    private WindowManager mWindowManger;              // 窗口管理器

    private WindowManager.LayoutParams mWmInflater;   // 窗口的布局参数

    @BindView(R.id.iv_float)
    AppImage mFloatBall;     // 悬浮球的图像视图


    public final String TAG=FloatingBall.this.getClass().getSimpleName();  // 标识为类名

    float mWidthPixels;              // 屏幕宽度(px)
    float mHeightPixels;             // 屏幕高度(px)

    private float  xFingerPosition;  // 手指点击到X坐标
    private float  yFingerPosition;  // 手指点击到Y坐标

    private float  xBallPosition;  // 球X坐标
    private float  yBallPosition;  // 球Y坐标

    float  curX ,curY ;            // 相对位置

    public FloatingBall(Context context) {
        this(context, null, 0);
    }

    public FloatingBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWindow(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingBall);
        inflate = View.inflate(getContext(), R.layout.view_floating_ball, this);
        ButterKnife.bind(this, inflate);
        mFloatBall.setOnTouchListener(this);
        a.recycle();
    }

    private void initWindow(Context context) {
        mWindowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);                // 获取当前视图的windowManager
        mWmInflater = new WindowManager.LayoutParams(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);// 设置悬浮球的系统层
        mWmInflater.format = PixelFormat.RGBA_8888;                                                // 表示透明，下面可以看见
        mWmInflater.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWmInflater.gravity = Gravity.LEFT | Gravity.TOP;                                         // 设置重新在左边顶部
        mWidthPixels= Float.valueOf(AppUtil.getDisplayWidth(context));                            //  屏幕的宽度
        mHeightPixels=Float.valueOf(AppUtil.getDisplayHeight(context)) ;                          //  屏幕的高度
        LogUtil.e(TAG,"当前屏幕的大小："+mWidthPixels+"*"+mHeightPixels);                            //  屏幕的大小
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
                LogUtil.e(TAG,"悬浮球被按下");
                mFloatBall.setImageResource(R.drawable.ic_float_ball_pre);
                // 球的位置
                xBallPosition=event.getX();
                yBallPosition=event.getY();
                // 点击的位置
                xFingerPosition=event.getRawX();
                yFingerPosition=event.getRawY();
                LogUtil.e(TAG,"当前按下的位置："+event.getRawX()+"*"+ event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e(TAG,"悬浮球按下后抬起");

                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e(TAG,"悬浮球在移动");
                mFloatBall.setImageResource(R.drawable.ic_float_ball_nor);
                // 更新点击的位置
                xFingerPosition=event.getRawX();
                yFingerPosition=event.getRawY();
                //  移动的距离
                curX=xFingerPosition-xBallPosition;
                curY=yFingerPosition-yBallPosition;
                //  确保不移动到屏幕外部
                if(curY<0)return true;
                if(curY>=(mHeightPixels-mWmInflater.height))  return true;
                LogUtil.e(TAG,"移动的x轴距离："+curX);
                LogUtil.e(TAG,"移动的y轴距离："+curY);
                // 更新控件的位置
                mWmInflater.x= (int) curX;
                mWmInflater.y= (int) curY;
                if(mFloatBall!=null){
                    mWindowManger.updateViewLayout(inflate,mWmInflater);
                }
                break;
        }
        return true;
    }

}
