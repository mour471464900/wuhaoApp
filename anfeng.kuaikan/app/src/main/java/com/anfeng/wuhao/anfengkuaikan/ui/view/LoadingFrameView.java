package com.anfeng.wuhao.anfengkuaikan.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.inter.ViewImageClickListener;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Android 抽象布局 减少视图层级
 * 
 * @author GaiQS E-mail:gaiqs@sina.com
 * @Date 2016年3月2日
 * @Time 上午11:16:25
 */
public class LoadingFrameView extends RelativeLayout {
	private static final int DELAY_MILLIS = 100;
	// 各种状态桢标志
	public final static int CONTAINER_ITEM = 0;
	public final static int PROGRESS_ITEM = 1;
	public final static int EMPTY_ITEM = 2;
	public final static int ERROR_ITEM = 3;
	public final static int REPEAT_ITEM = 4;
	public final static int CUSTOM_LAYOUT = 5;
	RelativeLayout mViewContainer;
	TextView mProgressView;
	LinearLayout mEmptyView;
	TextView mErrorView;
	LinearLayout mRepeatLayout;// 重试
	ImageView mRepeatView;
	TextView mRepeatInfo;
	private int layoutId;// 自定义布局体id
	private Runnable mRepeatRunnable;
	// 当前布局显示状态
	private int lastItem;
    // 重试接口
	private  OnRetryListener mOnRetryListener;


	public LoadingFrameView(Context context) {
		this(context, null, 0);
	}

	public LoadingFrameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadingFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View inflate = View.inflate(getContext(), R.layout.loading_frame_layout, this);
		initView(inflate);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingFrameView);
		setProgressInfo(a.getString(R.styleable.LoadingFrameView_fv_progressInfo));
		setEmptyInfo(a.getString(R.styleable.LoadingFrameView_fv_emptyInfo));
		setEmptyIcon(a.getResourceId(R.styleable.LoadingFrameView_fv_emptyIcon, R.drawable.load_error));
		setErrorInfo(a.getString(R.styleable.LoadingFrameView_fv_errorInfo));
		setErrorIcon(a.getResourceId(R.styleable.LoadingFrameView_fv_errorIcon, R.drawable.load_error));
		setRepeatInfo(a.getString(R.styleable.LoadingFrameView_fv_repeatInfo));
		setRepeatIcon(a.getResourceId(R.styleable.LoadingFrameView_fv_repeatIcon, R.drawable.load_error));
		setFrame(a.getInt(R.styleable.LoadingFrameView_fv_frame, CONTAINER_ITEM));
		layoutId = a.getResourceId(R.styleable.LoadingFrameView_fv_customLayout, -1);
		setDefaultProgressInfo(context);
