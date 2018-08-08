package com.cy.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cy.io.Log;


public abstract class BaseFloatView extends LinearLayout implements IFloatView{

    private WindowManager.LayoutParams mParams;
    private FloatingManager mWindowManager;
    FloatWindowDragUtil mFloatWindowDragUtil;
    protected String mNumber;
    public BaseFloatView(Context context) {
        super(context);
        baseInit();
    }

    protected void baseInit(){
        mFloatWindowDragUtil = FloatWindowDragUtil.newInstance(this);
        mWindowManager = FloatingManager.getInstance(getContext());
    }

    public void show(String number) {
        mNumber = number;

        if(this instanceof FloatView){
            mParams = createFloatWindowParams();
        }else if (this instanceof FloatView2){
            mParams = createFloatWindow2Params();
        }

        mFloatWindowDragUtil.setParam(mParams);
        mWindowManager.addView(this, mParams);
        if(this instanceof FloatView){
            Log.w("显示悬浮窗FloatView");
        }else {
            Log.w("显示悬浮窗FloatView2");
        }
    }

    public void hide() {
        clear();
        mWindowManager.removeView(this);
        if(this instanceof FloatView){
            Log.w("隐藏悬浮窗FloatView");
        }else {
            Log.w("隐藏悬浮窗FloatView2");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mFloatWindowDragUtil.onTouchEvent(event);
    }

    private WindowManager.LayoutParams createFloatWindowParams(){
        WindowManager.LayoutParams params = createDefaultParams();
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = com.cy.System.UtilEnv.getScreenSize(getContext()).y/3;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.WRAP_CONTENT;
        return params;
    }

    private WindowManager.LayoutParams createFloatWindow2Params(){
        WindowManager.LayoutParams params = createDefaultParams();
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        return params;
    }

    private WindowManager.LayoutParams createDefaultParams(){
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //总是出现在应用程序窗口之上
        int mType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0悬浮窗权限适配
//            mType = WindowManager.LayoutParams.TYPE_PHONE;
            mType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        params.type = mType;
        //设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;
        params.flags =
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        return params;
    }

}
