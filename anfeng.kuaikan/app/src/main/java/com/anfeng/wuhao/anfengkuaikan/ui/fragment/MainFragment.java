package com.anfeng.wuhao.anfengkuaikan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anfeng.game.ui.BaseFragment;
import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.adapter.DateListAdapter;
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean;
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper;
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon;
import com.anfeng.wuhao.anfengkuaikan.ui.view.ConditionView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.List;
import static com.anfeng.wuhao.anfengkuaikan.ui.view.ConditionView.CONTAINER;
import static com.anfeng.wuhao.anfengkuaikan.ui.view.ConditionView.DATA_EMPTY;
import static com.anfeng.wuhao.anfengkuaikan.ui.view.ConditionView.DATA_LOADING;
import static com.anfeng.wuhao.anfengkuaikan.ui.view.ConditionView.DATA_RETRY;

/**
 * 作者： 吴浩
 * 时间： 2017/4/28.
 * 描述：
 */


public class MainFragment extends BaseFragment implements ConditionView.OnRetryListener{
    LinearLayout mLlMain;
    LRecyclerView mRvMain;
    ConditionView mConditionView;
    //  时间戳ID
    private long id;
    // 传递的常量
    public static final String ID = "id";
    private DateListAdapter mDateAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;



    public static MainFragment getNewInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        将外部导入的bundle 传入到此fragment中去
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong(ID, 0);
            LogUtil.e(getTag(), "获取上层传入的id：" + id);
        }
    }



    @org.jetbrains.annotations.Nullable
    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }



    public void requestData() {
        HttpHelper.getInstance().httpGetString(URLCommon.getDayUrl(id), getContext(), new RequestCallback<String>() {
            @Override
            public void succeedOnResult(String response) {
                DateListBean dateListBean = GsonUtils.GsonToBean(response, DateListBean.class);
                List<DateListBean.DataBean.ComicsBean> comics = dateListBean.getData().getComics();
                if(null!=comics&&comics.size()>0){
                    mDateAdapter.setDataList(comics);
                    mConditionView.setDataCondition(CONTAINER);
                }else {
                    errorForCode(HttpHelper.SYSTEM_ERROR);
                }
            }

            @Override
            public void errorForCode(int code) {
                if(code==HttpHelper.NET_ERROR){ // 网络异常就有重试按钮
                    mConditionView.setDataCondition(DATA_RETRY);
                }else if (code==HttpHelper.SYSTEM_ERROR){
                    mConditionView.setDataCondition(DATA_EMPTY);
                }
            }

        });

    }



    protected void initView() {
        mLlMain= (LinearLayout) getActivity().findViewById(R.id.ll_main);
        mRvMain= (LRecyclerView) getActivity().findViewById(R.id.rv_main);
        mConditionView.setDataCondition(DATA_LOADING);
        mDateAdapter = new DateListAdapter(getContext());
        mLRecyclerViewAdapter=new LRecyclerViewAdapter(mDateAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.af_cededed)
                .build();
        mRvMain.setAdapter(mLRecyclerViewAdapter);
        mRvMain.addItemDecoration(divider);
        mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
        //禁用下拉刷新功能
        mRvMain.setPullRefreshEnabled(false);
        //禁用自动加载更多功能
        mRvMain.setLoadMoreEnabled(false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        requestData();
    }

    @Override
    public void onRetry() { // 点击重试之后视图变为加载状态
         mConditionView.setDataCondition(DATA_LOADING);
         requestData();
    }


}
