package com.syfm.groover.business.usecases;

import android.util.Log;

import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.SharedPreferenceHelper;

import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/27.
 */
public class LoginUseCase {

    public class LoginEvent {
        public final boolean success;
        public LoginEvent(boolean success) {
            this.success = success;
        }
    }

    public void checkLogin(final String serial, final String pass) {
        if(!loggedin()) {
            return;
        }
        ApiClient client = new ApiClient();
        client.tryLogin(serial, pass, new ApiClient.LoginListener() {
            @Override
            public void onSuccess() {
                if (!loggedin()) {
                    SharedPreferenceHelper.setLoginInfo(serial, pass);
                    EventBus.getDefault().post(new LoginEvent(true));
                } else {
                    EventBus.getDefault().post(new LoginEvent(false));
                }
            }

            @Override
            public void onFailure() {
                EventBus.getDefault().post(new LoginEvent(false));
            }
        });
    }

    private boolean loggedin() {
        return AppController.getInstance().checkLoginCookie();
    }
}
