package com.anfeng.game.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.anfeng.wuhao.anfengkuaikan.R
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


abstract class BaseFragment : Fragment() {
    companion object {
        val STATUS_EMPTY = "x1"
        val STATUS_NO_EXISTS = "x2"
    }
    protected lateinit var activity: AppCompatActivity
    private var loadLayout: ViewGroup? = null
    private var loadingDialog: Dialog? = null
    private var isCallSetUserVisibleHint = false
    private var isCallOnActivityCreated = false
    var baseView:View? = null
    lateinit var  loadService: LoadService<*>
    protected val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (fragmentIsDestroyView())
                return
            onHandleMessage(msg)
        }
    }

    protected var isPaused = false

    open fun onBackPressed(): Boolean {
        return false
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }



    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseView= inflater?.inflate(getContentView(),container,false)
        loadService=LoadSir.getDefault().register(baseView,onReloadLister())
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNet()
    }


    inner class onReloadLister: Callback.OnReloadListener{
        override fun onReload(p0: View?) {
            onNetReload(p0)
        }
    }

    open fun onNetReload(p0:View?){

    }

    open fun loadNet(){

    }

    /**
     *  使用该方法时就可以使用 loadSir
     */
    open  fun getContentView(): Int{
        return 0
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCallOnActivityCreated = true
        activity = context as AppCompatActivity
        loadLayout = view?.findViewById(R.id.loadLayout) as ViewGroup?
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isCallSetUserVisibleHint = true
        }
    }



    open fun onFirstDraw() {

    }

    open fun onCreateActionError(code: String, msg: String, type: String) {

    }

    fun loading() {
        setLoadUiVisible(View.GONE, View.VISIBLE)
    }

    fun loadCompleted(canAnimate: Boolean = true) {
        loadingDialog?.dismiss()
        if (loadLayout != null && loadLayout?.visibility == View.GONE) {
            return
        }
        setLoadUiVisible(View.GONE, View.GONE, View.GONE)
        if (loadLayout != null && canAnimate) {
            val view = loadLayout?.parent as View
            view.alpha = 0f
            view.animate().alpha(1f).setDuration(600).start()
        }
    }

    fun loadError(code: String, primaryText: String = "什么也没有~", subText: String = "", buttonVisible: Int = View.GONE) {
        setLoadUiVisible(View.VISIBLE, View.GONE)
        if(loadLayout==null) return
        val tipImage = loadLayout?.findViewById(R.id.tipImage) as ImageView
        val tipText = loadLayout?.findViewById(R.id.tipText) as TextView
        val subTipText = loadLayout?.findViewById(R.id.subTipText) as TextView
        val reload = loadLayout?.findViewById(R.id.reload) as TextView
        when (code) {
            STATUS_EMPTY -> {
                tipImage.setImageResource(R.drawable.image_none)
                tipText.text = primaryText
                subTipText.text = subText
                reload.visibility = buttonVisible
            }
            STATUS_NO_EXISTS -> {
                tipImage.setImageResource(R.drawable.image_nopage)
                tipText.text = "页面不存在"
                subTipText.text = ""
                reload.visibility = buttonVisible
            }
            else -> {
                tipImage.setImageResource(R.drawable.image_loadfail)
                tipText.text = "加载失败"
                subTipText.text = ""
                reload.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoadUiVisible(errorVisible: Int, loadingVisible: Int, loadlayoutVisible: Int = View.VISIBLE) {
        if (loadLayout == null) return
        val loadError = loadLayout?.findViewById(R.id.loadError)
        val loading = loadLayout?.findViewById(R.id.loading)
        loadLayout?.visibility = loadlayoutVisible
        loading?.visibility = loadingVisible
        loadError?.visibility = errorVisible
    }

    open fun onFinish() {

    }

    fun safeGetArguments(key: String): Any? {
        if (arguments == null) return null
        else return arguments[key]
    }

    open fun onNewIntent(intent: Intent?) {

    }


    open protected fun onHandleMessage(msg: Message?) {

    }

    fun fragmentIsDestroyView(): Boolean {
        return view == null
    }

    override fun onResume() {
        super.onResume()
        isPaused = false
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }
}
