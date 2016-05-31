package com.syfm.groover.model.api;

import android.util.Log;

import com.syfm.groover.model.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lycoris on 2016/05/11.
 */
public class PlayDataApi {
    PlayDataClient client = new PlayDataClient() {
        @Override
        public String sendRequest(String target_url) throws IOException {
            Request request = new okhttp3.Request.Builder()
                    .url(target_url)
                    .get()
                    .build();

            Response response = AppController.getOkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.d("PlayDataClient", "Bad Response");
                return null;
            }

            ResponseBody body = response.body();

            String json = body.string();
            body.close();

            return json;
        }
    };

    /**
     * Fetches player_data.php from mypage of Groove Coaster.
     *
     * @return @{code JSONObject} Response json from player_data.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchPlayerData() throws IOException, JSONException {
        Log.d("PlayDataApi", "fetchPlayerData");
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        String player_data = "player_data";

        String jsonString = client.sendRequest(url);
        return new JSONObject(jsonString).getJSONObject(player_data);
    }

    /**
     * Fetches shop_sales_data.php from mypage of Groove
     *
     * @return @{code JSONObject} Response json from shop_sales_data.php
     * @throws IOException
     * @throws JSONException
     */
   public JSONObject fetchShopSalesData() throws IOException, JSONException {
        Log.d("PlayDataApi", "fetchShopSalesData");
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";

        String jsonString = client.sendRequest(url);
        return new JSONObject(jsonString);
    }

    /**
     * Fetches average_score.php from mypage of Groove Coaster.
     *
     * @return @{code JSONObject} Response json from average_score.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchAverageScore() throws IOException, JSONException {
        Log.d("PlayDataApi", "fetchAverageScore");

        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        String average_data = "average";

        String jsonString = client.sendRequest(url);
        return new JSONObject(jsonString).getJSONObject(average_data);
    }

    /**
     * Fetches stage_data.php from mypage of Groove Coaster.
     *
     * @return @{code JSONObject} Response json from stage_data.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchStageData() throws IOException, JSONException {
        Log.d("PlayDataApi", "fetchStageData");

        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        String stage_data = "stage";

        String jsonString = client.sendRequest(url);
        return new JSONObject(jsonString).getJSONObject(stage_data);
    }

    /**
     * Send post request for fetching.
     * sendRequest() is return body string written by JSON.
     */
    public interface PlayDataClient {
        String sendRequest(String target_url) throws IOException;
    }
}