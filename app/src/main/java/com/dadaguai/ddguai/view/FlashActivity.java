package com.dadaguai.ddguai.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.api.API;
import com.dadaguai.ddguai.base.BaseLayoutActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlashActivity extends BaseLayoutActivity {


    private SharedPreferences flash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void initModelData() {
        showDialog();
        flash = getSharedPreferences("flash", MODE_PRIVATE);

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_flash_select, null);//获取自定义布局
        builder.setCancelable(false);
        builder.setView(layout);
        builder.setIcon(R.drawable.dadaguai_logo);//设置标题图标

        ImageView man = layout.findViewById(R.id.flash_man);
        ImageView woman = layout.findViewById(R.id.flash_woman);

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flash.edit().putString("gender", "man").commit();
                API.GENDER = "man";
                jumpTo(LoginActivity.class);
                finish();
            }
        });

        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flash.edit().putString("gender", "woman").commit();
                API.GENDER = "woman";
                jumpTo(LoginActivity.class);
                finish();
            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }


//    @OnClick({R.id.btn_register, R.id.btn_login})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_register:
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
    // TODO Auto-generated method stub
//                        try {
//                            EMClient.getInstance().createAccount(name + "2", password);
//                        } catch (HyphenateException e) {
//                            e.printStackTrace();
//
//                            Log.e("eeee", "run: " + e.toString() + e.getErrorCode());
//                        }

//                        try {
//                            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                            Log.e("usernames", "run: " + usernames.toString());
//                        } catch (HyphenateException e) {
//                            e.printStackTrace();
//                        }

    //参数为要添加的好友的username和添加理由
//                        try {
//                            EMClient.getInstance().contactManager().acceptInvitation(name);
//                        } catch (HyphenateException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                break;
//            case R.id.btn_login:

//                break;
//            default:
//        }
//    }
}
