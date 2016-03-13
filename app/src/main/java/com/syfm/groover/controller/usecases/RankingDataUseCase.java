package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.storage.SharedPreferenceHelper;

import org.jdeferred.android.AndroidDeferredManager;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class RankingDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    public void setRankingData(String LEVEL_TYPE) {
        ApiClient client = new ApiClient();

        deferred.when(() -> {
            client.fetchLevelRankingData(LEVEL_TYPE);
        }).done(callback -> {
            return;
        }).fail(callback -> {
            Log.d("ktr", callback.getStackTrace().toString());
        });
    }

    public void getRankingData(final String LEVEL_TYPE) {
        String value = SharedPreferenceHelper.getLevelRanking(LEVEL_TYPE);
        if (value == "") {
            setRankingData(LEVEL_TYPE);
            value = SharedPreferenceHelper.getLevelRanking(LEVEL_TYPE);
        }

        Log.d("ktr", "EventBus");
        EventBus.getDefault().post(value);
    }
}
