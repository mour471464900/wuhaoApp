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
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.anfeng.game.utils.Dialogs
import com.anfeng.wuhao.anfengkuaikan.R


abstract class BaseFragment : Fragment() {
    companion object {
        val STATUS_EMPTY = "x1"
        val STATUS_NO_EXISTS = "x2"
    }

    protected lateinit var activity: AppCompatActivity
    private var isDraw = false
    private var loadLayout: ViewGroup? = null
    private var loadingDialog: Dialog? = null
    private var updateDialog: Dialog? = null
    private var isCallSetUserVisibleHint = false
    private var isCallOnActivityCreated = false
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

    private var accountExceptionDialog: Dialog? = null
    open fun onBackPressed(): Boolean {
        return false
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val main = onCreateNetWrapperView(inflater, container, savedInstanceState) ?: return null
        val layout = FrameLayout(context)
        layout.addView(main)
        layout.addView(inflater?.inflate(R.layout.loading_layout, container, false) as ViewGroup?)
        return layout
    }

    open fun onCreateNetWrapperView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCallOnActivityCreated = true
        activity = context as AppCompatActivity

        handler.post { callOnFirstDraw() }
        loadLayout = view?.findViewById(R.id.loadLayout) as ViewGroup?

        onLoadErrorListener {
            onFirstDraw()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isCallSetUserVisibleHint = true
            handler.post { callOnFirstDraw() }
        }
    }

    private fun callOnFirstDraw() {
        if (isCallOnActivityCreated && isCallSetUserVisibleHint && !isDraw) {
            onFirstDraw()
            isDraw = true
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

    fun showLoadingDialog(desc: String):Dialog? {
        loadingDialog?.dismiss()
        Dialogs.showLoadingDialog(context, desc) {
            loadingDialog = it
        }
        return loadingDialog
    }

    fun onLoadErrorListener(call: () -> Unit) {
        if (loadLayout == null) return
        val reload = loadLayout?.findViewById(R.id.reload)
        reload?.setOnClickListener {
            call()
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

//    fun userExit() {
//        val intent = Intent(context, GameActivity.UserLoginActivity::class.java)
//        if (GameApp.user != null)
//            intent.putExtra(UserLoginFragment.USER_NAME, GameApp.user?.userName)
//        GameApp.token = null
//        GameApp.user = null
//        startActivity(intent)
//    }

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

//    fun showAEDialog(context: Context) {
//        accountExceptionDialog?.dismiss()
//        Dialogs.showAccountExceptionDialog(context) {
//            accountExceptionDialog = it
//        }
//    }

//    fun showUpdateDialog(context: Context, model: UpdateModel) {
//        updateDialog?.dismiss()
//        Dialogs.showUpdateDialog(context, model) {
//            updateDialog = it
//        }
//    }

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
