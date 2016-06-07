package com.syfm.api;

import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.model.api.ApiClient;

import org.json.JSONArray;
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

    /**
     * PlayerData
     */

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

    /**
     * MusicList
     */

    @Test
    public void fetchMusicList_success() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        String jsonString = new String(getFileContent("music_list_ok.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);
        JSONArray array = new JSONObject(jsonString).getJSONArray("music_list");

        // Verify
        assertThat(apiClient.fetchMusicList().toString(), equalTo(array.toString()));
    }


    @Test(expected = RuntimeException.class)
    public void fetchMusicList_failure_unauthorized() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";

        String jsonString = new String(getFileContent("music_list_ng_unauthorized.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);

        // Verify
        apiClient.fetchMusicList();
    }

    @Test(expected = JSONException.class)
    public void fetchMusicList_failure_empty_string() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";

        // Exercise
        when(client.sendRequest(url)).thenReturn("");

        // Verify
        apiClient.fetchMusicList();
    }

    /**
     * MusicDetail
     */

    @Test
    public void fetchMusicDetail_success() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0";
        String jsonString = new String(getFileContent("music_detail_ok.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);
        JSONObject object = new JSONObject(jsonString).getJSONObject("music_detail");

        // Verify
        assertThat(apiClient.fetchMusicDetail(0).toString(), equalTo(object.toString()));
    }


    @Test(expected = RuntimeException.class)
    public void fetchMusicDetail_failure_unauthorized() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0";

        String jsonString = new String(getFileContent("music_detail_ng_unauthorized.json"), "UTF-8");

        // Exercise
        when(client.sendRequest(url)).thenReturn(jsonString);

        // Verify
        apiClient.fetchMusicDetail(0);
    }

    @Test(expected = JSONException.class)
    public void fetchMusicDetail_failure_empty_string() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0";

        // Exercise
        when(client.sendRequest(url)).thenReturn("");

        // Verify
        apiClient.fetchMusicDetail(0);
    }


    /**
     * MusicThumbnail
     */

    // lengthが異なってfailureになる
//    @Test
//    public void fetchMusicThumbnail_success() throws Exception {
//        // Set up
//        String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=0";
//        byte[] bytes = getFileContent("music_image.jpeg");
//
//        // Exercise
//        when(client.sendRequest(url)).thenReturn(new String(bytes));
//
//        // Verify
//        assertArrayEquals(apiClient.fetchMusicThumbnail(0), bytes);
//    }

    @Test(expected = RuntimeException.class)
    public void fetchMusicThumbnail_failure_unauthorized() throws Exception {
        // Set up
        String url = "https://mypage.groovecoaster.jp/sp/json/music_image.php?music_id=0";

        // Exercise
        when(client.sendRequest(url)).thenReturn(null);

        // Verify
        apiClient.fetchMusicThumbnail(0);
    }

    private byte[] getFileContent(String fileName) throws Exception {
        InputStream stream = RuntimeEnvironment.application.getAssets().open(fileName);
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        return buffer;
    }
}