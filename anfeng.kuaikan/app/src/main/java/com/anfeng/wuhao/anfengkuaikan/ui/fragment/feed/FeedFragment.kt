package com.anfeng.wuhao.anfengkuaikan.ui.fragment.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R

/**
 *   社区
 */
class FeedFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate( R.layout.fragment_feed,container,false)
    }


}