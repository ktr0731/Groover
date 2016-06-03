//package com.syfm.api;
//
//import android.test.AndroidTestCase;
//
//import com.syfm.groover.model.storage.databases.PlayerData;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.InputStream;
//
//import static org.
//
///**
// * Created by lycoris on 2016/05/17.
// */
//public class PlayDataApiTest extends AndroidTestCase {
//    PlayDataApi api;
//    PlayDataApi.PlayDataClient client;
//
//
//    @Before
//    public void setUp() throws Exception {
//        api    = new PlayDataApi();
//        client = mock(PlayDataApi.PlayDataClient.class);
//
//        api.client = client;
//    }
//
//    @Test
//    public void fetchPlayerData_successful() throws Exception {
//        // Set up
//        InputStream stream = this.getContext().getResources().getAssets().open("player_data_ok.json");
//        byte[] buffer = new byte[stream.available()];
//        stream.read(buffer);
//        stream.close();
//
//        JSONObject object = new JSONObject(buffer.toString());
//    }
//}