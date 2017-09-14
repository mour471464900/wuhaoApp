package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

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
import kotlinx.android.synthetic.main.fragment_cartoon.*

/**
 *   漫画
 */
class CartoonFragment:BaseFragment() {

    private  var fragments = arrayOf(CartoonCareFragment(),CartoonHotFragment())

    private var currItem:Int=0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_cartoon,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.adapter=CartoonAdapter(childFragmentManager)
        viewPager.addOnPageChangeListener(CartoonPagerListener())
        for(i in 0 until radioTop.childCount){
            radioTop.getChildAt(i).setOnClickListener(tabClick)
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

}