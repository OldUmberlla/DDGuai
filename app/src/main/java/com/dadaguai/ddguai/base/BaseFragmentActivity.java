package com.dadaguai.ddguai.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.dadaguai.ddguai.utils.Logger;


/**
 * Created by GongSS
 * Date: 2018/9/3 14:20
 * _Umbrella
 */
public abstract class BaseFragmentActivity extends BaseLayoutActivity {
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 添加Fragment
     *
     * @param containerViewId 对应布局的id
     * @param fragments       所要添加的Fragment，可以添加多个
     */
    public void addFragment(int containerViewId, Fragment... fragments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragments != null) {
            for (int i = 0; i < fragments.length; i++) {
                transaction.add(containerViewId, fragments[i]);
                if (i != fragments.length - 1) {
                    transaction.hide(fragments[i]);
                }
            }
            transaction.commit();
        } else {
            Logger.e(getName(), "没有Fragment");
        }
    }

    /**
     * 显示Fragment
     *
     * @param fragment 所要显示的Fragment
     */
    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.show(fragment).commit();
        } else {
            Logger.e(getName(), "ragment不存在");
        }
    }

    /**
     * 隐藏Fragment
     *
     * @param fragments 所要隐藏的Fragment，可以添加多个
     */
    public void hideFragment(Fragment... fragments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                transaction.hide(fragment);
            }
            transaction.commit();
        } else {
            Logger.e(getName(), "没有Fragment");
        }
    }

    /**
     * 替换Fragment
     *
     * @param containerViewId 对应布局的id
     * @param fragment        用于替代原有Fragment的心Fragment
     */
    public void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.replace(containerViewId, fragment).commit();
        } else {
            Logger.e(getName(), "ragment不存在");
        }
    }

    /**
     * 移除Fragment
     *
     * @param fragments 所要移除的Fragment，可以添加多个
     */
    public void removeFragment(Fragment... fragments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                transaction.hide(fragment);
            }
            transaction.commit();
        } else {
            Logger.e(getName(), "没有Fragment");
        }
    }
}
