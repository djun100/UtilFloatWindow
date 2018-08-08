package com.cy.floatwindow;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * mFloatView = new FloatView(getApplicationContext());
 * mFloatView.show();
 */
public class FloatView extends BaseFloatView{

    public FloatView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.floatwindow, this);
        clear();
    }

    public void clear(){

    }
}
