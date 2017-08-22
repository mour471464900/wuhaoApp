package com.anfeng.wuhao.anfengkuaikan.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anfeng.wuhao.anfengkuaikan.utils.AppUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity  {
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
        ToastUtil.toastLong(text);
    }
    public void showLongToast(int resId){
        ToastUtil.toastLong(getResources().getText(resId).toString());
    }

    public void showShortToast(String text){
        ToastUtil.toastShort(text);
    }
    public void showShortToast(int resId){
        ToastUtil.toastShort(getResources().getText(resId).toString());
    }

//    /**
//     * 绑定点击事件
//     * @param mainView   主视图
//     * @param resIds     View id 的数组
//     *  <p> 使用例如：
//     *   bindOnClick(mainView,R.id.btn_main,R.id.btn_login)
//     *   就表示在mainView的视图中将R.id.btn_main,R.id.btn_login 两个视图绑定监听
//     *  </p>
//     *  但使用了ButterKnife就可以将初始化视图以及绑定点击监听一次完成，
//     */
//    public void bindOnClick(View mainView ,int... resIds){
//        for (int resId: resIds) {
//            View v=mainView.findViewById(resId);
//            v.setOnClickListener(this);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//       onBaseClick(v);
//    }
//
//    protected  abstract void onBaseClick(View v);


    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }
}
