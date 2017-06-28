package com.anfeng.wuhao.anfengkuaikan.inter;

/**
 * 网络请求的统一回调
 *
 * @param <T> 可以转化为String 或者JavaBean
 */
public interface RequestCallback<T> {
    /**
     * 请求数据成功
     * @param response   请求成功的数据回调
     */
    void succeedOnResult(T response);

    /**
     * 请求失败回调状态码
     * @param code     网络状态异常
     *                 数据解析异常
     */
    void errorForCode(int code);
}
