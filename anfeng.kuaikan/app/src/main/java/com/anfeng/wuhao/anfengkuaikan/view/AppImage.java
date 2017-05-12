package com.anfeng.wuhao.anfengkuaikan.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 *  该app中所有图片的视图的基类
 *  1.由于使用Fresco框架 ,SimpleDraweeView是不能向上转型为ImageView的
 *  2.当需要切换到其他框架的时候，只需要将此视图继承ImageView就可以了
 *
 */
public class AppImage extends SimpleDraweeView {
    public AppImage(Context context) {
        super(context);
    }

    public AppImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AppImage(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public AppImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
