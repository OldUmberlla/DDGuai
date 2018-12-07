package com.dadaguai.ddguai.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.base.BaseLayoutActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlashActivity extends BaseLayoutActivity {

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String name = "user_test";
    private String password = "test/123";

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

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                new Thread(new Runnable() {

                    @Override
                    public void run() {
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
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(name);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_login:
                EMClient.getInstance().login(name + "2", password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        btnLogin.post(new Runnable() {
                            @Override
                            public void run() {
                                btnLogin.setText("登陆成功！");
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
            default:
        }
    }
}
