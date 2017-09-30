package com.anfeng.wuhao.anfengkuaikan.inter;

import android.view.View;

import com.anfeng.wuhao.anfengkuaikan.holder.BetterViewHolder;


public interface TypeFactory {

//    int type(Category title);
//    int type(ProductList products);
//    int type(HotList products);

    BetterViewHolder onCreateViewHolder(View itemView, int viewType);

}
