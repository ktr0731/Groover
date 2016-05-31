package com.syfm.groover.model.api;

import com.syfm.groover.model.storage.databases.PlayerData;

import org.junit.Before;
import org.junit.Test;

import io.realm.Realm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lycoris on 2016/05/17.
 */
public class PlayDataApiTest {
    PlayDataApi api;
    PlayDataApi.PlayDataClient client;
    Realm realm;


    @Before
    public void setUp() throws Exception {
        api    = new PlayDataApi();
        client = mock(PlayDataApi.PlayDataClient.class);
        realm  = mock(Realm.class);

        api.client = client;
        api.realm  = realm;
    }

    @Test
    public void fetchPlayerData_successful() throws Exception {
        // Set up
        // 追加した要素が取り出せるかを確認する
        when(realm.where(PlayerData.class).findFirst()).thenReturn()
        api.fetchPlayerData();
    }
}