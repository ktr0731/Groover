package com.syfm.groover.model.api;

import android.util.Log;

import com.syfm.groover.controller.entities.AppController;

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
    public PlayDataClient client = new PlayDataClient() {
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

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString);

        checkAuthorization(object);

        return object.getJSONObject("player_data");
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

        JSONObject object = new JSONObject(client.sendRequest(url));

        checkAuthorization(object);

        return object;
    }

    /**
     * Send post request for fetching.
     * sendRequest() is return body string written by JSON.
     */
    public interface PlayDataClient {
        String sendRequest(String target_url) throws IOException;
    }

    /**
     * Checks status from JSON
     *
     * @param object @{code JSONObject} Fetched JSON data
     * @throws JSONException If status is 1
     */
    private void checkAuthorization(JSONObject object) throws JSONException {
        if (object.getInt("status") == 1) {
            throw new JSONException("Unauthorized access");
        }
    }
}