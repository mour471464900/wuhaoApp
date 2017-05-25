package com.anfeng.wuhao.anfengkuaikan.net;

import android.content.Context;

import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.NetworkUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 网络使用封装类
 * 1.对外部框架进行了封装
 * 2.目前支持Get，Post 请求
 * 3.缓存使用规则： 请求网络失败后，读取缓存,具体的情况
 * ---网络请求成功,不读取缓存 onBefore -> convertSuccess -> onSuccess -> onAfter<br>
 * ---网络请求失败,读取缓存成功 onBefore -> parseError -> onError -> onCacheSuccess -> onAfter<br>
 * ---网络请求失败,读取缓存失败 onBefore -> parseError -> onError -> onCacheError -> onAfter<br>
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
     * @param url      url地址
     * @param callback 网络请求回调
     *  <p>由于缓存模式为网络请求失败才读取缓存
     *     在使用缓存的时候，会先执行onError()--》onCacheSuccess()
     *  </p>
     */
    public void httpGetString(String url, final Context context, final RequestCallback<String> callback) {
        OkGo.get(url).cacheKey(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.e(TAG, "onSuccess执行的回调字符串：" + s);
                callback.succeedOnResult(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.e(TAG, "onError");
                callback.errorForCode(checkNetWork(context, response));
            }

            @Override
            public void onCacheSuccess(String s, Call call) {
                super.onCacheSuccess(s, call);
                LogUtil.e(TAG, "onCacheSuccess执行的回调字符串：" + s);
                callback.succeedOnResult(s);
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
        OkGo.post(url).cacheKey(url).params(parameter, false).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.e(TAG, "onSuccess执行的回调字符串：" + s);
                callback.succeedOnResult(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.errorForCode(checkNetWork(context, response));
            }

            @Override
            public void onCacheSuccess(String s, Call call) {
                super.onCacheSuccess(s, call);
                LogUtil.e(TAG, "onCacheSuccess执行的回调字符串：" + s);
                callback.succeedOnResult(s);
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
//            ToastUtil.toastShort(context, "网络异常");
            return  NET_ERROR;
        } else {                                        // 网络情况好
            LogUtil.e(TAG, "服务器异常");
//            ToastUtil.toastShort(context, "服务器异常");
            return  SYSTEM_ERROR;
        }
    }


}
