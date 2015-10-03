package com.syfm.groover.data.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.syfm.groover.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lycoris on 2015/09/26.
 */
public class SharedPreferenceHelper {
    private static SharedPreferences sp;
    private static Resources r;

    public static void create(Context context) {
        if (null == sp || null == r) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            r = context.getResources();
        }
    }

    public static Map<String, String> getLoginInfo() {
        Map<String, String> info = new HashMap<String, String>();
        String serialNo = sp.getString(getStr(R.string.pref_serial_no), "");
        String password = sp.getString(getStr(R.string.pref_password), "");
        if(serialNo != "" && password != "") {
            info.put(getStr(R.string.pref_serial_no), serialNo);
            info.put(getStr(R.string.pref_password), password);
        }
        return info;
    }

    public static void setLoginInfo(String nesicaId, String password) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getStr(R.string.pref_serial_no), nesicaId);
        editor.putString(getStr(R.string.pref_password),  password);
        editor.commit();
    }

    private static String getStr(int id) {
        return r.getString(id);
    }

}
