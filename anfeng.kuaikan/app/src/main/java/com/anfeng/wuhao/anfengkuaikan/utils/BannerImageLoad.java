package com.anfeng.wuhao.anfengkuaikan.utils;

import android.content.Context;
import android.widget.ImageView;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.ui.view.AppImage;
import com.facebook.drawee.generic.GenericDraweeHierarchy;

/**
 *  广告图片的加载类
 */
public class BannerImageLoad extends com.youth.banner.loader.ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ((AppImage)imageView).setImageURI((String) path);
    }

    @Override
    public ImageView createImageView(Context context) {
        AppImage appImage=new AppImage(context);
        GenericDraweeHierarchy hierarchy = appImage.getHierarchy();
        hierarchy.setPlaceholderImage(R.drawable.ic_common_placeholder_l);
        hierarchy.setFailureImage(R.drawable.ic_common_placeholder_l);
        appImage.setHierarchy(hierarchy);
        return appImage;
    }
}
