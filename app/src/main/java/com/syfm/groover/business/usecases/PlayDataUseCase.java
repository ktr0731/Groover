package com.syfm.groover.business.usecases;

import com.syfm.groover.business.entities.PlayData;
import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.storage.PlayDataDBController;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class PlayDataUseCase implements ApiClient.PlayDataCallback {

    public class PlayDataEvent {
        public final PlayData playData;
        public PlayDataEvent(PlayData playData) {
            this.playData = playData;
        }
    }

    public class SetPlayData {
        public final boolean success;
        public SetPlayData(boolean success) {
            this.success = success;
        }
    }

    public void setPlayData() {
        ApiClient client = new ApiClient();
        client.fetchAllPlayData(this);
    }

    public void getPlayerData() {
        PlayData playData;
        //SQLiteから取得
        PlayDataDBController controller = new PlayDataDBController();
        if(controller.isExistsRecord()) {
            playData = controller.getLatest();
            EventBus.getDefault().post(new PlayDataEvent(playData));
        }

    }



    public void isSuccess(Boolean success) {
        if(success) {
            //LoginActivityに通知する
            EventBus.getDefault().post(new SetPlayData(true));
        } else {
            EventBus.getDefault().post(new SetPlayData(false));
        }
    }

}
