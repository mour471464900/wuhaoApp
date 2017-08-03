package com.anfeng.wuhao.anfengkuaikan.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.base.BaseActivity;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/7/3.
 * 描述：
 * =============================
 */

public class UnitActivity extends BaseActivity {

    @Override
    public int setContentViewId() {
        return R.layout.activity_unit;
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void initView() {

    }

    public void sayHello(View v){
        TextView textView = (TextView) findViewById(R.id.textView);
        EditText editText = (EditText) findViewById(R.id.editText);
        textView.setText("Hello, " + editText.getText().toString() + "!");
    }

}
