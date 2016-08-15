package com.syfm.groover.controller.usecases;

import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.databases.CurrentEvent.CurrentEventData;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;

/**
 * Created by lycoris on 2015/09/26.
 */
public class EventDataUseCase {

    public class SetEventData {

    }

    public void setEventData() {
        ApiClient apiClient = new ApiClient();

        AndroidDeferredManager deferred = new AndroidDeferredManager();

        deferred.when(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            try {
                JSONObject object = apiClient.fetchCurrentEventDetail();

                realm.createObjectFromJson(CurrentEventData.class, object);
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
}
