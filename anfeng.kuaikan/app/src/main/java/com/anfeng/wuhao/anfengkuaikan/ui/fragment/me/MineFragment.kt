package com.anfeng.wuhao.anfengkuaikan.ui.fragment.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.wuhao.anfengkuaikan.R

/**
 *  我的
 */
class MineFragment: com.anfeng.game.ui.BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(  R.layout.fragemnt_mine,container,false)
    }
}