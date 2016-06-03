package com.syfm.api;

import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.model.api.PlayDataApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lycoris on 2016/05/17.
 */

@RunWith(CustomRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PlayDataApiTest {
    PlayDataApi api;
    PlayDataApi.PlayDataClient client;


    @Before
    public void setUp() throws Exception {
        api = new PlayDataApi();
        client = mock(PlayDataApi.PlayDataClient.class);

        api.client = client;
    }

    @Test
    public void fetchPlayerData_successful() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        String player_data = "player_data";

        InputStream stream = RuntimeEnvironment.application.getAssets().open("player_data_ok.json");
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        String jsonString = new String(buffer, "UTF-8");
        when(client.sendRequest(url)).thenReturn(jsonString);

        JSONObject object = new JSONObject(jsonString);
        // Exercise
        assertThat(api.fetchPlayerData().toString(), equalTo(object.getJSONObject(player_data).toString()));
    }

    @Test(expected = JSONException.class)
    public void fetchPlayerData_failure_unauthorized() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        InputStream stream = RuntimeEnvironment.application.getAssets().open("player_data_ng_unauthorized.json");
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        String jsonString = new String(buffer, "UTF-8");
        when(client.sendRequest(url)).thenReturn(jsonString);

        // Exercise
        api.fetchPlayerData();
    }

    @Test(expected = JSONException.class)
    public void fetchPlayerData_failure_empty_string() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        when(client.sendRequest(url)).thenReturn("");

        // Exercise
        api.fetchPlayerData();
    }

    @Test(expected = IOException.class)
    public void fetchPlayerData_failure_disconnection() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        when(client.sendRequest(url)).thenThrow(IOException.class);

        // Exercise
        api.fetchPlayerData();
    }
}