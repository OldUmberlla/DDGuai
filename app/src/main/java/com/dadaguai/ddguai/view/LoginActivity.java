package com.dadaguai.ddguai.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.base.BaseLayoutActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by GongSS
 * Date: 2018/11/30 14:34
 * _Umbrella
 */
public class LoginActivity extends BaseLayoutActivity {
    @BindView(R.id.login_user_icon)
    ImageView loginUserIcon;
    @BindView(R.id.login_edit_username)
    EditText loginEditUsername;
    @BindView(R.id.login_edit_password)
    EditText loginEditPassword;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_tv_register)
    TextView loginTvRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void initModelData() {
        SharedPreferences flash = getSharedPreferences("flash", MODE_PRIVATE);
        if (flash != null) {
            String gender = flash.getString("gender", "");
            switch (gender) {
                case "woman":
                    loginUserIcon.setBackgroundResource(R.mipmap.woman_black);
                    loginEditUsername.setText("20181207czx");
                    loginEditPassword.setText("test/123");
                    break;
                case "man":
                    loginUserIcon.setBackgroundResource(R.mipmap.man_black);
                    loginEditUsername.setText("20181207gss");
                    loginEditPassword.setText("test/123");
                    break;
                default:
            }
        }

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

    @OnClick({R.id.login_btn_login, R.id.login_tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                String name = getEditStr(loginEditUsername);
                String pwd = getEditStr(loginEditPassword);
                EMClient.getInstance().login(name, pwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        jumpTo(HomeActivity.class);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("---", "onError: "+s );
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
            case R.id.login_tv_register:
                toast("暂未开放");
                break;
            default:
        }
    }

    public String getEditStr(EditText edit) {
        String trim = edit.getText().toString().trim();
        if (!trim.equals("") || trim != null) {
            return trim;
        }
        toast("不能不填哦！");
        return "";
    }

}
