package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R

/**
 * Created by Administrator on 2017/9/13.
 */
class CartoonHotFragment:BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_cartoon_hot,container,false)
    }
}