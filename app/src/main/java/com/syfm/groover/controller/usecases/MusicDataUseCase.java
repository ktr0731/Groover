package com.syfm.groover.controller.usecases;

import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.AppController;
import com.syfm.groover.model.storage.databases.MusicData;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONException;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2015/09/26.
 */
public class MusicDataUseCase {

    Realm realm = Realm.getInstance(AppController.getInstance());
    private AndroidDeferredManager deferred = new AndroidDeferredManager();

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
        public final String  message;

        public SetMusicData(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }


    public void setMusicData() {

        deferred.when(() -> {
            ApiClient client = new ApiClient();
            try {
                client.fetchMusicData();
            } catch (IOException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetMusicData(false, "ミュージックデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
            } catch (JSONException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetMusicData(false, "JSONデータのパースに失敗しました。取得したデータが不正です。"));
            }
        }).done(callback -> {
            EventBus.getDefault().post(new SetMusicData(true, null));
        }).fail(callback -> {
            EventBus.getDefault().post(new SetMusicData(false, "不明なエラーが発生しました。"));
        });
    }

    public void getMusicData() {
        RealmResults<MusicData> musicData = realm.where(MusicData.class).findAll();
        EventBus.getDefault().post(new MusicDataEvent(musicData));
    }

    public void getScoreRanking(String id, String ex_flag) {
        // TODO: ここでデータが有るかを判定
        // TODO: 例外の修正

        final String mId;

        if (100 - Integer.parseInt(id) > 0) {
            mId = "0" + id;
        } else {
            mId = id;
        }

        final int max_diff;
        if (Integer.parseInt(ex_flag) == 1) {
            max_diff = 4;
        } else {
            max_diff = 3;
        }

        ApiClient client = new ApiClient();
        deferred.when(() -> {
            for (int diff = 0; diff < max_diff; diff++) {
                client.fetchScoreRanking(mId, diff);
            }
        }).done(callback -> {
            EventBus.getDefault().post(true);
        }).fail(callback -> {
            callback.printStackTrace();
            EventBus.getDefault().post(false);
        });
    }
}
