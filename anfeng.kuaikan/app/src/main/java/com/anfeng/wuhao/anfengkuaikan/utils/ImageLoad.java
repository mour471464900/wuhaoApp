package com.anfeng.wuhao.anfengkuaikan.utils;

import com.anfeng.wuhao.anfengkuaikan.view.AppImage;

/**
 * 图片加载工具类
 */
public class ImageLoad {
    /**
     * 单例对象
     */
    private static ImageLoad mImageLoad;

    /**
     * 返回单例对象
     * @return
     */
    public static ImageLoad getInstance() {
        if (null == mImageLoad) {
            mImageLoad = new ImageLoad();
        }
        return mImageLoad;
    }

    /**
     * 加载图片
     * @param appImage
     * @param uri
     */
    public void  setImageURI(AppImage appImage,String uri){
            try {
                appImage.setImageURI(uri);
            }catch (Exception e){
                e.printStackTrace();
            }
    }


}
