package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R


/**
 *  漫画关注
 */
class CartoonCareFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_cartoon_care,container,false)
    }
}