package com.anfeng.wuhao.anfengkuaikan.ui.fragment

import android.content.Intent
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.base.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.base.HomeActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by Administrator on 2017/8/22.
 */
class SearchFragment :BaseFragment() {
    override fun initView() {
     RxView.clicks(tagHotWord).subscribe {
         var intent= Intent(context,HomeActivity.SecondActivity::class.java)
         intent.putExtra("name","wuhao")
       context.startActivity(intent)
     }
    }

    override fun requestData() {

    }

    override fun setLayoutId(): Int = R.layout.fragment_search
}