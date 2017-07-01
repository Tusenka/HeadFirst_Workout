package com.headfirst.irina.stopwatch;

import android.util.Log;

//TODO: make binary weaving for using execution(public * android.support.v4.app.Activity+.on*(.
public aspect LoggerAspect {
    before(): execution( * *() )
            {
               Log.e("lifecycle", "AsepctJ test success");
               //throw new RuntimeException("AsepctJ test success");

            }
}
