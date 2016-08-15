package com.syfm.groover.controller.usecases;

import com.syfm.groover.model.api.ApiClient;

import org.jdeferred.android.AndroidDeferredManager;

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

            }
        })
    }
}
