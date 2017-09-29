package com.anfeng.wuhao.anfengkuaikan.ui.fragment.homepager

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon.CartoonFragment
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.discover.DiscoverFragment
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.feed.FeedFragment
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.me.MineFragment
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *
 */
class HomeFragment : com.anfeng.game.ui.BaseFragment() {


    private var lastBackTime = 0L
    private var tabPosition = 0
    val fragments= arrayOf(CartoonFragment(),DiscoverFragment(),FeedFragment(),MineFragment())
    val titles = arrayOf("漫画","发现","V社区","我的")

    override fun getContentView(): Int {
       return  R.layout.fragment_home
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.adapter= HomeAdapter(childFragmentManager)
        viewPager.offscreenPageLimit=3
        val count=homeTabs.childCount
        for(i in 0 until count){
            homeTabs.getChildAt(i).setOnClickListener(tabClick)
        }
    }

    inner class HomeAdapter(manger:FragmentManager) :FragmentPagerAdapter(manger){
        override fun getItem(position: Int): Fragment {
               return  fragments[position]
        }

        override fun getCount(): Int {
           return  fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }

    private var tabClick={ v:View ->
        val position=homeTabs.indexOfChild(v)
        tabPosition=position
        viewPager.setCurrentItem(tabPosition,false)
        updateTabsStyle(tabPosition)
    }

    private fun updateTabsStyle(position: Int) {
        val count = homeTabs.childCount
        (0 until count)
                .filter { position != it }
                .forEach {
                    (homeTabs.getChildAt(it) as RadioButton).isChecked = false
                }
        (homeTabs.getChildAt(position) as RadioButton).isChecked = true
    }

    override fun onBackPressed(): Boolean {
        if (!fragments[tabPosition].onBackPressed()) {
            val curBackTime = SystemClock.uptimeMillis()
            if (lastBackTime!=0L&&curBackTime - lastBackTime < 2000) {
                return false
            } else {
                ToastUtil.toastShort("再按一次退出程序")
                lastBackTime = curBackTime
                return true
            }
        }
        return true
    }

}