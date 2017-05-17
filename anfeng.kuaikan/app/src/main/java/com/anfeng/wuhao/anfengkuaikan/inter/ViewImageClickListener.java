package com.anfeng.wuhao.anfengkuaikan.inter;

import android.view.View;
import android.view.View.OnClickListener;

import com.anfeng.wuhao.anfengkuaikan.anim.AnimationUtils;

/**
 * 点击图片抖动事件
 * 
 */
public class ViewImageClickListener implements OnClickListener {
	private final OnClickListener listener;

	public ViewImageClickListener(OnClickListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void onClick(final View v) {
		AnimationUtils.startViewImageAnim(v);
		if (null != listener) {
			listener.onClick(v);
		}
	}

}
