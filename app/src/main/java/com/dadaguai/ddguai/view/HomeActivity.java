package com.dadaguai.ddguai.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.api.API;
import com.dadaguai.ddguai.base.BaseLayoutActivity;
import com.dadaguai.ddguai.model.adapter.TalkingMessageAdapter;
import com.dadaguai.ddguai.model.bean.TalkingMessageBean;
import com.dadaguai.ddguai.utils.AndroidBug54971Workaround;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by GongSS
 * Date: 2018/12/7 14:45
 * _Umbrella
 */
public class HomeActivity extends BaseLayoutActivity {
    private String TAG = getName();
    @BindView(R.id.home_talk_name)
    TextView homeTalkName;
    @BindView(R.id.home_talk_msglist)
    RecyclerView homeTalkMsglist;
    @BindView(R.id.home_talk_message)
    EditText homeTalkMessage;
    @BindView(R.id.home_talk_send)
    ImageView homeTalkSend;
    private List<TalkingMessageBean> messageBeanList = new ArrayList<>();
    private TalkingMessageAdapter messageAdapter;
    private String userId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void findViews() {
        AndroidBug54971Workaround.assistActivity(findViewById(R.id.main_root));
    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void initModelData() {
        if (API.GENDER.equals("man")) {
            userId = "20181207czx";
            homeTalkName.setText("小胖子");
        } else if (API.GENDER.equals("woman")) {
            userId = "20181207gss";
            homeTalkName.setText("全宇宙最帅的森哥");
        }
        //通过注册消息监听来接收消息。
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
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

    @OnClick(R.id.home_talk_send)
    public void onViewClicked() {
        String message = getEditStr(homeTalkMessage);
        if (message != null) {
            //创建一条文本消息，填入消息与用户id
            EMMessage emMessage = EMMessage.createTxtSendMessage(message, userId);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(emMessage);
            //添加消息到历史记录列表中
            setMyMessage(2, message);
            homeTalkMsglist.scrollToPosition(messageAdapter.getItemCount() - 1);
            homeTalkMessage.setText("");
        } else {
            toast("信息不能为空哦！");
        }
    }

    /**
     * 添加消息到历史记录列表中
     *
     * @param type    1 对方发送的消息  2 你发送的消息
     * @param message 消息
     */
    public void setMyMessage(int type, String message) {
        TalkingMessageBean talkingMessageBean = null;
        if (type == 1) {
            //1 对方发送的消息
            talkingMessageBean = new TalkingMessageBean();
            talkingMessageBean.setPersonMessage(message);
        } else if (type == 2) {
            //2 你发送的消息
            talkingMessageBean = new TalkingMessageBean();
            talkingMessageBean.setMyMessage(message);
        }

        if (messageBeanList.size() != 0 && messageAdapter != null) {
            messageBeanList.add(talkingMessageBean);
            messageAdapter.notifyDataSetChanged();
        } else {
            messageBeanList.add(talkingMessageBean);
            homeTalkMsglist.setLayoutManager(new LinearLayoutManager(mContext));
            messageAdapter = new TalkingMessageAdapter(mContext, messageBeanList);
            homeTalkMsglist.setAdapter(messageAdapter);
        }
        Log.e(TAG, "setMyMessage: " + messageBeanList.toString());
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            //收到消息
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String s = messages.get(0).getBody().toString();
                    String substring = s.substring(5, s.length() - 1);
                    setMyMessage(1, substring);
                    Log.e(TAG, "收到消息: " + messages.toString());
                }
            });

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            Log.e(TAG, "收到透传消息: " + messages.toString());
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            Log.e(TAG, "收到已读回执: " + messages.toString());
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            Log.e(TAG, "收到已送达回执: " + message.toString());
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
            Log.e(TAG, "消息被撤回: " + messages.toString());
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.e(TAG, "消息状态变动: " + message.toString());
        }
    };

    public String getEditStr(EditText edit) {
        String trim = edit.getText().toString().trim();
        if (!trim.equals("")) {
            return trim;
        }
        toast("信息不能为空哦！");
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
