package com.anfeng.wuhao.anfengkuaikan.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.base.BaseActivity;
import com.anfeng.wuhao.anfengkuaikan.bean.BannerBean;
import com.anfeng.wuhao.anfengkuaikan.fragment.MainFragment;
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback;
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper;
import com.anfeng.wuhao.anfengkuaikan.utils.BannerImageLoad;
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils;
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil;
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon;
import com.anfeng.wuhao.anfengkuaikan.view.AppViewPage;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnBannerListener {

    @BindView(R.id.banner_main)
    Banner mBannerMain;
    @BindView(R.id.tab_main)
    TabLayout mTabMain;
    @BindView(R.id.vp_main)
    AppViewPage mVpMain;
    private long  [] urlNum = {
            (new Date().getTime()-24*60*60*1000*7)/1000,
            (new Date().getTime()-24*60*60*1000*6)/1000,
            (new Date().getTime()-24*60*60*1000*5)/1000,
            (new Date().getTime()-24*60*60*1000*4)/1000,
            (new Date().getTime()-24*60*60*1000*3)/1000,
            1,    0
//           ，动态1      ,动态2,         动态3,       动态4,      动态5    ，昨天   今天
    };
    //    周一到  周日  让 title 显示不同天数的数组
    private String[] day1={"周二","周三","周四","周五","周六","昨天","今天"};
    private String[] day2={"周三","周四","周五","周六","周日","昨天","今天"};
    private String[] day3={"周四","周五","周六","周日","周一","昨天","今天"};
    private String[] day4={"周五","周六","周日","周一","周二","昨天","今天"};
    private String[] day5={"周六","周日","周一","周二","周三","昨天","今天"};
    private String[] day6={"周日","周一","周二","周三","周四","昨天","今天"};
    private String[] day7={"周一","周二","周三","周四","周五","昨天","今天"};
    //-------------------------------------
    private List<Fragment> fragmentList = new ArrayList<>();
    //    fragment 的集合
    private List<String> titleList = new ArrayList<>();

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void requestData() {
        HttpHelper.getInstance().httpGetString(URLCommon.URL_BANNER, MainActivity.this, new RequestCallback<String>() {

            @Override
            public void succeedOnResult(String response) {
                try {
                    BannerBean bannerBean = GsonUtils.GsonToBean(response, BannerBean.class);
                    LogUtil.e(getTag(), "广告实体类：" + bannerBean.toString());
                    if (null != bannerBean) {
                        List<String> urlString = new ArrayList<>();
                        for (int i = 0; i < bannerBean.getData().getBanner_group().size(); i++) {
                            urlString.add(bannerBean.getData().getBanner_group().get(i).getPic());
                        }
                        mBannerMain.setImageLoader(new BannerImageLoad()).setImages(urlString).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorForCode(int code) {

            }
        });
    }

    @Override
    protected void initView() {
        mBannerMain.setOnBannerListener(this);
        initData();
        initTableList();
        //        设置tablayout 到最后一个位置
        mVpMain.setDate(getSupportFragmentManager(),fragmentList,titleList);
        mTabMain.setupWithViewPager(mVpMain);
//        将viewpager关联起来
        mVpMain.setCurrentItem(urlNum.length);
//        设置viewpager的设置选中项
//        刚进入的设置到 tab 到:今天，
    }

    private void initTableList() {
        for (int i = 0; i < titleList.size(); i++) {
            //创建Tab:mTabLayout.newTab()
            //设置Tab内容:tab.setText(内容);
            mTabMain.addTab(mTabMain.newTab().setText(titleList.get(i)));
        }
    }
    private void initData() {
        fragmentList=initFragment();
//        设置fragment
        titleList=initTitle();
//        设置标题的集合
    }

    @Override
    public void OnBannerClick(int position) {
        showShortToast(R.string.son_account_id);
    }

    //       返回标题的集合
    private List<String> initTitle() {
        SimpleDateFormat myFmt=new SimpleDateFormat("E", Locale.CHINESE);
        Date date=new Date();
        String now = myFmt.format(date);
//        匹配今天是星期几，然后动态加入星期几
//        有的机型是周几 ，三星的机型是星期几
        List<String> mList=new ArrayList<>();
        switch (now){
            case "星期一":
                mList.addAll(getDay(day1));
                break;
            case "星期二":
                mList.addAll(getDay(day2));
                break;
            case "星期三":
                mList.addAll(getDay(day3));
                break;
            case "星期四":
                mList.addAll(getDay(day4));
                break;
            case "星期五":
                mList.addAll(getDay(day5));
                break;
            case "星期六":
                mList.addAll(getDay(day6));
                break;
            case "星期日":
                mList.addAll(getDay(day7));
                break;
//            此处应该动态用英文来判断
            case "周一":
                mList.addAll(getDay(day1));
                break;
            case "周二":
                mList.addAll(getDay(day2));
                break;
            case "周三":
                mList.addAll(getDay(day3));
                break;
            case "周四":
                mList.addAll(getDay(day4));
                break;
            case "周五":
                mList.addAll(getDay(day5));
                break;
            case "周六":
                mList.addAll(getDay(day6));
                break;
            case "周日":
                mList.addAll(getDay(day7));
                break;
//
        }
        return mList;
    }

    //     动态将数组加入title里面去
    private ArrayList< String> getDay(String [] days) {
        ArrayList< String> dlist=new ArrayList<>();
        for (int i = 0; i <days.length ; i++) {
            dlist.add(days[i]);
        }
        return dlist;
    }

    //    将id 动态的传到每个fragment里面去然后动态的取id
    private List<Fragment> initFragment() {
        List<Fragment> mList=new ArrayList<>();
        for (int i = 0; i <urlNum.length ; i++) {
            Bundle bundle=new Bundle();
            bundle.putLong("id",urlNum[i]);
            Fragment fragment= MainFragment.getNewInstance(bundle);
            mList.add(fragment);
        }
        return mList;
    }

    @Override
    protected void onStart() {
        super.onStart();
//       开始轮播
        mBannerMain.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
//       停止轮播
        mBannerMain.stopAutoPlay();
    }

}
