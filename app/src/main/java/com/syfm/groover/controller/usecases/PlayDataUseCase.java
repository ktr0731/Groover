package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.databases.SharedPreferenceHelper;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class PlayDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    // ログイン判定用のクラス
    // LoginActivityへ通知
    public class SetPlayData {
        public final boolean success;
        public final String message;

        public SetPlayData(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    public void setPlayData() {
        ApiClient apiClient = new ApiClient();

        deferred.when(() -> {
            try {
                SharedPreferenceHelper.setPlayerData(apiClient.fetchPlayerData());
            } catch (IOException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetPlayData(false, "プレイデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
            } catch (JSONException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetPlayData(false, "JSONデータのパースに失敗しました。取得したデータが不正です。"));
            }

        }).done(callback -> {
            Log.d("ktr", "setPlayDataDone");
            EventBus.getDefault().post(new SetPlayData(true, null));
        }).fail(e -> {
            e.printStackTrace();
            EventBus.getDefault().post(new SetPlayData(false, e.getMessage()));
        });
    }

    public void getPlayData() {
        try {
            JSONObject playerDataJson = SharedPreferenceHelper.getPlayerData();

            EventBus.getDefault().post(playerDataJson);
        } catch (JSONException e) {
            // TODO: 保存されたデータが委譲なので再取得したほうが良さ気
            e.printStackTrace();
        }
    }
}
