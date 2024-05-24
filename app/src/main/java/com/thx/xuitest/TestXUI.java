package com.thx.xuitest;

import android.app.Application;


import com.xuexiang.xui.XUI;

public class TestXUI extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        XUI.init(this);
        XUI.debug(true);
    }
}
