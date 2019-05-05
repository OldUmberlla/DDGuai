package com.dadaguai.ddguai.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.api.API;
import com.dadaguai.ddguai.base.BaseLayoutActivity;
import com.dadaguai.ddguai.model.adapter.TalkingMessageAdapter;
import com.dadaguai.ddguai.model.bean.TalkingMessageBean;
import com.dadaguai.ddguai.utils.AndroidBug54971Workaround;
import com.dadaguai.ddguai.utils.AudioRecoderUtils;
import com.dadaguai.ddguai.utils.ViewAnimation;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.FileDescriptor;
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
    @BindView(R.id.home_talk_back)
    ImageView homeTalkBack;
    @BindView(R.id.home_talk_pengyouquan)
    ImageView homeTalkPengyouquan;
    @BindView(R.id.home_talk_headlogo)
    ImageView homeTalkHeadlogo;
    @BindView(R.id.home_talk_voice)
    ImageView homeTalkVoice;
    @BindView(R.id.home_talk_emoji)
    ImageView homeTalkEmoji;
    @BindView(R.id.home_talk_add)
    ImageView homeTalkAdd;
    @BindView(R.id.home_talk_send_img)
    ImageView homeTalkSendImg;
    @BindView(R.id.home_talk_send_photo)
    ImageView homeTalkSendPhoto;
    @BindView(R.id.home_talk_send_video)
    ImageView homeTalkSendVideo;
    @BindView(R.id.home_talk_bottom_linear)
    LinearLayout homeTalkBottomLinear;
    @BindView(R.id.home_talk_linearlayout)
    LinearLayout homeTalkLinearlayout;
    @BindView(R.id.main_root)
    LinearLayout mainRoot;
    @BindView(R.id.home_talk_send_video_call)
    ImageView homeTalkSendVideoCall;
    @BindView(R.id.home_talk_send_call)
    ImageView homeTalkSendCall;
    @BindView(R.id.home_talk_take_audio)
    TextView homeTalkTakeAudio;
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
    private boolean voiceFlag = false;
    private AudioRecoderUtils audioRecoderUtils;
    private String audioTime;

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
        //发送语音消息相关监听
        takeAudio();

    }

    /**
     * 发送语音消息相关监听
     */
    public void takeAudio() {
        audioRecoderUtils = new AudioRecoderUtils();
        //录音回调
        audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                audioTime= timeParse(time);

                Log.e(TAG, "onUpdate: " + time);

            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                //filePath为语音文件路径，length为录音时间(秒)
//                EMMessage message = EMMessage.createVoiceSendMessage(filePath, audioTime, userId);
//                EMClient.getInstance().chatManager().sendMessage(message);
                Log.e(TAG, "onStop: ---" + audioTime + "---" + filePath);
            }
        });

        homeTalkTakeAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        homeTalkTakeAudio.setText("松开保存");
                        audioRecoderUtils.startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        audioRecoderUtils.stopRecord();
                        homeTalkTakeAudio.setText("按住说话");
                        break;
                        default:
                }
                return true;
            }
        });
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
                    homeTalkMsglist.scrollToPosition(messageAdapter.getItemCount() - 1);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @OnClick({R.id.home_talk_voice, R.id.home_talk_emoji, R.id.home_talk_add, R.id.home_talk_send,
            R.id.home_talk_send_img, R.id.home_talk_send_photo, R.id.home_talk_send_video, R.id.home_talk_send_video_call,
            R.id.home_talk_send_call, R.id.home_talk_take_audio})
    public void onViewClicked(View view) {
        ViewAnimation.setAnimation(view);
        switch (view.getId()) {
            case R.id.home_talk_voice://发送语音消息
                if (!voiceFlag) {
                    homeTalkVoice.setBackgroundResource(R.mipmap.key_board);
                    homeTalkMessage.setVisibility(View.GONE);
                    homeTalkTakeAudio.setVisibility(View.VISIBLE);
                    voiceFlag = true;
                } else {
                    homeTalkVoice.setBackgroundResource(R.mipmap.voice);
                    homeTalkMessage.setVisibility(View.VISIBLE);
                    homeTalkTakeAudio.setVisibility(View.GONE);
                    voiceFlag = false;
                }

                break;
            case R.id.home_talk_emoji://发送emoji表情
                break;
            case R.id.home_talk_add://发送更多

                if (homeTalkBottomLinear.getVisibility() == View.VISIBLE) {
                    homeTalkBottomLinear.setVisibility(View.GONE);
                } else if (homeTalkBottomLinear.getVisibility() == View.GONE) {
                    homeTalkBottomLinear.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.home_talk_send://发送消息
                String message = homeTalkMessage.getText().toString().trim();
                if (!message.equals("")) {
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

                break;
            case R.id.home_talk_send_img://发送图片
                break;
            case R.id.home_talk_send_photo://拍照发送
                break;
            case R.id.home_talk_send_video://发送视频
                break;
            case R.id.home_talk_send_video_call://视频电话
                break;
            case R.id.home_talk_send_call://语音通话
                break;
            case R.id.home_talk_take_audio://按住说话
                break;
            default:
        }
    }


    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }
}
