package com.dadaguai.ddguai.basadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * * 通用适配器（适合一些常规的适配器）
 * Created by GongSS
 * Date: 2018/9/12 10:40
 * _Umbrella
 *
 * @param <T>
 *           实体对象
 * @param <H>
 *     		ViewHolder
 */
public abstract class HolderAdapter<T,H> extends AbstractAdapter<T>{

	public HolderAdapter(Context context, List<T> listData) {
		super(context, listData);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		H holder = null;
		T t = getListData().get(position);
		if(convertView==null){
			convertView = buildConvertView(getLayoutInflater(),t,position,parent);
			holder = buildHolder(convertView,t,position);
			convertView.setTag(holder);
			//对于listview，注意添加这一行，即可在item上使用高度
			AutoUtils.autoSize(convertView);
		}else{
			holder = (H)convertView.getTag();
		}
		bindViewDatas(holder,t,position);

		return convertView;
	}

	/**
	 * 建立convertView
	 * @param layoutInflater
	 * @return
	 */
	public abstract View buildConvertView(LayoutInflater layoutInflater, T t, int position, ViewGroup parent);

	/**
	 * 建立视图Holder
	 * @param convertView
	 * @return
	 */
	public abstract H buildHolder(View convertView, T t, int position);

	/**
	 * 绑定数据
	 * @param holder
	 * @param t
	 * @param position
	 */
	public abstract void bindViewDatas(H holder,T t,int position);


}