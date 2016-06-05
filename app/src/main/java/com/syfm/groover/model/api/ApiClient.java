package com.syfm.groover.model.api;

import android.util.Log;

import com.syfm.groover.model.AppController;
import com.syfm.groover.model.Utils;
import com.syfm.groover.model.storage.Constants.Const;
import com.syfm.groover.model.storage.Constants.SPConst;
import com.syfm.groover.model.storage.SharedPreferenceHelper;
import com.syfm.groover.model.storage.databases.MusicData;
import com.syfm.groover.model.storage.databases.ResultData;
import com.syfm.groover.model.storage.databases.ScoreRankData;
import com.syfm.groover.model.storage.databases.UserRank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
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
//
//    /**
//     * Fetches music_list.php from mypage of Groove Coaster.
//     *
//     * @throws IOException
//     * @throws JSONException
//     */
//    public void fetchMusicList() throws IOException, JSONException {
//        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
//        String music_list_data = "music_list";
//
//        String jsonString = client.sendRequest(url);
//
//        JSONArray array = new JSONObject(jsonString).getJSONArray(music_list_data);
//
//        for (int i = 0; i < array.length(); i++) {
//            // For DEBUG
//            if (i > 3) {
//                break;
//            }
//
//            Utils.sleep();
//
//            realm = Realm.getInstance(AppController.getContext());
//            realm.beginTransaction();
//
//            try {
//                MusicData musicData = fetchMusicDetail(array.getJSONObject(i), i, 3 - 1); //実際はlist.size() -1
//                byte[] thumbnail = fetchMusicThumbnail(array.getJSONObject(i).getInt(Const.MUSIC_LIST_MUSIC_ID), musicData, i, 3 - 1);
//
//                if (musicData == null || thumbnail == null) {
//                    Log.d("ktr", "fetchMusicList Error");
//                    realm.cancelTransaction();
//                }
//
//                musicData.setMusic_thumbnail(thumbnail);
//                realm.commitTransaction();
//            } catch (IOException e) {
//                realm.cancelTransaction();
//            } catch (JSONException e) {
//                realm.cancelTransaction();
//            } catch (RealmException e) {
//                realm.cancelTransaction();
//            }
//        }
//    }

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

        return object;
    }

//    /**
//     * Fetches music_detail.php from mypage of Groove Coaster.
//     *
//     * @param music A fetched list by fetchMusicList()
//     * @param count The index of music list
//     * @param size  A size of music list
//     * @return {@code MusicData} if fetching was successfully, {@code null} if cannot fetches data.
//     * @throws IOException
//     * @throws JSONException
//     */
//    public MusicData fetchMusicDetail(final JSONObject music, final int count, final int size) throws IOException, JSONException {
//        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=";
//        String music_detail_data = "music_detail";
//        String simple = "simple_result_data";
//        String normal = "normal_result_data";
//        String hard = "hard_result_data";
//        String extra = "extra_result_data";
//        String user_rank = "user_rank";
//        String music_id = "music_id";
//
//        url += music.getString(music_id);
//
//        Request request = new okhttp3.Request.Builder()
//                .url(url)
//                .get()
//                .build();
//
//        Response response = AppController.getOkHttpClient().newCall(request).execute();
//        if (!response.isSuccessful()) {
//            // TODO: エラー処理
//            return null;
//        }
//
//        ResponseBody body = response.body();
//
//        JSONObject object = new JSONObject(body.string()).getJSONObject(music_detail_data);
//
//        body.close();
//
//        if (object.length() <= 0) {
//            return null;
//        }
//
//        resultDataJsonReplaceNull(object, simple);
//        resultDataJsonReplaceNull(object, normal);
//        resultDataJsonReplaceNull(object, hard);
//        resultDataJsonReplaceNull(object, extra);
//
//        userRankJsonReplaceNull(object.getJSONArray(user_rank));
//
//        MusicData data = realm.createObjectFromJson(MusicData.class, object);
//        data.setLast_play_time(music.getString(Const.MUSIC_LIST_LAST_PLAY_TIME));
//        data.setPlay_count(music.getInt(Const.MUSIC_LIST_PLAY_COUNT));
//
//        // 各難易度をMusicDataの子としてインサート
//        // 要素にNULLがあると挙動がおかしくなるので気をつける
//
//        ResultData resultSimple = realm.createObjectFromJson(ResultData.class, object.getJSONObject(simple).toString());
//        data.getResult_data().add(resultSimple);
//
//        ResultData resultNormal = realm.createObjectFromJson(ResultData.class, object.getJSONObject(normal).toString());
//        data.getResult_data().add(resultNormal);
//
//        ResultData resultHard = realm.createObjectFromJson(ResultData.class, object.getJSONObject(hard).toString());
//        data.getResult_data().add(resultHard);
//
//        ResultData resultExtra = realm.createObjectFromJson(ResultData.class, object.getJSONObject(extra).toString());
//        data.getResult_data().add(resultExtra);
//
//        // UserRankの整形
//        JSONArray userRankRaw = object.getJSONArray(user_rank);
//
//        for (int i = 0; i < userRankRaw.length(); i++) {
//            UserRank userRank = realm.createObjectFromJson(UserRank.class, userRankRaw.get(i).toString());
//            data.getUser_rank().add(userRank);
//        }
//
//        return data;
//    }

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

        String string = client.sendRequest(url);

        byte bytes[] = string.getBytes();

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

        realm = Realm.getInstance(AppController.getContext());

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
    }

}
