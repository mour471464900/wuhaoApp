package com.anfeng.wuhao.anfengkuaikan.ui.fragment.discover
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anfeng.game.ui.BaseFragment
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.bean.DiscoverBean
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.youth.banner.Banner


/**
 * 发现 推荐 页面
 */
class DiscoverRecommendFragment : BaseFragment() {

    private lateinit var mainView: View
    private lateinit var rvMain: LRecyclerView
    private lateinit var discoverList: Array<DiscoverBean.DataBean.InfosBean>
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater?.inflate(R.layout.fragment_discover_recommend, container, false)!!
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {

    }

    private fun initView() {
        rvMain = mainView.findViewById(R.id.rvMain) as LRecyclerView
        rvMain.adapter = DiscoverAdapter()
        rvMain.layoutManager = LinearLayoutManager(context)
        rvMain.setPullRefreshEnabled(false)
        rvMain.setLoadMoreEnabled(false)
    }

    inner class DiscoverAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            var data = discoverList[position]
            return  data.item_type
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
               if(holder is ItemBanner){
                   holder.banner
               }else if(holder is ItemTarget){

               }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            //加载Item View的时候根据不同TYPE加载不同的布局
           var viewHolder:RecyclerView.ViewHolder ?= null
           when(viewType){
               1->{
                   viewHolder=ItemBanner(View.inflate(context,R.layout.item_banner, parent))
               }
               13->{
                  viewHolder=ItemTarget(View.inflate(context,R.layout.item_target,parent))
               }
           }
            return  viewHolder!!
        }

        override fun getItemCount(): Int {
            return discoverList.size
        }

    }

    inner class ItemBanner(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            var banner = itemView?.findViewById(R.id.banner) as Banner
    }

    inner class ItemTarget(itemView: View?): RecyclerView.ViewHolder(itemView){
            var container =itemView?.findViewById(R.id.ll_target) as LinearLayout
    }

}