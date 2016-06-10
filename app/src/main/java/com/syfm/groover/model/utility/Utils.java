package com.syfm.groover.model.utility;

import android.util.Log;

import com.syfm.groover.model.constants.Const;

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

    public static double calcPercentage(int p1, int p2) {
        return (double) p1 / p2 * 100;
    }

    // TODO: String.formatもUtilsに含めるべき?
}
