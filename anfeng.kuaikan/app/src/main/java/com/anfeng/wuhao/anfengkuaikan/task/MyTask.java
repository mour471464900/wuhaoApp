package com.anfeng.wuhao.anfengkuaikan.task;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;

public abstract class MyTask<E> extends AsyncTask<Void, E, E> {
	private static final int SLEEP_TIME = 0;
	/** 执行结果回调 */
	private OperateListener<E> listener = null;
	/** 加载文字提示 */
	private String msg;

	public MyTask(OperateListener<E> listener) {
		this.msg = TextUtils.isEmpty(msg) ? "加载中…": msg;
		this.listener = listener;
	}

	public MyTask() {
		this(null);
	}

	@Override
	protected E doInBackground(Void... params) {
		// 测试使用
		SystemClock.sleep(SLEEP_TIME);
		E e = null;
		e = startTask();
		return e;
	}

	@Override
	protected void onProgressUpdate(E... values) {
		super.onProgressUpdate(values);
		if (null != listener && listener instanceof OperatePublishListener) {
			((OperatePublishListener) listener).publish(values);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(E result) {
		super.onPostExecute(result);
		if (null != listener && listener instanceof OperateResultListener) {
			((OperateResultListener<E>) listener).result(result);
		}
	}

	/**
	 * 线程执行体
	 * 
	 * @return
	 */
	public abstract E startTask();

	public interface OperateListener<T> {
		/** 操作执行逻辑 */
		T operate();
	}

	public interface OperatePublishListener<T> {
		/** 更新操作 */
		void publish(T t);
	}

	public interface OperateResultListener<T> extends OperateListener<T> {
		/** 操作返回结果,执行逻辑---主线程 */
		void result(T t);
	}
}