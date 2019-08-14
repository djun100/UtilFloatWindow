package com.cy.utilfloatwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cy.floatwindow.permission.FloatWindowPermissionUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FloatWindowPermissionUtil.getInstance().checkPermission(MainActivity.this)){
                    FloatWindowPermissionUtil.getInstance().applyPermissionWithDialogConfirm(MainActivity.this);
                }
            }
        });
    }
}
