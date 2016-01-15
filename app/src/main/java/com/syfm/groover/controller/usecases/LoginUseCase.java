package com.syfm.groover.controller.usecases;

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
        if(!loggedin()) {
            return;
        }
        ApiClient client = new ApiClient();
        deferred.when(() -> {
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
        });
    }

    private boolean loggedin() {
        return AppController.getInstance().checkLoginCookie();
    }
}
