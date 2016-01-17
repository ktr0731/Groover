package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.network.AppController;
import com.syfm.groover.model.storage.SharedPreferenceHelper;

import org.jdeferred.android.AndroidDeferredManager;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/27.
 */
public class LoginUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    public class LoginEvent {
        public final boolean success;

        public LoginEvent(boolean success) {
            this.success = success;
        }
    }

    public void checkLogin(final String serial, final String pass) {
        if (!loggedin()) {
            return;
        }
        ApiClient client = new ApiClient();
        deferred.when(() -> {
            client.tryLogin(serial, pass);
        }).done(callback -> {
            // ログインしていたら
            if (!loggedin()) {
                SharedPreferenceHelper.setLoginInfo(serial, pass);
                EventBus.getDefault().post(new LoginEvent(true));
            } else {
                Log.d("ktr", "okdesu");
                EventBus.getDefault().post(new LoginEvent(false));
            }
        });
    }

    private boolean loggedin() {
        return AppController.getInstance().checkLoginCookie();
    }
}
