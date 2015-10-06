package com.syfm.groover.business.usecases;

import android.util.Log;

import com.activeandroid.query.Select;
import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.storage.databases.AverageScore;
import com.syfm.groover.data.storage.databases.PlayerData;
import com.syfm.groover.data.storage.databases.ShopSalesData;
import com.syfm.groover.data.storage.databases.StageData;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class PlayDataUseCase implements ApiClient.PlayDataCallback {

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
        client.fetchAllPlayData(this);
    }

    public void getPlayData() {
        //SQLiteから取得
        PlayerData player    = new Select().from(PlayerData.class).orderBy("_id desc").executeSingle();
        ShopSalesData sales  = new Select().from(ShopSalesData.class).orderBy("_id desc").executeSingle();
        AverageScore average = new Select().from(AverageScore.class).orderBy("_id desc").executeSingle();
        StageData  stageData = new Select().from(StageData.class).orderBy("_id desc").executeSingle();
        EventBus.getDefault().post(new PlayDataEvent(player, sales, average, stageData));
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
