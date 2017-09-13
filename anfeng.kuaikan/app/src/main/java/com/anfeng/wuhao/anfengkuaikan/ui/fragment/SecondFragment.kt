package com.anfeng.wuhao.anfengkuaikan.ui.fragment

import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.base.BaseFragment

/**
 * Created by Administrator on 2017/8/22.
 */
class SecondFragment:BaseFragment() {
    override fun initView() {
        var charSequence = arguments.getCharSequence("name")

    }

    override fun requestData() {

    }

    override fun setLayoutId(): Int= R.layout.fragment_second
}