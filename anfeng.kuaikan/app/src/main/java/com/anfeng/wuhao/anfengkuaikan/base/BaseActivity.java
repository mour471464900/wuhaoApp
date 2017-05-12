package com.anfeng.wuhao.anfengkuaikan.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anfeng.wuhao.anfengkuaikan.utils.AppUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**获取该activity的布局视图**/
    public abstract  int setContentViewId();
    /**由于使用了黄油刀,onCreate中只要进行网络请求就可以了**/
    public abstract void requestData();
    /**由于使用了黄油刀,对于视图的操作还是保留了这个方法**/
    protected abstract void initView();
    /**类的标签方便打印日志**/
    public String getTag(){
        return  this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.setupActivityFullyTransparent(this);
        if(setContentViewId()!=0){
            setContentView(setContentViewId());
        }
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    public void showLongToast(String text){
        ToastUtil.toastLong(this,text);
    }
    public void showLongToast(int resId){
        ToastUtil.toastLong(this,getResources().getText(resId).toString());
    }

    public void showShortToast(String text){
        ToastUtil.toastShort(this,text);
    }
    public void showShortToast(int resId){
        ToastUtil.toastShort(this,getResources().getText(resId).toString());
    }
}
