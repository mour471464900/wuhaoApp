package com.anfeng.wuhao.anfengkuaikan.ui.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R

/**
 *  发现
 */
class DiscoverFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate( R.layout.fragment_discover,container,false)
    }

}