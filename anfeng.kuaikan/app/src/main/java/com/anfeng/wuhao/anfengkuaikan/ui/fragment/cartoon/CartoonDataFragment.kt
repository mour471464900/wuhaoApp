package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.adapter.DateListAdapter
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil

/**
 * Created by Administrator on 2017/9/14.
 */
class CartoonDataFragment(): BaseFragment() {
    //静态常量
    companion object {
        const val ID="id"
    }

    private var id :Long=0

    private lateinit var mDateAdapter:DateListAdapter

    private lateinit var mLRecyclerViewAdapter:LRecyclerViewAdapter

    private var actionBarAndNavVisible:Boolean =true

    private lateinit var parent: CartoonHotFragment

    @SuppressLint("ValidFragment")
    constructor(args: Bundle?) : this() {
        arguments = args
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //        将外部导入的bundle 传入到此fragment中去
        val bundle = arguments
        if (bundle != null) {
            id = bundle.getLong(ID, 0)
            LogUtil.e(tag, "获取上层传入的id：" + id)
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main,container,false)
    }

    fun requestData() {
        HttpHelper.getInstance().httpGetString(URLCommon.getDayUrl(id), context, object : RequestCallback<String> {
            override fun succeedOnResult(response: String) {
                val dateListBean = GsonUtils.GsonToBean(response, DateListBean::class.java)
                val comics = dateListBean.data.comics
                if (null != comics && comics.size > 0) {
                    mDateAdapter.setDataList(comics)
                } else {
                    errorForCode(HttpHelper.SYSTEM_ERROR)
                }
            }

            override fun errorForCode(code: Int) {
                 ToastUtil.toastShort("网络异常")
            }

        })

    }


    fun initView() {
        parent=parentFragment as CartoonHotFragment
        mDateAdapter = DateListAdapter(context)
        mLRecyclerViewAdapter = LRecyclerViewAdapter(mDateAdapter)
        val divider = DividerDecoration.Builder(context)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.af_cededed)
                .build()
        rv_main.adapter = mLRecyclerViewAdapter
        rv_main.addItemDecoration(divider)
        rv_main.layoutManager = LinearLayoutManager(context)
        //禁用下拉刷新功能
        rv_main.setPullRefreshEnabled(false)
        //禁用自动加载更多功能
        rv_main.setLoadMoreEnabled(false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        requestData()
    }

}