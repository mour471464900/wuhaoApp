package com.anfeng.wuhao.anfengkuaikan.ui.fragment.web

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_webview.*

/**
 * Created by Administrator on 2017/9/30.
 */
class WebFragment: BaseFragment() {

    companion object {
        val URL="url"
        val HTML="html"
    }

    @SuppressLint("HandlerLeak")
     var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_webview,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        setupData()
    }

    private fun setupData() {
        var url = activity.intent.getStringExtra(URL)
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.defaultTextEncodingName = "UTF-8"
        webView.settings.loadWithOverviewMode = true
        webView.settings.domStorageEnabled = true
        webView.setWebChromeClient(ChromeClient())
        webView.setWebViewClient(NormalClient())
    }

    inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(webView: WebView, i: Int) {
            LogUtil.e(tag, "当前加载进度:" + i)
            if (fragmentIsDestroyView()) return
            progressBarTop.progress = i
        }

        override fun onJsAlert(webView: WebView, s: String, s1: String, jsResult: JsResult?): Boolean {
            ToastUtil.toastShort(s1)
            return false
        }
    }

    inner class NormalClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, s: String): Boolean {
            LogUtil.e(tag, "加载的url----》》" + s)
            val uri = Uri.parse(s)
            val scheme = uri.scheme
            var handled = false
            if (scheme != null)
                when (scheme) {
                    "http", "https" -> {
                    }
                    else -> try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
                        startActivity(intent)
                        handled = true
                    } catch (ignores: Exception) {
                        handled = false
                    }
                }
            return handled
        }

        override fun onReceivedError(webView: WebView, i: Int, s: String, s1: String) {
            LogUtil.e(tag, "页面加载失败")
        }

        override fun onPageFinished(webView: WebView, s: String) {
            LogUtil.e(tag, "页面加载完成")
            if (fragmentIsDestroyView()) return
            progressBarTop.visibility = View.GONE
        }

        override fun onPageStarted(webView: WebView, s: String, bitmap: Bitmap?) {
            LogUtil.e(tag, "页面加载开始:" + s)

        }
    }


    override fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return  super.onBackPressed()
    }



}