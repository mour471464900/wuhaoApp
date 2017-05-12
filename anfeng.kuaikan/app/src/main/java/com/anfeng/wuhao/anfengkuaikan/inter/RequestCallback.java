package com.anfeng.wuhao.anfengkuaikan.inter;

/**
 * 网络请求的统一回调
 * @param <T>     可以转化为String 或者JavaBean
 */
public interface RequestCallback<T> {

        void succeedOnResult(T response) ;
}
