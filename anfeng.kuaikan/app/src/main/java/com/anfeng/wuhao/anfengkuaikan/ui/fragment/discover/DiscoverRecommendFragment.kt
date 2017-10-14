package com.anfeng.wuhao.anfengkuaikan.ui.fragment.discover

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.base.ListBaseAdapter
import com.anfeng.wuhao.anfengkuaikan.base.SuperViewHolder
import com.anfeng.wuhao.anfengkuaikan.bean.DiscoverBean
import com.anfeng.wuhao.anfengkuaikan.utils.*
import com.bumptech.glide.Glide
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.youth.banner.Banner
import java.util.*


@Suppress("SENSELESS_COMPARISON")
/**
 * 发现 推荐 页面
 */
class DiscoverRecommendFragment : BaseFragment() {

    companion object {
        val FILE_NAME = "discover.json"
    }

    private lateinit var mainView: View
    private lateinit var rvMain: RecyclerView
    private var discoverList: ArrayList<DiscoverBean.DataBean.InfosBean> = arrayListOf()
    private lateinit var discoverAdapter: DiscoverAdapter
    private lateinit var data: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater?.inflate(R.layout.fragment_discover_recommend, container, false)!!
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        rvMain = mainView.findViewById(R.id.rvMain) as RecyclerView
        discoverAdapter = DiscoverAdapter()
        rvMain.adapter = discoverAdapter
        rvMain.layoutManager = LinearLayoutManager(context)
    }

    override fun onHandleMessage(msg: Message?) {
        when (msg?.what) {
            0 -> {
                LogUtil.e(tag, "发现列表数据获取成功")
                data = AppUtil.getJson(context, FILE_NAME)
                if (TextUtils.isEmpty(data)) {
                    ToastUtil.toastShort("服务器异常")
                    return
                }
                val discoverBean = GsonUtils.GsonToBean(data, DiscoverBean::class.java)
                discoverList.addAll(discoverBean.data.infos)
                if (null != discoverList && discoverList.size > 0) {
                    discoverAdapter.notifyDataSetChanged()
                } else {
                    ToastUtil.toastShort("服务器异常")
                }
            }
        }
    }


    private fun initData() {
        handler.sendEmptyMessage(0)
    }


    inner class DiscoverAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            var data = discoverList[position]
            return data.item_type
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            when (holder) {
                is ItemBanner -> {
                    val banners = discoverList[position].banners
                    val urlList = banners.map { it.pic }
                    holder.banner.setImageLoader(BannerImageLoad()).setImages(urlList).start()
                }
                is ItemTarget -> {
                    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.weight = 1f
                    val banners = discoverList[position].banners
                    for (item in banners) {
                        val tagView = ImageView(context)
                        tagView.layoutParams = layoutParams
                        Glide.with(context).load(item.pic).placeholder(R.drawable.ic_common_placeholder_m).into(tagView)
                        holder.container.addView(tagView)
                    }
                }
                is ItemBottomBanner -> {
                    val bottomBanner = discoverList[position].banners
                    Glide.with(context).load(bottomBanner[0].pic).placeholder(R.drawable.ic_common_placeholder_m).into(holder.bottomBanner)
                }
                is ItemNormal -> {
                    val more_flag = discoverList[position].more_flag
                    val dataList = discoverList[position].topics
                    holder.title.text = discoverList[position].title
                    holder.more.visibility = if (more_flag) View.VISIBLE else View.GONE
                    var spanCount = 0
                    when {
                        dataList.size in 1..3 -> {
                            spanCount = 2
                        }
                        dataList.size > 4 -> {
                            spanCount = 3
                        }
                    }
                    if (spanCount > 0) {
                        holder.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
                    } else {
                        holder.recyclerView.layoutManager = GridLayoutManager(context, 2)
                    }
                    var innerAdapter = InnerAdapter(context)
                    holder.recyclerView.adapter = innerAdapter
                    innerAdapter.setDataList(dataList)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
            //加载Item View的时候根据不同TYPE加载不同的布局
            var viewHolder: RecyclerView.ViewHolder? = null
            val inflater = LayoutInflater.from(context)
            when (viewType) {
                1 -> viewHolder = ItemBanner(inflater.inflate(R.layout.item_banner, parent, false))
                13 -> viewHolder = ItemTarget(inflater.inflate(R.layout.item_target, parent, false))
                7 -> viewHolder = ItemBottomBanner(inflater.inflate(R.layout.item_bottem_banner, parent, false))
                else -> viewHolder = ItemNormal(inflater.inflate(R.layout.item_topic_normal, parent, false))
            }
            return viewHolder
        }

        override fun getItemCount(): Int {
            return discoverList.size
        }

    }

    inner class ItemBanner(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var banner = itemView?.findViewById(R.id.banner) as Banner
    }

    inner class ItemTarget(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var container = itemView?.findViewById(R.id.ll_target) as LinearLayout
    }

    inner class ItemBottomBanner(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var bottomBanner = itemView?.findViewById(R.id.iv_bottem) as ImageView
    }

    inner class ItemNormal(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title = itemView?.findViewById(R.id.tv_title) as TextView
        var more = itemView?.findViewById(R.id.tv_more) as TextView
        var recyclerView = itemView?.findViewById(R.id.rvNormal) as RecyclerView
    }

    inner class InnerAdapter(context: Context?) : ListBaseAdapter<DiscoverBean.DataBean.InfosBean.TopicsBean>(context) {
        override fun onBindItemHolder(holder: SuperViewHolder?, position: Int) {
            var realBean = dataList[position]
            var ivPic = holder?.getView<ImageView>(R.id.iv_cartoon)
            var tvName = holder?.getView<TextView>(R.id.tv_cartoon_name)
            var tvIntro = holder?.getView<TextView>(R.id.tv_cartoon_intro)
            Glide.with(context).load(realBean.pic).placeholder(R.drawable.ic_common_placeholder_m).into(ivPic)
            tvName?.text = realBean.title
            tvIntro?.text = realBean.recommended_text
        }

        override fun getLayoutId(): Int {
            return R.layout.item_summary
        }
    }
}