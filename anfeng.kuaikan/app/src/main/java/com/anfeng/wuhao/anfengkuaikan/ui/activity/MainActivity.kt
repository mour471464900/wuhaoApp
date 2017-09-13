package com.anfeng.wuhao.anfengkuaikan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.homepager.HomeFragment
import com.anfeng.wuhao.anfengkuaikan.utils.KotlinUtil

/**
 *
 */
open class MainActivity :AppCompatActivity() {
     var fragment: BaseFragment? = null
        set(value) {
            field = value
            replaceFragment()
            if (isResumed)
                field?.userVisibleHint = true
            field?.arguments = arguments
        }

    private var arguments: Bundle? = null
    private var isResumed: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_app)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        arguments = intent?.extras
        //fragment?.arguments = arguments
        fragment?.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        isResumed = true
        fragment?.userVisibleHint = true
    }

    override fun onPause() {
        super.onPause()
        isResumed = false
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentApp, fragment, fragment!!::class.java.simpleName)
                .show(fragment)
                .commit()
    }

    override fun onBackPressed() {
        if (!fragment!!.onBackPressed())
            super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return fragment!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    class  HomeActivity:MainActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            fragment= HomeFragment()
        }
    }
}