//		setRepeatListener();
		a.recycle();
	}

	private void initView(View inflate) {
		mViewContainer= (RelativeLayout) inflate.findViewById(R.id.container);
		mProgressView= (TextView) inflate.findViewById(R.id.load_info);
		mEmptyView= (LinearLayout) inflate.findViewById(R.id.empty_container);
		mErrorView= (TextView) inflate.findViewById(R.id.error_container);
		mRepeatLayout= (LinearLayout) inflate.findViewById(R.id.repeat_container);
		mRepeatView= (ImageView) inflate.findViewById(R.id.iv_repeat_pic);
		mRepeatInfo= (TextView) inflate.findViewById(R.id.tv_try);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 这里,自定义的layout才解析完,这时能拿到用户自定义的xml布局体,他的位置在最后
		int index = REPEAT_ITEM + 1;// 从此处起始,添加所有用户自定义view
		// 这里不能用i++,因为removeView之后,角标自动前移0.就能拿到后面的view
		for (int i = index; i < getChildCount();) {
			View childView = getChildAt(i);
			removeView(childView);
			mViewContainer.addView(childView);
		}
		if (0 < layoutId) {
			// 装载自定义布局,默认隐藏
			View inflateView = inflate(getContext(), layoutId, null);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			addView(inflateView, CUSTOM_LAYOUT, params);
			View customView = getChildAt(CUSTOM_LAYOUT);
			if (null != customView) {
				customView.setVisibility(View.GONE);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * 设置加载进度信息
	 *
	 * @param info
	 *            加载提示信息
	 */
	public void setProgressInfo(String info) {
		if (null != this.mProgressView) {
			this.mProgressView.setText(info);
		}
	}

	/**
	 * 设置默认的加载信息
	 */
	private void setDefaultProgressInfo(Context context) {
		CharSequence text = this.mProgressView.getText();
		if (TextUtils.isEmpty(text)) {
			setProgressInfo(context.getString(R.string.loading));
		}
	}

	public void setEmptyInfo(int resId) {
		if (null != mEmptyView) {
			((TextView)mEmptyView.findViewById(R.id.tv_empty_info)).setText(resId);
		}
	}

	/**
	 * 设置空布局信息
	 *
	 * @param info
	 *            空布局提示信息
	 */
	public void setEmptyInfo(String info) {
		if (null != mEmptyView) {
			((TextView)mEmptyView.findViewById(R.id.tv_empty_info)).setText(info);
		}
	}

	public void setEmptyDrawable(Drawable drawable) {
		if (null != mEmptyView) {
			((TextView)mEmptyView.findViewById(R.id.tv_empty_info)).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		}
	}

	public void setEmptyIcon(int resId) {
		if (null != mEmptyView) {
			((TextView)mEmptyView.findViewById(R.id.tv_empty_info)).setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
		}
	}

	/**
	 * 设置空布局信息
	 *
	 * @param info
	 */
	public void setErrorInfo(String info) {
		if (null != mErrorView && !TextUtils.isEmpty(info)) {
			mErrorView.setText(info);
		}
	}

	public void setErrorInfo(int resId) {
		if (null != mErrorView) {
			mErrorView.setText(resId);
		}
	}

	public void setErrorDrawable(Drawable drawable) {
		if (null != mErrorView) {
			mErrorView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		}
	}

	/**
	 * 设置error提示标志
	 *
	 * @param resId
	 *            异常信息提示引用资源
	 */
	public void setErrorIcon(int resId) {
		if (null != mErrorView) {
			mErrorView.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
		}
	}

	public void setLoadInfo(int resId) {
		if (null != mProgressView) {
			mProgressView.setText(resId);
		}
	}

	/**
	 * 设置显示空布局体
	 *
	 * @param animate
	 *            true:显示 false:隐藏
	 */
	public void setEmptyShown(boolean animate) {
		setFrame(EMPTY_ITEM, animate, false);
	}

	/**
	 * 延迟设置空布局体
	 *
	 * @param animate
	 */
	public void delayShowEmpty(final boolean animate) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				setEmptyShown(animate);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 设置进度装载显示
	 *
	 * @param animate
	 *            true:显示 false:隐藏
	 */
	public void setProgressShown(boolean animate) {
		setFrame(PROGRESS_ITEM, animate, false);
	}

	/**
	 * 设置重试布局是否显示
	 *
	 * @param animate
	 *            true:显示 false:隐藏
	 */
	public void setRepeatShown(boolean animate) {
		setFrame(REPEAT_ITEM, animate, false);
	}

	/**
	 * 设置重试跳转设置事件
	 */
	public void setRepeatSetting() {
		setRepeatShown(true);
//		setRepeatInfo(R.string.setting);
		// mRepeatRunnable = () -> PackageUtils.startSetting(getContext());
	}

	/**
	 * 设置重试事件
	 *
	 * @param runnable
	 */
	public void setRepeatRunnable(Runnable runnable) {
		delayShowRepeat(true);
		setRepeatInfo(R.string.refresh_try);
		mRepeatRunnable = runnable;
	}

	/**
	 * 设置内容与进度并存的布局状态
	 *
	 * @param animate
	 */
	public void setContainerProgressShown(boolean animate) {
		int item = PROGRESS_ITEM;
		lastItem = CONTAINER_ITEM;
		if (null != mViewContainer) {
			View showView = getChildAt(item);
			View closeView = getChildAt(lastItem);
			showView.setVisibility(View.VISIBLE);
			closeView.setVisibility(View.VISIBLE);
			if (animate) {
				ViewHelper.setAlpha(showView, 0f);
				ViewPropertyAnimator.animate(showView).alpha(1f);
			} else {
				showView.clearAnimation();
			}
			lastItem = item;
		}
	}

	/**
	 * 延迟设置进度体
	 *
	 * @param animate
	 */
	public void delayShowProgress(final boolean animate) {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				setProgressShown(animate);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 延迟设置进度体
	 *
	 * @param animate
	 */
	public void delayShowRepeat(final boolean animate) {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				setRepeatShown(animate);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 设置错误布局体显示
	 *
	 * @param animate
	 *            true:显示 false:隐藏
	 */
	public void setErrorShown(boolean animate) {
		setFrame(ERROR_ITEM, animate, false);
	}

	/**
	 * 延迟设置错误布局体
	 *
	 * @param animate
	 */
	public void delayShowError(final boolean animate) {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				setErrorShown(animate);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 设置布局体显示
	 *
	 * @param animate
	 *            true:显示 false:隐藏
	 */
	public void setContainerShown(boolean animate) {
		setFrame(CONTAINER_ITEM, animate, false);
	}

	/**
	 * 延迟设置布局体
	 *
	 * @param animate
	 */
	public void delayShowContainer(final boolean animate) {
		postDelayed(new Runnable() {
			@Override
			public void run() {

				setContainerShown(animate);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 设置重试提示图标信息
	 *
	 * @param resId
	 *            提示图标引用资源id
	 */
	public void setRepeatIcon(int resId) {
//		this.mRepeatView.setImageResource(resId);
		LogUtil.e("cuowu",mRepeatView.toString());
		this.mRepeatView.setImageResource(R.drawable.load_error);
	}

	/**
	 * 设置重试按钮文字
	 *
	 * @param info
	 */
	public void setRepeatInfo(String info) {
		if (null != mRepeatInfo && !TextUtils.isEmpty(info)) {
			mRepeatInfo.setText(info);
		}
	}

	/**
	 * 设置重试按钮文字
	 *
	 * @param resid
	 */
	public void setRepeatInfo(int resid) {
		if (null != mRepeatInfo) {
			mRepeatInfo.setText(resid);
		}
	}

	/**
	 * 设置空布局点击事件
	 *
	 * @param listener
	 */
	public void setEmptyListener(OnClickListener listener) {
		if (null != mEmptyView) {
			mEmptyView.setOnClickListener(new ViewImageClickListener(listener));
		}
	}

	/**
	 * 设置异常局点击事件
	 *
	 * @param listener
	 */
	public void setErrorListener(OnClickListener listener) {
		if (null != mErrorView) {
			mErrorView.setOnClickListener(new ViewImageClickListener(listener));
		}
	}

	/**
	 * 设置重试监听
	 * @param onRetryListener
     */
	public  void setRepeatListener(final OnRetryListener onRetryListener) {
		if (null != mRepeatLayout) {
			mRepeatLayout.findViewById(R.id.tv_try).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != onRetryListener) {
						onRetryListener.onRetryListener();
					}
				}
			});
		}
	}

	/**
	 * 设置自定义布局是否显示
	 *
	 * @param animate
	 */
	public void setCustomLayoutShown(boolean animate) {
		setFrame(CUSTOM_LAYOUT, animate, false);
	}

	/**
	 * 延持显示自定义布局
	 *
	 * @param animate
	 */
	public void delayCustomLayoutShown(final boolean animate) {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				setFrame(CUSTOM_LAYOUT, animate, false);
			}
		}, DELAY_MILLIS);
	}

	/**
	 * 设置自定义布局体点击
	 *
	 * @param id
	 * @param listener
	 */
	public void setCustomLayoutViewClickListener(int id, OnClickListener listener) {
		View view = getChildAt(CUSTOM_LAYOUT);
		if (null != view) {
			View findView = view.findViewById(id);
			if (null != findView) {
				findView.setOnClickListener(listener);
			}
		}
	}

	public View findCustomView(int id) {
		View findView = null;
		View view = getChildAt(CUSTOM_LAYOUT);
		if (null != view) {
			findView = view.findViewById(id);
		}
		return findView;
	}

	public boolean isStatus(int status) {
		return lastItem == status;
	}

	/**
	 * 设置显示桢
	 *
	 * @param item
	 */
	public void setFrame(int item) {
		setFrame(item, false, true);
	}

	/**
	 * 设置展示桢view
	 *
	 * @param item
	 *            当前展示桢
	 * @param animate
	 *            是否显示动画
	 * @param unChecked
	 *            无须检测last==当前指定桢
	 */
	private void setFrame(final int item, final boolean animate, boolean unChecked) {
		if (null != mViewContainer && (unChecked || item != lastItem)) {
			View showView = getChildAt(item);
			View closeView = getChildAt(lastItem);
			if (animate) {
				ViewHelper.setAlpha(showView, 0f);
				ViewPropertyAnimator.animate(showView).setDuration(200).alpha(1f);
			}
			showView.clearAnimation();
			closeView.clearAnimation();
			closeView.setVisibility(View.GONE);
			showView.setVisibility(View.VISIBLE);
			lastItem = item;
		}
	}

	/**
	 * 设置背景色
	 *
	 * @param color
	 */
	public void setBackGroundColor(int color) {
		setBackgroundColor(color);
	}

    public interface  OnRetryListener{
		void onRetryListener();
	}

	public OnRetryListener getmOnRetryListener() {
		return mOnRetryListener;
	}

	public void setmOnRetryListener(OnRetryListener mOnRetryListener) {
		this.mOnRetryListener = mOnRetryListener;
	}

	public void setRetryCondition(OnRetryListener onRetryListener){
		setRepeatShown(true);
		setRepeatListener(onRetryListener);
	}

}
