package com.syfm.groover.controller.usecases;

import com.syfm.groover.model.Utils;
import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.AppController;
import com.syfm.groover.model.storage.databases.Music;
import com.syfm.groover.model.storage.databases.MusicData;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        ApiClient client = new ApiClient();
        Realm realm      = Realm.getInstance(AppController.getContext());

        deferred.when(() -> {
            try {
                int music_id;
                String title;
                String artist;
                String skin;
                byte[] thumbnail;
                boolean favorite;

                JSONObject musicDetailJsonObject;
                JSONObject musicDetailObject;
                JSONObject simpleResultObject;
                JSONObject normalResultObject;
                JSONObject hardResultObject;
                JSONObject extraResultObject;

                JSONObject musicObject;

                JSONArray musicListArray = client.fetchMusicList();
                for (int i = 0; i < musicListArray.length(); i++) {
                    // For debug
                    if (i > 3) break;

                    music_id = musicListArray.getJSONObject(i).getInt("music_id");

                    Utils.sleep();

                    musicDetailJsonObject = client.fetchMusicDetail(music_id);
                    musicDetailObject     = musicDetailJsonObject.getJSONObject("music_detail");

                    thumbnail             = client.fetchMusicThumbnail(music_id);

                    // TODO: 抽出したほうが良い?
                    title    = musicListArray.getJSONObject(i).getString("music_title");
                    artist   = musicDetailObject.getString("artist");
                    skin     = musicDetailObject.getString("skin_name");
                    favorite = musicDetailObject.getInt("fav_flg") == 1;

                    musicObject = new JSONObject();
                    musicObject.accumulate("id", music_id);
                    musicObject.accumulate("title", title);
                    realm.createObjectFromJson(Music.class, object);
                }
            } catch (IOException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetMusicData(false, "ミュージックデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
            } catch (JSONException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetMusicData(false, "JSONデータのパースに失敗しました。取得したデータが不正です。"));
            } catch (RuntimeException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SetMusicData(false, e.getMessage()));
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
