package com.dadaguai.ddguai.basadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by GongSS
 * Date: 2018/9/12 10:40
 * _Umbrella
 *
 * @param <T>
 *           实体对象
 * @param <H>
 *     		ViewHolder
 */
public abstract class HolderRecyclerAdapter<T,H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>{

    private Context context;

    private List<T> listData;

    private LayoutInflater layoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.mOnItemClickListener = clickListener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    protected void onItemClick(View v, int position){
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,position);
        }
    }

    public HolderRecyclerAdapter(Context context, List<T> listData){
        super();
        this.context = context;
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getLayoutInflater(){
        return layoutInflater;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = buildConvertView(layoutInflater,parent,viewType);
        return buildHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(H holder, final int position) {
        T t = position<listData.size() ? listData.get(position) : null;
        bindViewDatas(holder,t,position);
        if(this.mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData==null ? 0:listData.size();
    }

    public View inflate(@LayoutRes int layoutId, ViewGroup parent, boolean attachToRoot){
        return layoutInflater.inflate(layoutId,parent,attachToRoot);
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }

    /**
     * 建立convertView
     * @param layoutInflater
     * @param parent
     * @param viewType
     * @return
     */
    public abstract View buildConvertView(LayoutInflater layoutInflater, ViewGroup parent, int viewType);

    /**
     * 建立视图Holder
     * @param convertView
     * @param viewType
     * @return
     */
    public abstract H buildHolder(View convertView, int viewType);

    /**
     * 绑定数据
     * @param holder
     * @param t
     * @param position
     */
    public abstract void bindViewDatas(H holder,T t,int position);
}
