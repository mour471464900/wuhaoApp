package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import kotlinx.android.synthetic.main.fragment_cartoon_hot.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2017/9/13.
 */
class CartoonHotFragment:BaseFragment() {


    private val urlNum = longArrayOf((Date().time - 24 * 60 * 60 * 1000 * 7) / 1000, (Date().time - 24 * 60 * 60 * 1000 * 6) / 1000, (Date().time - 24 * 60 * 60 * 1000 * 5) / 1000, (Date().time - 24 * 60 * 60 * 1000 * 4) / 1000, (Date().time - 24 * 60 * 60 * 1000 * 3) / 1000, 1, 0)//           ，动态1      ,动态2,         动态3,       动态4,      动态5    ，昨天   今天
    //    周一到  周日  让 title 显示不同天数的数组
    private val day1 = arrayOf("周二", "周三", "周四", "周五", "周六", "昨天", "今天")
    private val day2 = arrayOf("周三", "周四", "周五", "周六", "周日", "昨天", "今天")
    private val day3 = arrayOf("周四", "周五", "周六", "周日", "周一", "昨天", "今天")
    private val day4 = arrayOf("周五", "周六", "周日", "周一", "周二", "昨天", "今天")
    private val day5 = arrayOf("周六", "周日", "周一", "周二", "周三", "昨天", "今天")
    private val day6 = arrayOf("周日", "周一", "周二", "周三", "周四", "昨天", "今天")
    private val day7 = arrayOf("周一", "周二", "周三", "周四", "周五", "昨天", "今天")
    //-------------------------------------
    private var fragmentList: List<BaseFragment> = ArrayList()
    //    fragment 的集合
    private var titleList: List<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate( R.layout.fragment_cartoon_hot,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        titleList=initTitle()
        fragmentList=initFragment()
        viewPager.adapter=CartoonAdapter(childFragmentManager)
        viewPager.offscreenPageLimit=1
        detailTabs.setupWithViewPager(viewPager)
        viewPager.setCurrentItem(fragmentList.size,false)
    }

    inner class CartoonAdapter(manger: FragmentManager): FragmentPagerAdapter(manger){
        override fun getItem(position: Int): Fragment {
            return  fragmentList[position]
        }

        override fun getCount(): Int {
            return  fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titleList[position]
        }

    }

    //       返回标题的集合
    private fun initTitle(): List<String> {
        val myFmt = SimpleDateFormat("E", Locale.CHINESE)
        val date = Date()
        val now = myFmt.format(date)
        //        匹配今天是星期几，然后动态加入星期几
        //        有的机型是周几 ，三星的机型是星期几
        val mList = ArrayList<String>()
        when (now) {
            "星期一" -> mList.addAll(getDay(day1))
            "星期二" -> mList.addAll(getDay(day2))
            "星期三" -> mList.addAll(getDay(day3))
            "星期四" -> mList.addAll(getDay(day4))
            "星期五" -> mList.addAll(getDay(day5))
            "星期六" -> mList.addAll(getDay(day6))
            "星期日" -> mList.addAll(getDay(day7))
        //            此处应该动态用英文来判断
            "周一" -> mList.addAll(getDay(day1))
            "周二" -> mList.addAll(getDay(day2))
            "周三" -> mList.addAll(getDay(day3))
            "周四" -> mList.addAll(getDay(day4))
            "周五" -> mList.addAll(getDay(day5))
            "周六" -> mList.addAll(getDay(day6))
            "周日" -> mList.addAll(getDay(day7))
        }//
        return mList
    }

    //     动态将数组加入title里面去
    private fun getDay(days: Array<String>): ArrayList<String> {
        val dlist = ArrayList<String>()
        for (i in days.indices) {
            dlist.add(days[i])
        }
        return dlist
    }


    //    将id 动态的传到每个fragment里面去然后动态的取id
    private fun initFragment(): List<BaseFragment> {
        val mList = ArrayList<BaseFragment>()
        for (i in urlNum.indices) {
            val bundle = Bundle()
            bundle.putLong("id", urlNum[i])
            val fragment =CartoonDataFragment(bundle)
            mList.add(fragment)
        }
        return mList
    }

    fun getTopView():View{
        return  (parentFragment as CartoonFragment).getTopView()
    }
}