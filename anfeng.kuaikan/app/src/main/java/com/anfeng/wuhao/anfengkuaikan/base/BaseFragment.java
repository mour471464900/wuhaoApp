package com.anfeng.wuhao.anfengkuaikan.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * base层Fragment
 * 1.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 由于Fragment的生命周期的问题,在onCreate中bind(),在onDestroyView中unbind(),减少开销
     **/
    private Unbinder bind;

    /**
     * 获取该activity的布局视图
     **/
    public abstract int setLayoutId();

    /**
     * 请求网络
     */
    public abstract void requestData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        requestData();
        return view;
    }

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
