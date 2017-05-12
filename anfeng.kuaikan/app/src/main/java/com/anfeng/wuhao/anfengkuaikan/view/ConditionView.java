package com.anfeng.wuhao.anfengkuaikan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
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
     * 数据正常
     **/
    public static final int DATA_NORMAL = 1;
    /**
     * 网络异常
     **/
    public static final int NETWORK_ERROR = 2;
    /**
     * 没有数据
     **/
    public static final int NO_DATA = 3;
    /**
     *  错误资源id标识
     */
    public static final int ERROR_RES_ID=-1;


    private String netErrorHint; // 网络异常提示文字
    private int netErrorIcon; // 网络异常的图标

    @BindView(R.id.iv_net_error)
    ImageView mIvNetError;
    @BindView(R.id.tv_net_error)
    TextView mTvNetError;
    @BindView(R.id.iv_loading_anim)
    ImageView mIvLoadingAnim;
    @BindView(R.id.ll_load)
    LinearLayout mLlLoad;
    @BindView(R.id.btn_retry)
    Button mBtnRetry;
    @BindView(R.id.ll_load_error)
    LinearLayout mLlLoadError;
    @BindView(R.id.btn_load)
    Button mBtnLoad;
    @BindView(R.id.ll_net_error)
    LinearLayout mLlNetError;
    /**
     * “点击重试”按钮的监听
     **/
    private OnRetryListener mOnRetryListener;

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
        a.recycle();
        initView();
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
            case DATA_NORMAL:
                mLlLoad.setVisibility(GONE);
                mLlLoadError.setVisibility(GONE);
                mLlNetError.setVisibility(GONE);
                break;
            case NETWORK_ERROR:
                break;
            case NO_DATA:
                break;
        }
    }

    @OnClick(R.id.btn_retry)
    public void onMBtnRetryClicked() {
    }

    @OnClick(R.id.btn_load)
    public void onMBtnLoadClicked() {
    }

    /**
     * 设置网络异常的提示语
     * @param netErrorHint   提示语
     */
    public  void setNetErrorHint( String netErrorHint) {
        if (!TextUtils.isEmpty(netErrorHint)) {
            mTvNetError.setText(netErrorHint);
        }
    }

    /**
     * 设置网络异常的图标
     * @param netErrorIcon  异常图标
     */
    public void setNetErrorIcon(int netErrorIcon){
        if(netErrorIcon!=ERROR_RES_ID){
            mIvNetError.setImageResource(netErrorIcon);
        }
    }


    /**
     * “点击重试” 按钮的监听
     */
    public interface OnRetryListener {
        void onRetry();
    }


}
