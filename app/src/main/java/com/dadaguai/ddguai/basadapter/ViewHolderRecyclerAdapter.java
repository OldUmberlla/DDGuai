package com.dadaguai.ddguai.basadapter;

import android.content.Context;
import android.view.View;

import com.dadaguai.ddguai.basadapter.holder.ViewHolder;

import java.util.List;

/**
 * 通用适配器
 *      在HolderRecyclerAdapter基础之上将H:ViewHolder具体化，通过通用的ViewHolder根据控件的id得到对应控件，来进行相关的数据绑定操作
 * Created by GongSS
 * Date: 2018/9/12 10:40
 * _Umbrella
 *
 */
public abstract class ViewHolderRecyclerAdapter<T> extends HolderRecyclerAdapter<T,ViewHolder> {

    public ViewHolderRecyclerAdapter(Context context, List<T> listData) {
        super(context, listData);
    }

    @Override
    public ViewHolder buildHolder(View convertView, int viewType) {
        return new ViewHolder(convertView);
    }

}
