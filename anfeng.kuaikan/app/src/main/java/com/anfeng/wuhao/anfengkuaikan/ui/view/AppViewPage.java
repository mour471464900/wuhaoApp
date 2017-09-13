package com.anfeng.wuhao.anfengkuaikan.ui.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.List;

/**
 *  自定义页面的viewPage,内部封装了适配器
 */
public class AppViewPage extends ViewPager {

    private Context mContext;    // 上下文

    private FragmentManager mFm; // fragment管理器

    private ViewPagerAdapter mAdapter;      // 该ViewPage 的适配器
    public AppViewPage(Context context) {
        super(context);
        this.mContext=context;
    }

    public AppViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
    }

    public AppViewPage setDate(FragmentManager fm, List<Fragment> fmList,List<String> titles){
        mAdapter=new ViewPagerAdapter(fm,fmList,titles);
        this.setAdapter(mAdapter);
        return this;
    }

    public static  class  ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment>  mFragmentList;  // fragment 集合数据
        private List<String>  mTitles;          // 标题
        public ViewPagerAdapter(FragmentManager fm,List<Fragment> fmList,List<String> titles) {
            super(fm);
            this.mFragmentList=fmList;
            this.mTitles=titles;
        }

        @Override
        public Fragment getItem(int position) {
            return  mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList==null?0:mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles==null? "": mTitles.get(position) ;
        }
    }


}
