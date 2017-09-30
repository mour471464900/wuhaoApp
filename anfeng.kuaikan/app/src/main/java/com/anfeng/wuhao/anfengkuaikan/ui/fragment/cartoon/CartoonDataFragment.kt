package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.adapter.DateListAdapter
import com.anfeng.wuhao.anfengkuaikan.base.ListBaseAdapter
import com.anfeng.wuhao.anfengkuaikan.base.SuperViewHolder
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper
import com.anfeng.wuhao.anfengkuaikan.ui.activity.MainActivity
import com.anfeng.wuhao.anfengkuaikan.ui.fragment.web.WebFragment
import com.anfeng.wuhao.anfengkuaikan.ui.view.AppImage
import com.anfeng.wuhao.anfengkuaikan.utils.*
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.ErrorCallback
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.LoadingCallback
import com.anfeng.wuhao.anfengkuaikan.utils.loadcallback.TimeoutCallback
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.LRecyclerView
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by Administrator on 2017/9/14.
 */
class CartoonDataFragment() : BaseFragment() {


    //静态常量
    companion object {
        const val ID = "id"
    }

    private var id: Long = 0

    private lateinit var mDateAdapter: DateListAdapter

    private lateinit var mLRecyclerViewAdapter: LRecyclerViewAdapter

    private var actionBarAndNavVisible: Boolean = true

    private lateinit var parent: CartoonHotFragment

    private lateinit var rvMain: LRecyclerView

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

    override fun getContentView(): Int {
        return R.layout.fragment_main
    }


    fun requestData() {
        HttpHelper.getInstance().httpGetString(URLCommon.getDayUrl(id), context, object : RequestCallback<String> {
            override fun succeedOnResult(response: String) {
                val dateListBean = GsonUtils.GsonToBean(response, DateListBean::class.java)
                val comics = dateListBean.data.comics
                if (null != comics && comics.size > 0) {
                    mDateAdapter.setDataList(comics)
                    loadCompleted()
                    PostUtil.postSuccessDelayed(loadService, PostUtil.DELAY_TIME)
                } else {
                    errorForCode(HttpHelper.SYSTEM_ERROR)
                    PostUtil.postCallbackDelayed(loadService, ErrorCallback().javaClass)
                }
            }

            override fun errorForCode(code: Int) {
                PostUtil.postCallbackDelayed(loadService, TimeoutCallback().javaClass)
                loadError("" + code)
            }
        })
    }


    override fun onNetReload(p0: View?) {
        loadService.showCallback(LoadingCallback().javaClass)
        requestData()
    }


    fun initView() {
        parent = parentFragment as CartoonHotFragment
        mDateAdapter = DateListAdapter(context)
        mLRecyclerViewAdapter = LRecyclerViewAdapter(mDateAdapter)
        val divider = DividerDecoration.Builder(context)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.af_cededed)
                .build()
        rvMain = baseView?.findViewById(R.id.rv_main) as LRecyclerView
        rvMain.adapter = mLRecyclerViewAdapter
        rvMain.addItemDecoration(divider)
        rvMain.layoutManager = LinearLayoutManager(context)
        //禁用下拉刷新功能
        rvMain.setPullRefreshEnabled(false)
        //禁用自动加载更多功能
        rvMain.setLoadMoreEnabled(false)
    }

    override fun onFirstDraw() {
        loading()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        requestData()
    }

    inner class  DateListAdapter(context: Context?) : ListBaseAdapter<DateListBean.DataBean.ComicsBean>(context) {
        override fun getLayoutId(): Int {
            return R.layout.item_seven_fragment_listview
        }

        override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
            val bean = dataList[position]
            val lable = holder.getView<TextView>(R.id.tv_seven_lable)
            val top_title = holder.getView<TextView>(R.id.tv_seven_top_title)
            val top_avatar = holder.getView<TextView>(R.id.tv_seven_top_avatar)
            val bottom_title = holder.getView<TextView>(R.id.tv_seven_bottom_title)
            val dianzhan = holder.getView<CheckBox>(R.id.tv_seven_dianzhan)
            val pinlun = holder.getView<CheckBox>(R.id.tv_seven_pinlun)
            val cover = holder.getView<AppImage>(R.id.iv_seven_cover)
            //            改变Ui控件
            lable.text = bean.label_text // 改变文字
            //             改变颜色
            val background = lable.background
            //bean.getLabel_color()这是一个十六进制的颜色配置    "label_color":"#fa6499",
            background.setColorFilter(Color.parseColor(bean.label_color), PorterDuff.Mode.SRC)
            //这个是颜色过滤器，来改变drawable 的颜色
            top_title.text = bean.topic.title
            bottom_title.text = bean.title
            dianzhan.text = "" + bean.likes_count
            pinlun.text = "" + bean.comments_count
            ImageLoad.getInstance().setImageURI(cover, bean.cover_image_url)
            top_avatar.setOnClickListener {
                val intent=Intent(activity,MainActivity.WebActivity::class.java).putExtra(WebFragment.URL,bean.url)
                startActivity(intent)
            }
        }
    }

}