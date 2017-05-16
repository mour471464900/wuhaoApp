package com.anfeng.wuhao.anfengkuaikan.net;

import android.content.Context;

import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.NetworkUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil;
import com.lzy.okgo.OkGo;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 网络使用封装类
 * 1.对外部框架进行了封装
 * 2.目前支持Get，Post 请求
 */
public class HttpHelper {
    public static final String TAG = HttpHelper.class.getSimpleName();
    private static HttpHelper mHttpHelper;

    public static final int NET_ERROR=100;
    public static final int SYSTEM_ERROR=101;


    /**
     * 单例工厂模式
     *
     * @return 该类的单例
     */
    public static HttpHelper getInstance() {
        if (null == mHttpHelper) {
            mHttpHelper = new HttpHelper();
        }
        return mHttpHelper;
    }

    /**
     * get请求封装类
     *
     * @param url      url地址
     * @param callback 网络请求回调
     */
    public void httpGetString(String url, final Context context, final RequestCallback<String> callback) {
        OkGo.get(url).execute(new StringDialogCallback(context) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.e(TAG, "get请求成功回调信息" + s);
                callback.succeedOnResult(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.errorForCode(checkNetWork(context, response));

            }
        });
    }

    /**
     * post请求的封装类
     *
     * @param url       url地址
     * @param parameter 请求参数
     * @param callback  互调信息
     */
    public void httpPostString(String url, Map<String, String> parameter, final Context context, final RequestCallback<String> callback) {
        OkGo.post(url).params(parameter, false).execute(new StringDialogCallback(context) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.e(TAG, "post请求成功回调信息" + s);
                callback.succeedOnResult(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.errorForCode(checkNetWork(context, response));
            }
        });
    }

    /**
     * 检查网络情况
     *
     * @param context  上下文
     * @param response 回调
     */
    private int checkNetWork(Context context, Response response) {
        if (!NetworkUtil.isNetworkAvailable(context)) { // 网络情况不好
            LogUtil.e(TAG, "网络异常");
            ToastUtil.toastShort(context, "网络异常");
            return  NET_ERROR;
        } else {                                        // 网络情况好
            LogUtil.e(TAG, "服务器异常");
            ToastUtil.toastShort(context, "服务器异常");
            return  SYSTEM_ERROR;
        }
    }


}
