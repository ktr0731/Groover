package com.syfm.groover.business.usecases;

import com.syfm.groover.data.network.ApiClient;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class MusicDataUseCase implements ApiClient.MusicDataCallback {

    public class SetMusicData {
        public final boolean success;
        public SetMusicData(boolean success) {
            this.success = success;
        }
    }


    public void setMusicData() {
        ApiClient client = new ApiClient();
        client.fetchAllMusicData();
    }

    public void isSuccess(Boolean success) {
        if(success) {
            //LoginActivityに通知する
            EventBus.getDefault().post(new SetMusicData(true));
        } else {
            EventBus.getDefault().post(new SetMusicData(false));
        }
    }
}
