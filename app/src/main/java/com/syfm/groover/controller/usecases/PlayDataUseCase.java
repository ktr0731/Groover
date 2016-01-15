package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.network.AppController;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.databases.AverageScore;
import com.syfm.groover.model.storage.databases.PlayerData;
import com.syfm.groover.model.storage.databases.ShopSalesData;
import com.syfm.groover.model.storage.databases.StageData;

import org.jdeferred.android.AndroidDeferredManager;

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
        //client.fetchPlayData(this);

        deferred.when(() -> {
            client.fetchPlayerData();
            sleep();
        }).then(result -> {
            client.fetchShopSalesData();
            sleep();
        }).then(result -> {
            client.fetchAverageScore();
            sleep();
        }).then(result1 -> {
            client.fetchStageData();
            sleep();
        }).done(callback -> {
            EventBus.getDefault().post(new SetPlayData(true));
        }).fail(callback -> {
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

    private void sleep() {
        try {
            Thread.sleep(Const.TIME);
        } catch (InterruptedException e) {
            Log.d("ktr", e.toString());
        }
    }

}
