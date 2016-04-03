package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.Utils;
import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.network.AppController;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.databases.AverageScore;
import com.syfm.groover.model.storage.databases.PlayerData;
import com.syfm.groover.model.storage.databases.ShopSalesData;
import com.syfm.groover.model.storage.databases.StageData;

import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONException;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import io.realm.Realm;

/**
 * Created by lycoris on 2015/09/26.
 */
public class PlayDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    // PlayDataを通知するためのクラス
    // PlayDataFragmentへ通知
    public class PlayDataEvent {
        public final PlayerData playerData;
        public final ShopSalesData salesData;
        public final AverageScore averageScore;
        public final StageData stageData;
        public PlayDataEvent(PlayerData playerData, ShopSalesData salesData, AverageScore averageScore, StageData stageData) {
            this.playerData   = playerData;
            this.salesData    = salesData;
            this.averageScore = averageScore;
            this.stageData    = stageData;
        }
    }

    // ログイン判定用のクラス
    // LoginActivityへ通知
    public class SetPlayData {
        public final boolean success;
        public SetPlayData(boolean success) {
            this.success = success;
        }
    }

    public void setPlayData() {
        ApiClient client = new ApiClient();

        // なぜかnetworkOnMainThreadException
        Promise p = deferred.when(() -> {
            try {
                client.fetchPlayerData();
                client.fetchShopSalesData();
                client.fetchAverageScore();
                client.fetchStageData();
            } catch (IOException e) {
                Log.d("ktr", e.toString());
            } catch (JSONException e) {
                Log.d("ktr", e.toString());
            }
        }).done(callback -> {
            Log.d("ktr", "setPlayDataDone");
            EventBus.getDefault().post(new SetPlayData(true));
        }).fail(callback -> {
            Log.d("ktrerror", callback.getMessage().toString());
            EventBus.getDefault().post(new SetPlayData(false));
        });
    }

    public void getPlayData() {
        Realm realm = Realm.getInstance(AppController.getInstance());
        PlayerData player    = realm.where(PlayerData.class).findFirst();
        ShopSalesData sales  = realm.where(ShopSalesData.class).findFirst();
        AverageScore average = realm.where(AverageScore.class).findFirst();
        StageData stage      = realm.where(StageData.class).findFirst();

        EventBus.getDefault().post(new PlayDataEvent(player, sales, average, stage));
    }
}
