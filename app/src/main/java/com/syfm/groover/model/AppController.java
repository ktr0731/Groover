package com.syfm.groover.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

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
                .connectTimeout(5, TimeUnit.SECONDS)
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
