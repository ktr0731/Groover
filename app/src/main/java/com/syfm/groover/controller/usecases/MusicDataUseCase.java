package com.syfm.groover.controller.usecases;

import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.databases.Music;
import com.syfm.groover.model.utility.MusicFormatter;
import com.syfm.groover.model.utility.Utils;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2015/09/26.
 */
public class MusicDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    // MusicDataを通知するためのクラス
    // MusicDataFragmentへ通知
    public class MusicDataEvent {
        public final RealmResults<Music> music;

        public MusicDataEvent(RealmResults<Music> music) {
            this.music = music;
        }
    }

    public class SetMusicData {
        public final boolean success;
        public final String message;

        public SetMusicData(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }


    public void setMusicData() {
        ApiClient client = new ApiClient();

        deferred.when(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            try {
                int musicId;
                Music music;
                JSONArray musicListArray = client.fetchMusicList();

                for (int i = 0; i < musicListArray.length(); i++) {
                    // For debug
                    if (i > 3) break;

                    musicId = musicListArray.getJSONObject(i).getInt("music_id");

                    Utils.sleep();

                    // 3つの取得したデータを元にRealmのテーブルにフォーマットしたオブジェクトを返す
                    music = new MusicFormatter().getFormattedMusicObject(
                            musicId,
                            musicListArray.getJSONObject(i).getString("last_play_time"),
                            client.fetchMusicDetail(musicId),
                            client.fetchMusicThumbnail(musicId)
                    );

                    realm.copyToRealm(music);
                }
                realm.commitTransaction();
            } catch (IOException e) {
                e.printStackTrace();
                realm.cancelTransaction();
                EventBus.getDefault().post(new SetMusicData(false, "ミュージックデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
            } catch (JSONException e) {
                e.printStackTrace();
                realm.cancelTransaction();
                EventBus.getDefault().post(new SetMusicData(false, "JSONデータのパースに失敗しました。取得したデータが不正です。"));
            } catch (RuntimeException e) {
                e.printStackTrace();
                realm.cancelTransaction();
                EventBus.getDefault().post(new SetMusicData(false, e.getMessage()));
            } finally {
                realm.close();
            }
        }).done(callback -> {
            EventBus.getDefault().post(new SetMusicData(true, null));
        }).fail(callback -> {
            // TODO: catchの内容を捕捉したい
            EventBus.getDefault().post(new SetMusicData(false, "不明なエラーが発生しました。"));
        });
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
