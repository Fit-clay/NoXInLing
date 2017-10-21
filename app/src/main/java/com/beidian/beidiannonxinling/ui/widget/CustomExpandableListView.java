package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;


/**
 * Created by shanpu on 2016/12/02.
 * Describe: 自定义ExpandableListView
 */
public class CustomExpandableListView extends ExpandableListView {

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
