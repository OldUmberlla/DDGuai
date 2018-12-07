package com.dadaguai.ddguai.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.dadaguai.ddguai.utils.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by GongSS
 * Date: 2018/9/3 14:22
 * _Umbrella
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    /**
     * 用于传递的上下文信息
     */
    protected Context mContext;
    protected Activity mActivity;
    public Unbinder unbinder;

    private View currentLayout;
    private boolean isUseBase = false;
    private Toast toast;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the title_layout for this fragment
        currentLayout = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, currentLayout);
        isUseBase = true;
        return currentLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        findViews();
        formatViews();
        lazyLoad();
        formatData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context.getApplicationContext();
        mActivity = (Activity) context;
    }



    /**
     * 隐藏键盘
     */
    protected void hideKeyBoard() {
        View view = mActivity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 跳转页面
     *
     * @param targetActivity 所跳转的目标Activity类
     */
    protected void jumpTo(Class<?> targetActivity) {
        Intent intent = new Intent(mContext, targetActivity);
        if (mActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Logger.e(getName(), "mActivity not found for " + targetActivity.getSimpleName());
            }
        }
    }

    /**
     * 跳转页面
     *
     * @param targetActivity 所跳转的目的Activity类
     * @param bundle         跳转所携带的信息
     */
    protected void jumpTo(Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(mContext, targetActivity);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        if (mActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Logger.e(getName(), "mActivity not found for " + targetActivity.getSimpleName());
            }
        }
    }

    /**
     * 跳转页面
     *
     * @param targetActivity 所跳转的Activity类
     * @param requestCode    请求码
     */
    protected void jumpTo(Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent(mContext, targetActivity);
        if (mActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                Logger.e(getName(), "mActivity not found for " + targetActivity.getSimpleName());
            }
        }
    }

    /**
     * 跳转页面
     *
     * @param targetActivity 所跳转的Activity类
     * @param bundle         跳转所携带的信息
     * @param requestCode    请求码
     */
    protected void jumpTo(Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mContext, targetActivity);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        if (mActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                Logger.e(getName(), "mActivity not found for " + targetActivity.getSimpleName());
            }
        }
    }

    /**
     * 获取当前Fragment类名
     *
     * @return 类名字符串
     */
    protected String getName() {
        return getClass().getSimpleName();
    }

    /**
     * 获取类名
     *
     * @param clz 需要获取名称的类
     * @return 类名字符串
     */
    protected String getName(Class<?> clz) {
        return clz.getClass().getSimpleName();
    }

    /**
     * 消息提示框
     * https://www.jianshu.com/p/4551734b3c21
     *
     * @param message 提示消息文本
     */
    @SuppressLint("ShowToast")
    public void toast(String message) {
        try {
            if (toast != null) {
                toast.setText(message);
            } else {
                toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.show();
            Looper.loop();
        }
    }

    /**
     * 消息提示框
     * https://www.jianshu.com/p/4551734b3c21
     *
     * @param messageId 提示消息文本ID
     */
    protected void toast(int messageId) {
        try {
            if (toast != null) {
                toast.setText(messageId);
            } else {
                toast = Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            toast = Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT);
            toast.show();
            Looper.loop();
        }
    }

    /**
     * 简化获取View
     *
     * @param viewId View的ID
     * @param <T>    将View转化为对应泛型，简化强转的步骤
     * @return ID对应的View
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int viewId) {
        return (T) currentLayout.findViewById(viewId);
    }

    /**
     * 简化获取View
     *
     * @param view   父view
     * @param viewId View的ID
     * @param <T>    将View转化为对应泛型，简化强转的步骤
     * @return ID对应的View
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param layouts 点击控件Id
     */
    protected void setOnClickListener(int... layouts) {
        for (int layout : layouts) {
            getView(layout).setOnClickListener(this);
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }
    }

    protected abstract void loadData();
    /**
     * 获取布局ID
     *
     * @return 获取的布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 获取所有View信息
     */
    protected abstract void findViews();

    /**
     * 初始化布局信息
     */
    protected abstract void formatViews();

    /**
     * 初始化数据信息
     */
    protected abstract void formatData();

    /**
     * 初始化Bundle
     */
    protected abstract void getBundle(Bundle bundle);
}
