package com.beidian.beidiannonxinling.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;

/**
 * Created by Eric on 2017/9/12.
 */

public class ViewUtils {

    /**
     * 设置控件不可点击
     * @param viewGroup 父控件
     */
    public static void disableSubControls(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    spinner.setEnabled(false);
                } else if (v instanceof ListView) {
                    ((ListView) v).setClickable(false);
                    ((ListView) v).setEnabled(false);
                } else if (v instanceof RecyclerView) {
                    ((RecyclerView) v).setClickable(false);
                    ((RecyclerView) v).setEnabled(false);
                } else {
                    disableSubControls((ViewGroup) v);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setEnabled(false);
                ((EditText) v).setClickable(false);
                ((EditText) v).setTextColor(BaseApplication.getAppContext().getResources().getColor(R.color.change_edit));

            } else if (v instanceof TextView) {
                ((TextView) v).setEnabled(false);
                ((TextView) v).setClickable(false);
                ((TextView) v).setTextColor(Color.BLACK);
            } else if (v instanceof Button) {
                ((Button) v).setEnabled(false);
            }


        }
    }

    /**
     * 设置控件几秒内不可重复点击
     * @param view  控件
     * @param times 时间  单位 毫秒
     */
    public static void PreventDuplicateClicks(final View view, int times){
        view.setEnabled(false);
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                view.setClickable(true);
            }
        },times);
    }
    public static void initSpinner(Context mContext, Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(mContext, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }
}
