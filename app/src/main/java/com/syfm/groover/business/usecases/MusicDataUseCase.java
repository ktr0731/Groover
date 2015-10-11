package com.syfm.groover.business.usecases;

import com.activeandroid.query.Select;
import com.syfm.groover.data.network.ApiClient;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.ResultData;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class MusicDataUseCase implements ApiClient.MusicDataCallback {

    // MusicDataを通知するためのクラス
    // MusicDataFragmentへ通知
    public class MusicDataEvent {
        public final ArrayList<List<ResultData>> resultData;
        public MusicDataEvent(ArrayList<List<ResultData>> resultData) {
            this.resultData = resultData;
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
        ArrayList<List<ResultData>> result = new ArrayList<>();
        List<MusicData> musicData = new Select().from(MusicData.class).orderBy("Id desc").execute();
        for (MusicData row : musicData) {
            result.add(MusicData.getAll(row));
        }
        EventBus.getDefault().post(new MusicDataEvent(result));
    }

    public void isSuccess(Boolean success) {
        if(success) {
            //MusicListFragmentに通知する
            EventBus.getDefault().post(new SetMusicData(true));
        } else {
            EventBus.getDefault().post(new SetMusicData(false));
        }
    }
}
