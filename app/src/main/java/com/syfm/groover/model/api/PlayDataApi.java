package com.syfm.groover.model.api;

import android.text.format.DateFormat;
import android.util.Log;

import com.syfm.groover.model.AppController;
import com.syfm.groover.model.storage.databases.AverageScore;
import com.syfm.groover.model.storage.databases.PlayerData;
import com.syfm.groover.model.storage.databases.ShopSalesData;
import com.syfm.groover.model.storage.databases.StageData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import io.realm.Realm;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lycoris on 2016/05/11.
 */
public class PlayDataApi {
    Realm realm;
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
     * @throws IOException
     * @throws JSONException
     */
    public void fetchPlayerData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("PlayDataApi", "fetchPlayerData");
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        String player_data = "player_data";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString).getJSONObject(player_data);

        realm.beginTransaction();
        PlayerData playerData = realm.createObjectFromJson(PlayerData.class, object.toString());
        playerData.setDate(DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString());
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Fetches shop_sales_data.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchShopSalesData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("PlayDataApi", "fetchShopSalesData");
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString);

        realm.beginTransaction();
        realm.createObjectFromJson(ShopSalesData.class, object);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Fetches average_score.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchAverageScore() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("PlayDataApi", "fetchAverageScore");

        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        String average_data = "average";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString).getJSONObject(average_data);

        realm.beginTransaction();
        realm.createObjectFromJson(AverageScore.class, object.toString());
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Fetches stage_data.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchStageData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("PlayDataApi", "fetchStageData");

        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        String stage_data = "stage";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString).getJSONObject(stage_data);

        realm.beginTransaction();
        realm.createObjectFromJson(StageData.class, object.toString());
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Send post request for fetching.
     * sendRequest() is return body string written by JSON.
     */
    public interface PlayDataClient {
        String sendRequest(String target_url) throws IOException;
    }
}