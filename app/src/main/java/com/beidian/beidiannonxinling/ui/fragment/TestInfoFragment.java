package com.beidian.beidiannonxinling.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/310:44
 * @描述: ------------------------------------------
 */

public class TestInfoFragment extends Fragment {
    View mView;


    public void init(View tv_testInfo) {
        this.mView = tv_testInfo;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return mView;
    }
}
