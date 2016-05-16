package com.syfm.groover.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * Created by lycoris on 2015/09/24.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private static AppController sInstance;

    private static Context context;

    private static CookieManager cookieManager;

    private static OkHttpClient client;

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String SESSION_COOKIE = "PHPSESSID";
    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    public static synchronized AppController getInstance() {
        return sInstance;
    }

    public final boolean checkLoginCookie() {
        // Groove Coasterだけで判断すること
        if (cookieManager.getCookieStore().getCookies().size() > 0) {
            return true;
        }
        return false;
    }

    public static OkHttpClient getOkHttpClient() {
        Log.d("AppController", "API accessed");
        return client;
    }

    public static void clearCookies() {
        cookieManager.getCookieStore().removeAll();
    }

    public static Context getContext() {
        return context;
    }

}
