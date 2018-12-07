package com.dadaguai.ddguai.basadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用ViewPagerAdapter
 * Created by GongSS
 * Date: 2018/9/12 10:40
 * _Umbrella
 *           实体对象
 */
public abstract class ViewPagerAdapter<T> extends PagerAdapter {

	protected Context context;
	private List<T> listData = null;
	private List<CharSequence> listTitle = null;

	public ViewPagerAdapter(Context context, List<T> listData) {
		this.context = context;
		this.listData = listData;
	}
	public ViewPagerAdapter(Context context, List<T> listData, List<CharSequence> listTitle) {
		this.context = context;
		this.listData = listData;
		this.listTitle = listTitle;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public int getCount() {
		return listData==null ? 0:listData.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = getView(container,listData.get(position),position);
		container.addView(view);
		return view;
	}

	public abstract View getView(ViewGroup container, T t, int position);

	@Override
	public boolean isViewFromObject(View paramView, Object paramObject) {
		return paramView == paramObject;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(listTitle!=null && listTitle.size()!=0){
			return listTitle.get(position);
		}
		return super.getPageTitle(position);
	}


}
