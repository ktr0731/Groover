package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.network.AppController;
import com.syfm.groover.model.storage.databases.MusicData;

import org.jdeferred.Promise;
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

        public SetMusicData(boolean success) {
            this.success = success;
        }
    }


    public void setMusicData() {

        deferred.when(() -> {
            ApiClient client = new ApiClient();
            try {
                client.fetchMusicData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).done(callback -> {
            EventBus.getDefault().post(new SetMusicData(true));
        }).fail(callback -> {
            EventBus.getDefault().post(new SetMusicData(false));
        });
    }

    public void getMusicData() {
        RealmResults<MusicData> musicData = realm.where(MusicData.class).findAll();
        EventBus.getDefault().post(new MusicDataEvent(musicData));
    }

    public void getScoreRanking(String id, String ex_flag) {
        // TODO: ここでデータが有るかを判定

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
            Log.d("ktr", "deferred exception: " + callback.getLocalizedMessage());
            EventBus.getDefault().post(false);
        });
    }
}
