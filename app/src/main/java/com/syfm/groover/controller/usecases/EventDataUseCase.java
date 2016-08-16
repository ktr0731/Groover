package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.databases.CurrentEvent.AvatarAward;
import com.syfm.groover.model.databases.CurrentEvent.CurrentEventData;
import com.syfm.groover.model.databases.CurrentEvent.MusicAward;
import com.syfm.groover.model.databases.CurrentEvent.TitleAward;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by lycoris on 2015/09/26.
 */
public class EventDataUseCase {

    public void setEventData() {
        ApiClient apiClient = new ApiClient();

        AndroidDeferredManager deferred = new AndroidDeferredManager();

        deferred.when(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            try {
                JSONObject object = apiClient.fetchCurrentEventDetail();

                // 現時点では何も報酬をもらえない時
                if (!object.getJSONObject("event_data").getJSONObject("user_event_data").isNull("award_data")) {
                    convertJSONArrayToJSONObject(object);
                }

                realm.createObjectFromJson(CurrentEventData.class, object.getJSONObject("event_data"));

                realm.commitTransaction();
            } catch (IOException e) {
                realm.cancelTransaction();
                e.printStackTrace();
            } catch (JSONException e) {
                realm.cancelTransaction();
                e.printStackTrace();
            } finally {
                realm.close();
            }
        });
    }

    public JSONObject convertJSONArrayToJSONObject(JSONObject object) throws JSONException {
        JSONObject awardData = object.getJSONObject("event_data").getJSONObject("user_event_data").getJSONObject("award_data");
        JSONArray avatarAward = awardData.getJSONArray("avatar_award");
        JSONObject newAvatarAward = new JSONObject();

        for (int i = 0; i < avatarAward.length(); i++) {
            newAvatarAward.accumulate("name", avatarAward.get(i).toString());
            Log.d("Hogeeeeeeeeeee", avatarAward.get(i).toString());
        }

        awardData.accumulate("avatar_award", newAvatarAward);
        object.accumulate("award_data", awardData);

        return object;
    }
}
