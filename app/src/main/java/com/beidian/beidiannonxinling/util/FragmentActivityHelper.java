package com.beidian.beidiannonxinling.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Eric on 2016/10/28.
 */
public class FragmentActivityHelper {
    public interface BFragmentLifeCycleListener {
        void onFragmentShow();
        void onFragmentHide();
    }

    static Fragment lastFragment = null;
    private int curIndex = 0;

    int resId;
    Class<?>[] clses = null;
//    Fragment[] fragments=null;
    FragmentActivity activity;

    public FragmentActivityHelper(FragmentActivity activity, Class<?>[] fragments, int resId) {
        this.activity = activity;
        this.clses = fragments;
        this.resId = resId;
    }
//    public FragmentActivityHelper(FragmentActivity activity, Fragment[] fragments, int resId) {
//        this.activity = activity;
//        this.fragments = fragments;
//        this.resId = resId;
//    }

    /**
     * 需要在check前实例化FragmentActivityHelper对象
     *
     * @param index
     * @return
     */
    public Fragment onChanged(int index) {
        curIndex = index;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Class<?> cls = clses[index];
        Fragment fragment = retrieveFromCache(index);
        if (null == fragment) {// 尚未实例化
            try {
                fragment = (Fragment) cls.newInstance();
                //TODO 初始值
            } catch (Exception e) {
            }

            transaction.add(resId, fragment,"tag_" + index);
            if (lastFragment != null) {
                //lastFragment.onPause();//TODO 当切换隐藏时，触发onPause[注意：更正为统一的方式]
                if(lastFragment instanceof BFragmentLifeCycleListener){
                    ((BFragmentLifeCycleListener) lastFragment).onFragmentHide();
                }
                transaction.hide(lastFragment);
            }

            transaction.commit();
        } else {
            if (lastFragment != null) {
                //lastFragment.onPause();//TODO 当切换隐藏时，触发onPause
                if(lastFragment instanceof BFragmentLifeCycleListener){
                    ((BFragmentLifeCycleListener) lastFragment).onFragmentHide();
                }
                transaction.hide(lastFragment);
            }
            transaction.show(fragment).commitAllowingStateLoss();//原来 commit
            if(fragment instanceof BFragmentLifeCycleListener){
                ((BFragmentLifeCycleListener) fragment).onFragmentShow();
            }
            //if(fireResume && !fragment.isResumed()){ fragment.onResume(); } //TODO 当再次显示时,触发onResume ？？
        }

        return lastFragment = fragment;
    }

    private Fragment retrieveFromCache(int index) {

        Object tag = activity.getSupportFragmentManager().findFragmentByTag("tag_" + index);
        if(tag != null){ return (Fragment) tag;   }
        /*List<Fragment> list = activity.getSupportFragmentManager().getFragments();
        if (null == list) {
            return null;
        }
        for (Fragment fragment : list) {
            if (fragment != null && fragment.getClass().equals(cls)) {
                return fragment;
            }
        }*/
        return null;
    }

    public int getCurrentIndex() {
        return curIndex;
    }

    public Fragment getCurrentFragment(){
        return retrieveFromCache(curIndex);
    }
}