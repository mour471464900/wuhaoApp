package com.anfeng.wuhao.anfengkuaikan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;
import android.widget.TextView;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.base.ListBaseAdapter;
import com.anfeng.wuhao.anfengkuaikan.base.SuperViewHolder;
import com.anfeng.wuhao.anfengkuaikan.bean.DateListBean;
import com.anfeng.wuhao.anfengkuaikan.utils.ImageLoad;
import com.anfeng.wuhao.anfengkuaikan.view.AppImage;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/12.
 * 描述：
 * =============================
 */

public class DateListAdapter extends ListBaseAdapter<DateListBean.DataBean.ComicsBean> {
    private Context mContext;

    public DateListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_seven_fragment_listview;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        DateListBean.DataBean.ComicsBean bean = getDataList().get(position);
        TextView lable = holder.getView(R.id.tv_seven_lable);
        TextView top_title = holder.getView(R.id.tv_seven_top_title);
        TextView top_avatar = holder.getView(R.id.tv_seven_top_avatar);
        TextView bottom_title = holder.getView(R.id.tv_seven_bottom_title);
        CheckBox dianzhan = holder.getView(R.id.tv_seven_dianzhan);
        CheckBox pinlun = holder.getView(R.id.tv_seven_pinlun);
        AppImage cover = holder.getView(R.id.iv_seven_cover);
        //            改变Ui控件
        lable.setText(bean.getLabel_text()); // 改变文字
        //             改变颜色
        Drawable background = lable.getBackground();
        //bean.getLabel_color()这是一个十六进制的颜色配置    "label_color":"#fa6499",
        background.setColorFilter(Color.parseColor(bean.getLabel_color()), PorterDuff.Mode.SRC);
        //这个是颜色过滤器，来改变drawable 的颜色
        top_title.setText(bean.getTopic().getTitle());
        bottom_title.setText(bean.getTitle());
        dianzhan.setText("" + bean.getLikes_count());
        pinlun.setText("" + bean.getComments_count());
        ImageLoad.getInstance().setImageURI(cover,bean.getCover_image_url());
    }
}
