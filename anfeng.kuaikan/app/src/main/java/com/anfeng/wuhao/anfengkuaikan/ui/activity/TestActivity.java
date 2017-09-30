package com.anfeng.wuhao.anfengkuaikan.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.adapter.DateListAdapter;
import com.anfeng.wuhao.anfengkuaikan.base.BaseActivity;
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean;
import com.anfeng.wuhao.anfengkuaikan.bean.UserInfo;
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper;
import com.anfeng.wuhao.anfengkuaikan.utils.AppUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon;
import com.anfeng.wuhao.anfengkuaikan.ui.view.FloatingBall;
import com.anfeng.wuhao.anfengkuaikan.ui.view.LoadingFrameView;
import com.anfeng.wuhao.anfengkuaikan.ui.view.StateTextView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.lzy.okgo.OkGo.getContext;


/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/17.
 * 描述： 测试FrameView的视图的Activity
 * =============================
 */

public class TestActivity extends BaseActivity implements OnRefreshListener ,LoadingFrameView.OnRetryListener, OnItemClickListener {

    LRecyclerView mRvMain;
    LoadingFrameView mFrameView;
    StateTextView mLlSearch;
    private DateListAdapter mDateAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private FloatingBall floatingBall;
    @Override
    public int setContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public void requestData() {
        HttpHelper.getInstance().httpGetString(URLCommon.getDayUrl(0), getContext(), new RequestCallback<String>() {
            @Override
            public void succeedOnResult(String response) {
                LogUtil.e(getTag(),"回调成功,网络请求或者读取缓存成功");
                mRvMain.refreshComplete(0);
                DateListBean dateListBean = GsonUtils.GsonToBean(response, DateListBean.class);
                List<DateListBean.DataBean.ComicsBean> comics = dateListBean.getData().getComics();
                if(AppUtil.isListEmpty(comics)){
                     mDateAdapter.setDataList(comics);
                     mFrameView.setContainerShown(true);
                }else {
                    LogUtil.e(getTag(),"数据解析异常");
                    errorForCode(HttpHelper.SYSTEM_ERROR);
                }
            }

            @Override
            public void errorForCode(int code) {
                LogUtil.e(getTag(),"请求失败,网络异常或者服务器响应异常");
                if(code==HttpHelper.NET_ERROR){ // 网络异常就有重试按钮
                    mFrameView.setRetryCondition(TestActivity.this);
                }else if (code==HttpHelper.SYSTEM_ERROR){
                    mFrameView.setEmptyShown(true);
                }
            }

        });
    }

    @Override
    protected void initView() {
        mRvMain= (LRecyclerView) findViewById(  R.id.rv_main);
        mFrameView= (LoadingFrameView) findViewById(  R.id.frame_view);
        mLlSearch= (StateTextView) findViewById( R.id.search);
        mFrameView.setProgressShown(true);
        mDateAdapter = new DateListAdapter(getContext());
        mLRecyclerViewAdapter=new LRecyclerViewAdapter(mDateAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.af_cededed)
                .build();
        mRvMain.setAdapter(mLRecyclerViewAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mRvMain.addItemDecoration(divider);
        }
        mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
        //启用下拉刷新
        mRvMain.setPullRefreshEnabled(true);
        //禁用自动加载更多功能
        mRvMain.setLoadMoreEnabled(false);
        mRvMain.setOnRefreshListener(this);
        mLRecyclerViewAdapter.setOnItemClickListener(this);
        showShortToast("当前屏幕的宽高："+AppUtil.getDisplayWidth(this)+"*"+AppUtil.getDisplayHeight(this));
        floatingBall=new FloatingBall(this);// 初始化就可以附着当前的Activity中
         UserInfo userInfo=new UserInfo("吴浩","mour471464900");
        boolean save = userInfo.save();
        LogUtil.e(getTag(),"wuhao用户插入成功"+save);
        List<UserInfo> list= DataSupport.findAll(UserInfo.class);
        if(list!=null&&list.size()>0){
            LogUtil.e(getTag(),"用户名"+list.get(0).getUsername()+"用户token"+list.get(0).getToken());
        }
    }

    @Override
    public void onRefresh() {
        mDateAdapter.clear();
        mLRecyclerViewAdapter.notifyDataSetChanged();
        requestData();
    }

    @Override
    public void onRetryListener() {
        mFrameView.setProgressShown(true);
        requestData();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(floatingBall!=null){
            floatingBall.remove();
        }
    }
}
