package com.anfeng.wuhao.anfengkuaikan.task;


public class MyAsyncTask {
	/**
	 * 异步执行数据库操作
	 */
	public static <T> void executor(final MyTask.OperateListener<T> listener) {
		new MyTask<T>(listener) {
			@Override
			public T startTask() {
				T t = null;
				if (null != listener) {
					if (listener instanceof OperateListener) {
						t = listener.operate();
					} else if (listener instanceof OperatePublishListener) {
						((OperatePublishListener<T>) listener).publish(t);
					}
				}
				return t;
			}

			protected void onPostExecute(T t) {
				super.onPostExecute(t);
				// 当结果不为null时才回调
				if (null != t && null != listener && listener instanceof OperateResultListener) {
					((OperateResultListener<T>) listener).result(t);
				}
			};

		}.execute();
	}

}
