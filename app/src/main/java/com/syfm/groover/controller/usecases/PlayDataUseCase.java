package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.databases.PlayerData;
import com.syfm.groover.model.databases.SharedPreferenceHelper;
import com.syfm.groover.model.utility.PlayerDataFormatter;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

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
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            try {
                PlayerData data = new PlayerDataFormatter().getFormattedPlayerDataObject(apiClient.fetchPlayerData());
                realm.copyToRealm(data);
                realm.commitTransaction();
            } catch (IOException e) {
                realm.cancelTransaction();
                e.printStackTrace();
                EventBus.getDefault().post(new SetPlayData(false, "プレイデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
            } catch (JSONException e) {
                realm.cancelTransaction();
                e.printStackTrace();
                EventBus.getDefault().post(new SetPlayData(false, "JSONデータのパースに失敗しました。取得したデータが不正です。"));
            } finally {
                realm.close();
            }

        }).done(callback -> {
            Log.d("ktr", "setPlayDataDone");
            EventBus.getDefault().post(new SetPlayData(true, null));
        }).fail(e -> {
            e.printStackTrace();
            EventBus.getDefault().post(new SetPlayData(false, e.getMessage()));
        });
    }
}
