package com.anfeng.wuhao.anfengkuaikan.activity

import android.app.Activity
import android.os.Bundle
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.base.BaseKotlinActivity
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_search_main.*

/**
 * Created by Administrator on 2017/8/12.
 */
class SearchActivity : BaseKotlinActivity() {

    var since:Int=0
    override fun setMainView(): Int {
       return  R.layout.activity_search_main
    }

    override fun initView() {
      RxView.clicks(tvCancel).subscribe {
          onBackPressed()
      }
       RxView.clicks(ivChange).subscribe{
           since += 10
       }
        RxView.clicks(ivDelete).subscribe {
            clearHistory()
        }
    }

    private fun clearHistory() {

    }

    override fun requestData() {
       HttpHelper.getInstance().httpGetString(URLCommon.getNewHotWord(since),this,HotWordRequest())
    }

    inner class  HotWordRequest :RequestCallback<String>{
        override fun errorForCode(code: Int) {
          when(code){
              1-> ToastUtil.toastLong("")
          }
        }

        override fun succeedOnResult(response: String?) {

        }

    }

}