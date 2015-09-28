package com.syfm.groover.business.usecases;

import android.util.Log;

import com.syfm.groover.business.entities.PlayData;
import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.storage.PlayDataDBController;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class PlayDataUseCase {

    public class PlayDataEvent {
        public final PlayData playData;
        public PlayDataEvent(PlayData playData) {
            this.playData = playData;
        }

    }

    public void getPlayData() {

        PlayData playData;

        //SQLiteから取得
        PlayDataDBController controller = new PlayDataDBController();
        if(controller.isExistsRecord()) {
            Log.d("Unko", "SQLiteから取得します");
            playData = controller.getLatest();
            EventBus.getDefault().post(new PlayDataEvent(playData));
            return;
        }

        Log.d("Unko", "ApiClientから取得します");
        ApiClient client = new ApiClient();
        client.getPlayData(new ApiClient.PlayDataListener() {
            @Override
            public void onSuccess(PlayData playData) {
                EventBus.getDefault().post(new PlayDataEvent(playData));
            }

            @Override
            public void onFailure() {

            }
        });

    }

}
