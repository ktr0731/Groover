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
    private Realm realm;

    /**
     * Fetches player_data.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchPlayerData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("ktr", "player data");
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        String player_data = "player_data";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = AppController.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            // TODO: エラー処理
            Log.d("ktr", "errordesu");
            return;
        }

        ResponseBody body = response.body();
        JSONObject object = new JSONObject(body.string()).getJSONObject(player_data);

        body.close();

        if (object.length() <= 0) {
            Log.d("ktr", "length0");
            return;
        }

        realm.beginTransaction();
        PlayerData playerData = realm.createObjectFromJson(PlayerData.class, object.toString());
        playerData.setDate(DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString());
        realm.commitTransaction();
    }

    /**
     * Fetches shop_sales_data.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchShopSalesData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("ktr", "shop data");
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = AppController.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            // TODO: エラー処理
            return;
        }
        ResponseBody body = response.body();
        JSONObject object = new JSONObject(body.string());

        body.close();

        if (object.length() <= 0) {
            return;
        }

        realm.beginTransaction();
        realm.createObjectFromJson(ShopSalesData.class, object);
        realm.commitTransaction();
    }

    /**
     * Fetches average_score.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchAverageScore() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("ktr", "ave data");

        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        String average_data = "average";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = AppController.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            // TODO: エラー処理
            return;
        }

        ResponseBody body = response.body();

        JSONObject object = new JSONObject(body.string()).getJSONObject(average_data);

        body.close();

        if (object.length() <= 0) {
            return;
        }

        realm.beginTransaction();
        realm.createObjectFromJson(AverageScore.class, object.toString());
        realm.commitTransaction();
    }

    /**
     * Fetches stage_data.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public void fetchStageData() throws IOException, JSONException {
        realm = Realm.getInstance(AppController.getContext());
        Log.d("ktr", "stage data");

        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        String stage_data = "stage";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = AppController.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            // TODO: エラー処理
            return;
        }

        ResponseBody body = response.body();

        JSONObject object = new JSONObject(body.string()).getJSONObject(stage_data);

        body.close();

        if (object.length() <= 0) {
            return;
        }

        realm.beginTransaction();
        realm.createObjectFromJson(StageData.class, object.toString());
        realm.commitTransaction();
    }
}
