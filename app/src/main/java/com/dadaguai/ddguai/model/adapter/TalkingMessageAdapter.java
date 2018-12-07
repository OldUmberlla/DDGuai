package com.dadaguai.ddguai.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dadaguai.ddguai.R;
import com.dadaguai.ddguai.basadapter.ViewHolderRecyclerAdapter;
import com.dadaguai.ddguai.basadapter.holder.ViewHolder;
import com.dadaguai.ddguai.model.bean.TalkingMessageBean;

import java.util.List;

/**
 * Created by GongSS
 * Date: 2018/12/7 15:43
 * _Umbrella
 */
public class TalkingMessageAdapter extends ViewHolderRecyclerAdapter<TalkingMessageBean> {
    public TalkingMessageAdapter(Context context, List<TalkingMessageBean> listData) {
        super(context, listData);
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return inflate(R.layout.home_talking_list_item, parent, false);
    }

    @Override
    public void bindViewDatas(ViewHolder holder, TalkingMessageBean talkingMessageBean, int position) {
        List<TalkingMessageBean> list = getListData();
        TextView myMessage = holder.getView(R.id.talking_list_item_me);
        TextView pMessage = holder.getView(R.id.talking_list_item_person);
        if (list.get(position).getPersonMessage() != null) {
            pMessage.setVisibility(View.VISIBLE);
            pMessage.setText(list.get(position).getPersonMessage());
        }else{
            pMessage.setVisibility(View.GONE);
        }
        if (list.get(position).getMyMessage() != null) {
            myMessage.setVisibility(View.VISIBLE);
            myMessage.setText(list.get(position).getMyMessage());
        }else{
            myMessage.setVisibility(View.GONE);
        }
    }
}
