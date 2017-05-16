package com.anfeng.wuhao.anfengkuaikan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anfeng.wuhao.anfengkuaikan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/5.
 * 描述： 状态视图，专门显示是如何没有数据的空视图
 * 1.当数据正常加载时，且有数据的情况下，该视图必须隐藏
 * 2.当数据正常加载，但是由于网络异常，该视图会呈现网络异常的重试的按钮
 * 3.当数据正常加载，但是服务器没有给客户端数据时，才会显示没有数据
 * =============================
 */
public class ConditionView extends RelativeLayout {
    /**
     * 显示容器内容
     */
    public static final int CONTAINER = 0;

    /**
     * 正在加载
     */
    public static final int DATA_LOADING = 1;
    /**
     * 网络正常 ,但是没有数据（数据解析出错或者没有数据）
     */
    public static final int DATA_EMPTY = 2;

    /**
     * 网络异常,可以点击“重试按钮”
     **/
    public static final int DATA_RETRY = 3;


    /**
     * 错误资源id标识
     */
    public static final int ERROR_RES_ID = -1;
    @BindView(R.id.iv_loading_anim)
    ImageView mIvLoadingAnim;
    @BindView(R.id.ll_load)
    LinearLayout mLlLoad;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.iv_net_error)
    ImageView mIvNetError;
    @BindView(R.id.tv_net_error)
    TextView mTvNetError;
    @BindView(R.id.btn_load)
    Button mBtnLoad;
    @BindView(R.id.ll_net_error)
    LinearLayout mLlNetError;
    @BindView(R.id.rl_container)
    RelativeLayout mRlContainer;
    private String netErrorHint; // 网络异常提示文字
    private int netErrorIcon; // 网络异常的图标

    // 当前布局显示状态
    private int lastItem;
    /**
     * “点击重试”按钮的监听
     **/
    private OnRetryListener mOnRetryListener;
    private AnimationDrawable mDrawable;

    public ConditionView(Context context) {
        this(context, null, 0);
    }

    public ConditionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConditionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = View.inflate(context, R.layout.view_condition, this);
        ButterKnife.bind(this, inflate);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConditionView);
        netErrorHint = a.getString(R.styleable.ConditionView_netErrorHint);
        netErrorIcon = a.getResourceId(R.styleable.ConditionView_netErrorIcon, ERROR_RES_ID);
        setFrame(a.getInt(R.styleable.ConditionView_viewCondition, CONTAINER));
        initView();
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 这里,自定义的layout才解析完,这时能拿到用户自定义的xml布局体,他的位置在最后
        int index = DATA_RETRY + 1;// 从此处起始,添加所有用户自定义view
        // 这里不能用i++,因为removeView之后,角标自动前移.就能拿到后面的view
        for (int i = index; i < getChildCount(); ) {
            View childView = getChildAt(i);
            removeView(childView);
            mRlContainer.addView(childView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void initView() {
        setNetErrorHint(netErrorHint);
        setNetErrorIcon(netErrorIcon);
    }

    /**
     * 切换视图的状态
     *
     * @param code 视图的状态码
     */
    public void setDataCondition(int code) {
        switch (code) {
            case CONTAINER:
                setFrame(CONTAINER, true, false);
                break;
            case DATA_LOADING:
                setFrame(DATA_LOADING, true, false);
                break;
            case DATA_EMPTY:
                setFrame(DATA_EMPTY, true, false);
                break;
            case DATA_RETRY:
                setFrame(DATA_RETRY, true, false);
                break;
        }
    }


    @OnClick(R.id.btn_load)
    public void onMBtnLoadClicked() {
        if (null != mOnRetryListener) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 设置网络异常的提示语
     *
     * @param netErrorHint 提示语
     */
    public void setNetErrorHint(String netErrorHint) {
        if (!TextUtils.isEmpty(netErrorHint)) {
            mTvNetError.setText(netErrorHint);
        }
    }

    /**
     * 设置网络异常的图标
     *
     * @param netErrorIcon 异常图标
     */
    public void setNetErrorIcon(int netErrorIcon) {
        if (netErrorIcon != ERROR_RES_ID) {
            mIvNetError.setImageResource(netErrorIcon);
        }
    }



    /**
     * “点击重试” 按钮的监听
     */
    public interface OnRetryListener {
        void onRetry();
    }

    /**
     * 设置展示桢view
     *
     * @param item      当前展示桢
     * @param animate   是否显示动画
     * @param unChecked 无须检测last==当前指定桢
     */
    private void setFrame(final int item, final boolean animate, boolean unChecked) {
        if (null != mRlContainer && (unChecked || item != lastItem)) {
            View showView = getChildAt(item);
            View closeView = getChildAt(lastItem);
            if (animate) {
                mDrawable = (AnimationDrawable) mIvLoadingAnim.getDrawable();
                mDrawable.start();
            } else {
                if (mDrawable != null && mDrawable.isRunning()) {
                    mDrawable.stop();
                }
            }
            showView.clearAnimation();
            closeView.clearAnimation();
            closeView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);
            lastItem = item;
        }
    }

    private void setFrame(int item) {
        setFrame(item, false, true);
    }


    public OnRetryListener getmOnRetryListener() {
        return mOnRetryListener;
    }

    /**
     * 设置重试监听
     *
     * @param mOnRetryListener
     */
    public void setmOnRetryListener(OnRetryListener mOnRetryListener) {
        this.mOnRetryListener = mOnRetryListener;
    }
}
