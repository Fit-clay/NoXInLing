package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.beidian.beidiannonxinling.bean.PointBean;
import com.beidian.beidiannonxinling.bean.TestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动打点View
 * Created by Eric on 2017/9/26.
 */

public class PointCanvasView  extends ImageView{

    private Paint paint;
    private List<TestResult> pointList = new ArrayList<>();           //线的集合
    private List<TestResult> pList = new ArrayList<>();              //所要打的点的集合
    private List<TestResult> allList = new ArrayList<>();              //所有打的点的集合
    private List<List<TestResult>> arrayLineList=new ArrayList<>();  //每条线上点的集合
    private float pointX = 0, pointY = 0;



    private int index=1;            //自动打点时的线条

    private Bitmap backgroundBitmap;

    private boolean isAuto ;

    public PointCanvasView(Context context) {
        super(context);
    }

    public PointCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public List<TestResult> getAllList() {
        return allList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(10.0f);
        if(backgroundBitmap !=null){
            RectF rectF=new RectF(0,0,getWidth(),getHeight());
            canvas.drawBitmap(backgroundBitmap,null,rectF,null);
        }
        for (int i = 0; i < pointList.size(); i++) {     //画线
            paint.setColor(Color.GREEN);
            TestResult bean = pointList.get(i);
            canvas.drawCircle(bean.getX(), bean.getY(), 20, paint);// 小圆
            if (i > 0) {
                paint.setColor(Color.BLUE);
                TestResult lastBean = pointList.get(i - 1);
                canvas.drawLine(bean.getX(), bean.getY(), lastBean.getX(), lastBean.getY(), paint);
            }
        }
        allList.clear();
        for(int i=0;i<arrayLineList.size();i++){
            for (TestResult bean : arrayLineList.get(i)) {     //画点
                if(bean.getColor()!=-1){
                    paint.setColor(bean.getColor());
                }else {
                    paint.setColor(Color.BLACK);
                }
                canvas.drawCircle(bean.getX(), bean.getY(), 20, paint);// 小圆
                allList.add(bean);
            }
        }


    }
    public void DrawPointLine(float xx, float yy) {
        pointX = xx;
        pointY = yy;
        TestResult testResult=new TestResult();
        testResult.setX(xx);
        testResult.setY(yy);
        pointList.add(testResult);//xx, yy,-1
        if(isAuto&&pointList.size()>0&&index<pointList.size()){
            TestResult bean = pointList.get(index);
            TestResult lastBean = pointList.get(index - 1);
            setPlistInfo(lastBean, bean);
            arrayLineList.add(pList);
            pList=new ArrayList<>();
//            for(int c=0;c<10;c++){
//                pList.add(new PointBean());
//            }
            index++;
        }
        invalidate();//更新绘制
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            DrawPointLine(event.getX(), event.getY());
            invalidate();//更新绘制
        }
        return super.onTouchEvent(event);
    }

    private void setPlistInfo(TestResult bean1, TestResult bean2) {   //计算两个点之的点的集合坐标
        float indexY = bean2.getY() - bean1.getY();
        float indexX = bean2.getX() - bean1.getX();
        for (int i = 0; i < pList.size(); i++) {
            pList.get(i).setX(bean1.getX() + indexX /  pList.size() * i);
            pList.get(i).setY(bean1.getY() + indexY /  pList.size() * i);
        }
    }

    public void setPlist(TestResult bean){
        pList.add(bean);
    }

    public List<TestResult> getPointList() {
        return pointList;
    }

    public interface isFulfil{
        void fulfil();
    }
    public void nextLine(isFulfil fulfil) {
        if(pointList.size()>1) {
            if (index < pointList.size()) {
                TestResult bean = pointList.get(index);
                TestResult lastBean = pointList.get(index - 1);
                setPlistInfo(lastBean, bean);
                arrayLineList.add(pList);
                pList = new ArrayList<>();
                invalidate();
                index++;
            }else {
                fulfil.fulfil();
            }
        }
    }

    public void setBackgroundBitmap(Bitmap bitmap) {
        this.backgroundBitmap = bitmap;
        invalidate();
    }

    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBackgroundBitmap() {
        invalidate();
        setDrawingCacheEnabled(true);
        Bitmap bitmap = getDrawingCache(true);
        return bitmap;
    }
    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBackgroundimage() {
        return backgroundBitmap;
    }

  /*  public void setAllList(List<PointBean> allList) {
        this.allList = allList;
    }*/

    public void setArrayLineList(List<List<TestResult>> arrayLineList) {
        this.arrayLineList = arrayLineList;
    }

    public List<List<TestResult>> getArrayLineList() {
        return arrayLineList;
    }

}
