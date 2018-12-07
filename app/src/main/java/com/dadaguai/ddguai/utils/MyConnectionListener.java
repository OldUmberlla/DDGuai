package com.dadaguai.ddguai.utils;

import android.content.Context;
import android.widget.Toast;

import com.dadaguai.ddguai.view.HomeActivity;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;

/**
 * Created by GongSS
 * Date: 2018/12/7 15:04
 * _Umbrella
 */
public class MyConnectionListener implements EMConnectionListener {
    private Context context;

    public MyConnectionListener(Context context) {
        this.context = context;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                    Toast.makeText(context, "显示帐号已经被移除", Toast.LENGTH_SHORT).show();
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    Toast.makeText(context, "显示帐号在其他设备登录", Toast.LENGTH_SHORT).show();
                } else {
                    if (NetUtils.hasNetwork(context)) {
                        //连接不到聊天服务器
                        Toast.makeText(context, "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
                    } else {
                        //当前网络不可用，请检查网络设置
                        Toast.makeText(context, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
