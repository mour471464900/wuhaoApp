package com.anfeng.wuhao.anfengkuaikan.ui.fragment.discover

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon.SearchActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_discover.*


/**
 *  发现
 */
class DiscoverFragment: BaseFragment() {
    private  var fragments = arrayOf(DiscoverRecommendFragment(), DiscoverClassifyFragment())

    private var currItem:Int=0
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate( R.layout.fragment_discover,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.adapter=CartoonAdapter(childFragmentManager)
        viewPager.addOnPageChangeListener(CartoonPagerListener())
        viewPager.setCurrentItem(0,false)
        for(i in 0 until radioTop.childCount){
            radioTop.getChildAt(i).setOnClickListener(tabClick)
        }
        RxView.clicks(search).subscribe {
            var intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private var tabClick={ v:View ->
        val position=radioTop.indexOfChild(v)
        currItem=position
        viewPager.setCurrentItem(currItem,false)
        updateTabsStyle(currItem)
    }

    private fun updateTabsStyle(position: Int) {
        val count = radioTop.childCount
        (0 until count)
                .filter { position != it }
                .forEach {
                    (radioTop.getChildAt(it) as RadioButton).isChecked = false
                }
        (radioTop.getChildAt(position) as RadioButton).isChecked = true
    }

    inner class CartoonAdapter(manger: FragmentManager): FragmentPagerAdapter(manger){
        override fun getItem(position: Int): Fragment {
            return  fragments[position]
        }

        override fun getCount(): Int {
            return  fragments.size
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            var beginTransaction = childFragmentManager.beginTransaction()
            beginTransaction.remove(`object` as Fragment?)
            beginTransaction.commit()
        }
    }

    inner class CartoonPagerListener: ViewPager.OnPageChangeListener{
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            if(currItem !=position){
                updateTabsStyle(position)
            }
            currItem=position
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    fun getTopView():View{
        return topView
    }
}