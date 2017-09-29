package com.anfeng.wuhao.anfengkuaikan.base;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera;

import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.CustomCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.EmptyCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.ErrorCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.LoadingCallback;
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.TimeoutCallback;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import org.litepal.LitePal;

import java.util.logging.Level;

/**
 *  公用application
 */
public class MainApplication extends Application {

    public static final String TAG=MainApplication.class.getSimpleName();

    public static  MainApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this); // 初始化Fresco
        OkGo.init(this);         // 初始化okgo请求框架
        initOkGo();
        LitePal.initialize(this); // 初始化litePal
        mainApplication=this;
        initLoadSir();
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    public static Context getContext(){
        return  mainApplication.getApplicationContext();
    }


    /**
     * 设置okgo工具类
     */
    private void initOkGo() {
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE) //  请求网络失败后，读取缓存
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(6);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
