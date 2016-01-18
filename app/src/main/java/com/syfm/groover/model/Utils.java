package com.syfm.groover.model;

import android.util.Log;

import com.syfm.groover.model.storage.Const;

/**
 * Created by lycoris on 2016/01/17.
 */
public class Utils {

    public static void sleep() {
        try {
            Thread.sleep(Const.TIME);
        } catch (InterruptedException e) {
            Log.d("ktr", e.toString());
        }
    }
}
