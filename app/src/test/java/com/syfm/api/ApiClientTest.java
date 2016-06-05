package com.syfm.api;

import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.model.api.ApiClient;

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
public class ApiClientTest {
    ApiClient apiClient;
    ApiClient.ClientInterface client;


    @Before
    public void setUp() throws Exception {
        apiClient = new ApiClient();
        client = mock(ApiClient.ClientInterface.class);

        apiClient.client = client;
    }

    @Test
    public void fetchPlayerData_successful() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        String player_data = "player_data";

        String jsonString = new String(getFileContent("player_data_ok.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);

        JSONObject object = new JSONObject(jsonString);

        // Verify
        assertThat(apiClient.fetchPlayerData().toString(), equalTo(object.toString()));
    }

    @Test(expected = RuntimeException.class)
    public void fetchPlayerData_failure_unauthorized() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        String jsonString = new String(getFileContent("player_data_ng_unauthorized.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);

        // Verify
        apiClient.fetchPlayerData();
    }

    @Test(expected = JSONException.class)
    public void fetchPlayerData_failure_empty_string() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        // Exercise
        when(client.sendRequest(url)).thenReturn("");

        // Verify
        apiClient.fetchPlayerData();
    }

    @Test(expected = IOException.class)
    public void fetchPlayerData_failure_disconnection() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        // Exercise
        when(client.sendRequest(url)).thenThrow(IOException.class);

        // Verify
        apiClient.fetchPlayerData();
    }

//    @Test
//    public void fetchMusicList_success() throws Exception {
//        // Set up
//        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
//
//        // Exercise
//        when(client.sendRequest(url)).thenReturn()
//    }

    private byte[] getFileContent(String fileName) throws Exception {
        InputStream stream = RuntimeEnvironment.application.getAssets().open(fileName);
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        return buffer;
    }
}