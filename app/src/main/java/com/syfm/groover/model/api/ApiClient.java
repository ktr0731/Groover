package com.syfm.groover.model.api;

import android.util.Log;

import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.model.constants.Const;
import com.syfm.groover.model.constants.SPConst;
import com.syfm.groover.model.databases.SharedPreferenceHelper;
import com.syfm.groover.model.databases.ScoreRankData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by lycoris on 2015/09/27.
 */
public class ApiClient {
    private Realm realm;

    public ClientInterface client = new ClientInterface() {
        @Override
        public String sendRequest(String target_url) throws IOException {
            Request request = new okhttp3.Request.Builder()
                    .url(target_url)
                    .get()
                    .build();

            Response response = AppController.getOkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.d("HttpRequest", "Bad Response");
                return null;
            }

            ResponseBody body = response.body();

            String json = body.string();
            body.close();

            return json;
        }

        @Override
        public byte[] sendRequestForByteArray(String target_url) throws IOException {
            Request request = new okhttp3.Request.Builder()
                    .url(target_url)
                    .get()
                    .build();

            Response response = AppController.getOkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.d("HttpRequest", "Bad Response");
                return null;
            }

            ResponseBody body = response.body();

            byte[] bytes = body.bytes();
            body.close();

            return bytes;
        }
    };

    /**
     * Fetches player_data.php from mypage of Groove Coaster.
     *
     * @return @{code JSONObject} Response JSON from player_data.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchPlayerData() throws IOException, JSONException {
        Log.d("PlayDataApi", "fetchPlayerData");
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString);

        checkAuthorization(object);

        return object;
    }

    /**
     * Fetches shop_sales_data.php from mypage of Groove Coaster.
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
     * MusicData API
     */

    /**
     * Fetches music_list.php from mypage of Groove Coaster.
     *
     * @throws IOException
     * @throws JSONException
     */
    public JSONArray fetchMusicList() throws IOException, JSONException {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        String music_list_data = "music_list";

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString);
        checkAuthorization(object);

        return object.getJSONArray(music_list_data);
    }

    /**
     * Fetches music_detail.php from mypage of Groove Coaster.
     *
     * @return {@code JSONObject} if fetching was successfully, {@code null} if cannot fetches data.
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchMusicDetail(int music_id) throws IOException, JSONException {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=";

        url += music_id;

        String jsonString = client.sendRequest(url);
        JSONObject object = new JSONObject(jsonString);
        checkAuthorization(object);

        return object.getJSONObject("music_detail");
    }

    /**
     * Fetches music_image from mypage of Groove Coaster.
     *
     * @param music_id The id of the music's
     * @return {@code byte[]} if fetching was successfully, {@code null} fetching was failed.
     * @throws IOException
     */
    public byte[] fetchMusicThumbnail(int music_id) throws IOException, RuntimeException {
        String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=";
        url += music_id;

        byte[] bytes = client.sendRequestForByteArray(url);

        if (bytes.length <= 0) {
            Log.d("ktr", "thumb error");
            throw new RuntimeException("サムネイルの取得に失敗しました。");
        }

        return bytes;
    }

    /**
     * Fetches score_ranking.php of the target music id.
     *
     * @param id   The target music's id
     * @param diff A difficulty of target music
     */
    public void fetchScoreRanking(final String id, final int diff) {
        final String url = "https://mypage.groovecoaster.jp/sp/json/score_ranking_bymusic_bydifficulty.php?music_id=" + id + "&difficulty=" + diff;

        realm = Realm.getDefaultInstance();

        String score_rank = "score_rank";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = AppController.getOkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                // TODO: エラー処理
                Log.d("ktr", "ApiClient.fetchScoreRanking response is not successful");
                return;
            }

            ResponseBody body = response.body();

            JSONArray array = new JSONObject(body.string()).getJSONArray(score_rank);

            body.close();

            if (array.length() <= 0) {
                return;
            }

            for (int i = 0; i < 5; i++) {
                realm.beginTransaction();
                ScoreRankData item = realm.createObjectFromJson(ScoreRankData.class, array.get(i).toString());
                item.setDiff(String.valueOf(diff));
                item.setId(id);
                realm.commitTransaction();
            }

        } catch (IOException e) {
            Log.d("ktr", e.toString());

        } catch (JSONException e) {
            Log.d("ktr", e.toString());
        }
    }

    /**
     * RankingData API
     */

    /**
     * Fetches ranking data from groovecoaster.jp
     *
     * @param RANKING_TYPE A type of fetches
     * @throws IOException
     */
    public void fetchRankingData(final String RANKING_TYPE) throws IOException {
        String url = "http://groovecoaster.jp/xml/fmj2100/rank/";

        switch (RANKING_TYPE) {
            case SPConst.LEVEL_ALL_RANKING:
                url += "all/rank_1.xml";
                break;

            case SPConst.LEVEL_SIMPLE_RANKING:
                url += "diff/0/rank_1.xml";
                break;

            case SPConst.LEVEL_NORMAL_RANKING:
                url += "diff/1/rank_1.xml";
                break;

            case SPConst.LEVEL_HARD_RANKING:
                url += "diff/2/rank_1.xml";
                break;

            case SPConst.LEVEL_EXTRA_RANKING:
                url += "diff/3/rank_1.xml";
                break;

            case SPConst.GENRE_JPOP_RANKING:
                url += "genre/J-POP/rank_1.xml";
                break;

            case SPConst.GENRE_ANIME_RANKING:
                url += "genre/ANIME/rank_1.xml";
                break;

            case SPConst.GENRE_VOCALOID_RANKING:
                url += "genre/VOCALOID/rank_1.xml";
                break;

            case SPConst.GENRE_TOUHOU_RANKING:
                url += "genre/TOUHOU/rank_1.xml";
                break;

            case SPConst.GENRE_GAME_RANKING:
                url += "genre/GAME/rank_1.xml";
                break;

            case SPConst.GENRE_VARIETY_RANKING:
                url += "genre/VARIETY/rank_1.xml";
                break;

            case SPConst.GENRE_ORIGINAL_RANKING:
                url += "genre/ORIGINAL/rank_1.xml";
                break;

            default:
                url += "all/rank_1.xml";
                break;
        }

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
        String value = body.string();

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setRankingData(RANKING_TYPE, value);
        }

        body.close();

    }

    /**
     * Fetches event ranking data from groovecoaster.jp
     *
     * @param SP_NAME The name of the SharedPreference for save this ranking data
     * @param number  The id of the target ranking
     * @throws IOException
     */
    public void fetchEventRankingData(final String SP_NAME, int number) throws IOException {
        String url = String.format("http://groovecoaster.jp/xml/fmj2100/rank/event/%03d/rank_1.xml", number + 1);

        Log.d("ktr", "url : " + url);

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
        String value = body.string();

        Log.d("ktr", value);

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setRankingData(SP_NAME, value);
        }

        body.close();
    }

    /**
     * Fetches event.xml to get a list of all event name.
     *
     * @throws IOException
     */
    public void fetchEventNameList() throws IOException {
        final String url = "http://groovecoaster.jp/xml/fmj2100/rank/event.xml";

        Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = AppController.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            // TODO: エラー処理
            Log.d("ktr", "[RankingDataUseCase] fetchEventNameList OkHttp isNotSuccessful");
            return;
        }

        ResponseBody body = response.body();
        String value = body.string();

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setEventNameList(value);
        }

        body.close();
    }

    /**
     * Now event API
     */

    // TODO: イベントがない時の処理

    /**
     * Fetches now event information.
     *
     * @return @{code JSONObject} Response JSON from event_data.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchCurrentEventDetail() throws IOException, JSONException {
        Log.d("CurrentEventApi", "fetchCurrentEventDetail");

        String url = "https://mypage.groovecoaster.jp/sp/json/event_data.php";

        JSONObject object = new JSONObject(client.sendRequest(url));

        checkAuthorization(object);

        return object;
    }

    // TODO: イベントがない時の処理
    /**
     * Fetches now event destination.
     * @return @{code JSONObject} Response JSON from event_destination.php
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject fetchCurrentEventDestination() throws IOException, JSONException {
        Log.d("CurrentEventApi", "fetchCurrentEventDestination");

        String url = "https://mypage.groovecoaster.jp/sp/json/event_destination.php";

        JSONObject object = new JSONObject(client.sendRequest(url));

        checkAuthorization(object);

        return object;
    }

    /**
     * Other Methods
     */

    // TODO: すごく汚いから治したい

    /**
     * Replaces {@code null} to safety value.
     *
     * @param obj The target object
     * @param key A key of target object
     */
    private void resultDataJsonReplaceNull(JSONObject obj, String key) {
        if (obj.isNull(key) && obj.has(key)) {
            JSONObject result = new JSONObject();
            try {
                result.put(Const.MUSIC_RESULT_ADLIB, 0);
                result.put(Const.MUSIC_RESULT_FULL_CHAIN, 0);
                result.put(Const.MUSIC_RESULT_IS_CLEAR_MARK, "false");
                result.put(Const.MUSIC_RESULT_IS_FAILED_MARK, "false");
                result.put(Const.MUSIC_RESULT_MAX_CHAIN, 0);
                result.put(Const.MUSIC_RESULT_LEVEL, "-");
                result.put(Const.MUSIC_RESULT_NO_MISS, 0);
                result.put(Const.MUSIC_LIST_PLAY_COUNT, 0);
                result.put(Const.MUSIC_RESULT_RATING, "-");
                result.put(Const.MUSIC_RESULT_SCORE, 0);
                obj.put(key, result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    // TODO: すごく汚いから治したい

    /**
     * Replaces {@code null} to safety value.
     *
     * @param array The target array
     */
    private void userRankJsonReplaceNull(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            if (array.isNull(i)) {
                JSONObject rank = new JSONObject();
                try {
                    rank.put(Const.MUSIC_USER_RANK, 0);
                    array.put(i, rank);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks status from JSON
     *
     * @param object @{code JSONObject} Fetched JSON data
     * @throws JSONException If status is 1
     */
    private void checkAuthorization(JSONObject object) throws JSONException {
        if (object.getInt("status") == 1) {
            throw new RuntimeException("Unauthorized access");
        }
    }

    /**
     * Send post request for fetching.
     * sendRequest() is return body string written by JSON.
     */
    public interface ClientInterface {
        String sendRequest(String target_url) throws IOException;
        byte[] sendRequestForByteArray(String target_url) throws IOException;
    }

}
