package com.cy.floatwindow;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.cy.io.Log;

/**
 * 1、在view的构造函数中初始化                   mFloatWindowDragUtil=FloatWindowDragUtil.newInstance(this);
 * 2、显示悬浮窗后，立即调用这行将params传进来     mFloatWindowDragUtil.setParam(mParams);
 */
public class FloatWindowDragUtil {
    private FloatingManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private float mLastX;
    private float mLastY;
    private float mStartX;
    private float mStartY;
    private long mLastTime ;
    private long mCurrentTime;
    int sW;
    int sH;
    private Context mContext;
    private WindowManager.LayoutParams mParams;
    private View mView;
    public static FloatWindowDragUtil newInstance(View view){
        return new FloatWindowDragUtil(view);
    }

    private FloatWindowDragUtil(View view){
        mContext=view.getContext();
        mView=view;
        mWindowManager = FloatingManager.getInstance(mContext);
    }

    public void setParam(WindowManager.LayoutParams params) {
        mParams = params;
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() /*- statusHeight*/;   //statusHeight是系统状态栏的高度
//        Log.i("currP", "currX" + x + "====currY" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                //获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                mLastTime = System.currentTimeMillis();
                Log.i("startP", "startX" + mTouchStartX + "====startY" + mTouchStartY);
                break;


            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                updateViewPosition();
                break;


            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                mLastX = event.getRawX();
                mLastY = event.getRawY();


                // 抬起手指时让floatView紧贴屏幕左右边缘
                mParams.x = mParams.x <= (sW / 2) ? 0 : sW;
                mParams.y = (int) (y - mTouchStartY);
//                wm.updateViewLayout(this, mParams);
                mWindowManager.updateView(mView,mParams);



                mCurrentTime = System.currentTimeMillis();
                if(mCurrentTime - mLastTime < 800){
                    if(Math.abs(mStartX- mLastX )< 10.0 && Math.abs(mStartY - mLastY) < 10.0){
                        //处理点击的事件
//                        Toast.makeText(mContext,"可以处理点击事件", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
        }
        return true;
    }

    private void updateViewPosition() {
        //更新浮动窗口位置参数
        mParams.x = (int) (x - mTouchStartX);
        mParams.y = (int) (y - mTouchStartY);
//        wm.updateViewLayout(this, mParams);  //刷新显示

        mWindowManager.updateView(mView,mParams);
    }
}
