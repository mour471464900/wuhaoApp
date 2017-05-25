package com.anfeng.wuhao.anfengkuaikan.activity;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anfeng.wuhao.anfengkuaikan.R;
import com.anfeng.wuhao.anfengkuaikan.base.BaseActivity;
import com.anfeng.wuhao.anfengkuaikan.view.RoundLinearLayout;

import butterknife.BindView;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/5/18.
 * 描述： 演示进度框的progressDialog
 * =============================
 */

public class ProgressActivity extends BaseActivity {

    public static final int PADDING = 20;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;


//     Handler handler=new Handler(){
//         @Override
//         public void handleMessage(Message msg) {
//             switch (msg.what){
//                 case 1:
//                     if(null!=dialog){
//                         dialog.show();
//                     }
//                 break;
//             }
//
//         }
//     };

    @Override
    public int setContentViewId() {
        return R.layout.activity_progress;
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void initView() {
//        dialog=new Dialog(this);
//        RoundLinearLayout mainView = new RoundLinearLayout(this,50f);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mainView.setLayoutParams(layoutParams);
//        mainView.setOrientation(LinearLayout.VERTICAL);
//        mainView.setPadding(PADDING,PADDING,PADDING,PADDING);
//        mainView.setGravity(CENTER);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_logo_title);
//        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        lpChild.setMargins(0,0,0,30);
//        imageView.setLayoutParams(lpChild);
//        mainView.addView(imageView);
//        TextView textView=new TextView(this);
//        textView.setText("游戏更新中，请稍后：0%");
//        textView.setTextSize(15f);
//        textView.setTextColor(R.color.af_c000000);
//        textView.setLayoutParams(lpChild);
//        mainView.addView(textView);
//        ProgressBar progressBar=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
//        progressBar.setMax(100);
//        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_hor));
//        progressBar.setIndeterminateDrawable(getResources().getDrawable(android.R.drawable.progress_indeterminate_horizontal));
//        mainView.addView(progressBar);
//        dialog.setContentView(mainView);
//        dialog.setCanceledOnTouchOutside(false);
//        handler.sendEmptyMessageDelayed(1,1000);
        RoundLinearLayout linearLayout=new RoundLinearLayout(this,20f);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(200,200);
        layoutParams.setMargins(12,12,12,12);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundColor(Color.GREEN);
        TextView textView=new TextView(this);
        textView.setText("代码布局");
        textView.setTextSize(20f);
        linearLayout.addView(textView);
        mLlMain.addView(linearLayout);
    }

}
