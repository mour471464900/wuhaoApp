package com.anfeng.wuhao.anfengkuaikan.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Administrator on 2017/8/15.
 */
abstract class BaseKotlinActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setMainView())
        initView()
        requestData()
    }
    abstract fun requestData()

    abstract fun initView()

    abstract fun setMainView(): Int
}