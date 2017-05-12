package com.anfeng.wuhao.anfengkuaikan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.adapter.DateListAdapter;
import com.anfeng.wuhao.anfengkuaikan.base.BaseFragment;
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean;
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper;
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon;
import com.anfeng.wuhao.anfengkuaikan.view.ConditionView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;

import static com.anfeng.wuhao.anfengkuaikan.view.ConditionView.DATA_NORMAL;

/**
 * 作者： ${USER}
 * 时间： 2017/4/28.
 * 描述：
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.rv_main)
    LRecyclerView mRvMain;
    @BindView(R.id.cv_main)
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

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void requestData() {
        initView();
        HttpHelper.getInstance().httpGetString(URLCommon.getDayUrl(id), getContext(), new RequestCallback<String>() {
            @Override
            public void succeedOnResult(String response) {
                DateListBean dateListBean = GsonUtils.GsonToBean(response, DateListBean.class);
                List<DateListBean.DataBean.ComicsBean> comics = dateListBean.getData().getComics();
                mDateAdapter.setDataList(comics);
            }
        });
    }

    private void initView() {
        mConditionView.setDataCondition(DATA_NORMAL);
        mDateAdapter = new DateListAdapter(getContext());
        mLRecyclerViewAdapter=new LRecyclerViewAdapter(mDateAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.af_c9E9E9E)
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
//        int red = (int) (Math.random() * 256);
//        int green = (int) (Math.random() * 256);
//        int blue = (int) (Math.random() * 256);
//        int rgb = Color.rgb(red, green, blue);
//        LogUtil.e(getTag(), "当前的颜色值为：" + rgb);
//        mLlMain.setBackgroundColor(rgb);
    }
}
