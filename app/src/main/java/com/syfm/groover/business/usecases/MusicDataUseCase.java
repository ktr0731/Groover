package com.syfm.groover.business.usecases;

import android.util.Log;

import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.databases.MusicData;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2015/09/26.
 */
public class MusicDataUseCase implements ApiClient.MusicDataCallback, ApiClient.ScoreRankingCallback {

    Realm realm = Realm.getInstance(AppController.getInstance());

    // MusicDataを通知するためのクラス
    // MusicDataFragmentへ通知
    public class MusicDataEvent {
        public final RealmResults<MusicData> musicData;
        public MusicDataEvent(RealmResults<MusicData> musicData) {
            this.musicData = musicData;
        }
    }

    public class SetMusicData {
        public final boolean success;
        public SetMusicData(boolean success) {
            this.success = success;
        }
    }


    public void setMusicData() {
        ApiClient client = new ApiClient();
        client.fetchAllMusicData(this);
    }

    public void getMusicData() {
        RealmResults<MusicData> musicData = realm.where(MusicData.class).findAll();
        EventBus.getDefault().post(new MusicDataEvent(musicData));
    }

    public void getScoreRanking(String id, String ex_flag) {
        // TODO: ここでデータが有るかを判定
        ApiClient client = new ApiClient();
        client.fetchAllScoreRanking(id, ex_flag, this);
    }

    public void isSuccess(Boolean success) {
        if(success) {
            //MusicListFragmentに通知する
            EventBus.getDefault().post(new SetMusicData(true));
        } else {
            EventBus.getDefault().post(new SetMusicData(false));
        }
    }

    public void setScoreRankingIsSuccess(Boolean success) {
        if(success) {
            //ScoreRankingFragmentに通知する
            // データベースがあるという通知
            Log.d("ktr", "setScoreRankingIs" + success);
            EventBus.getDefault().post(true);
        }
    }
}
