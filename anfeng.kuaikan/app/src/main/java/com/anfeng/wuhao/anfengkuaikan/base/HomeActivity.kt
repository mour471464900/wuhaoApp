package com.anfeng.wuhao.anfengkuaikan.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.event.KotlinUtil
import com.anfeng.wuhao.anfengkuaikan.fragment.SearchFragment
import com.anfeng.wuhao.anfengkuaikan.fragment.SecondFragment

/**
 * Created by Administrator on 2017/8/22.
 */
open class HomeActivity :AppCompatActivity() {
    protected var fragment:Fragment?= null
    set(value) {
        field=value
        replaceFragment()
        if(isResume){
            field?.userVisibleHint=true
            field?.arguments=arguments
        }
    }
    var  isResume :Boolean = false
    var  arguments: Bundle?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        arguments=intent.extras
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        arguments=intent?.extras
    }

    override fun onResume() {
        super.onResume()
        isResume=true
        fragment?.userVisibleHint=true
    }

    override fun onPause() {
        super.onPause()
        isResume=false
    }

    fun replaceFragment(){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container,fragment,fragment!!::class.java.simpleName)
                .show(fragment)
                .commit()
    }

    class SearchActivity:HomeActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            KotlinUtil.setTransparencyStausBar(this)
            fragment= SearchFragment()
        }
    }

    class  SecondActivity: HomeActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            KotlinUtil.setTransparencyStausBar(this)
            fragment= SecondFragment()
        }
    }

}