package com.dadaguai.ddguai.basadapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 抽象适配器（实现部分父类方法、免去一些通用的代码）
 * Created by GongSS
 * Date: 2018/9/12 10:40
 * _Umbrella
 *
 * @param <T>
 *           实体对象
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {
	
	private Context context;
	
	private List<T> listData;

	private LayoutInflater layoutInflater;
	
	public AbstractAdapter(Context context, List<T> listData){
		this.context = context;
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData==null ? 0:listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData==null ? null:listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}

	public Context getContext(){
		return context;
	}

	public LayoutInflater getLayoutInflater(){
		return layoutInflater;
	}
	
	public View inflate(@LayoutRes int layoutId, ViewGroup parent, boolean attachToRoot){
		return layoutInflater.inflate(layoutId,parent,attachToRoot);
	}

}
