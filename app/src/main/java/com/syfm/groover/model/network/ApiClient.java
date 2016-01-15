package com.syfm.groover.model.network;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syfm.groover.controller.entities.MusicListEntity;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.databases.AverageScore;
import com.syfm.groover.model.storage.databases.MusicData;
import com.syfm.groover.model.storage.databases.ScoreRankData;
import com.syfm.groover.model.storage.databases.PlayerData;
import com.syfm.groover.model.storage.databases.ResultData;
import com.syfm.groover.model.storage.databases.ShopSalesData;
import com.syfm.groover.model.storage.databases.StageData;
import com.syfm.groover.model.storage.databases.UserRank;

import org.jdeferred.android.AndroidDeferredManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


/**
 * Created by lycoris on 2015/09/27.
 */
public class ApiClient {
    private Gson gson = new Gson();
    private Realm realm = Realm.getInstance(AppController.getInstance());
    private Handler handler = new Handler();

    public void tryLogin(final String serial, final String pass, final LoginListener listener) {

        final String url = "https://mypage.groovecoaster.jp/sp/login/auth_con.php";
        final String serialNoKey = "nesicaCardId";
        final String passwordKey = "password";

        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.POST, url,
                response -> listener.onFailure(),
                error -> listener.onSuccess()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(serialNoKey, serial);
                params.put(passwordKey, pass);
                return params;
            }

        });
        // TODO: Volleyを置き換える

    }

    public void fetchPlayerData() {
        Log.d("ktr", "player data");
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject object = response.getJSONObject("player_data");
                        if (object.toString() == null) {
                            return;
                        }
                        realm.beginTransaction();
                        PlayerData playerData = realm.createObjectFromJson(PlayerData.class, object.toString());
                        playerData.setDate(DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString());
                        realm.commitTransaction();

                    } catch (JSONException e) {
                        Log.d("JSONException", e.toString());
                    }
                },
                error -> {
                    Log.d("getPlayerDataError", error.toString());
                }
        ));
    }

    public void fetchShopSalesData() {
        Log.d("ktr", "shop data");
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    Log.d("getShopDataResponse", response.toString());
                    if (response.toString() == null) {
                        return;
                    }
                    realm.beginTransaction();
                    realm.createObjectFromJson(ShopSalesData.class, response.toString());
                    realm.commitTransaction();

                },
                error -> {
                    Log.d("getShopDataError", error.toString());
                }
        ));
    }

    public void fetchAverageScore() {
        Log.d("ktr", "ave data");

        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    Log.d("getAverageScoreResponse", response.toString());
                    try {
                        JSONObject object = response.getJSONObject("average");
                        if (object.toString() == null) {
                            return;
                        }
                        realm.beginTransaction();
                        realm.createObjectFromJson(AverageScore.class, object.toString());
                        realm.commitTransaction();

                    } catch (JSONException e) {
                        Log.d("JSONException", e.toString());
                    }
                },
                error -> {
                    Log.d("getAverageScoreError", error.toString());
                }
        ));
    }

    public void fetchStageData() {
        Log.d("ktr", "stage data");

        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    Log.d("getStageDataResponse", response.toString());
                    try {
                        JSONObject object = response.getJSONObject("stage");
                        if (object.toString() == null) {
                            return;
                        }
                        realm.beginTransaction();
                        realm.createObjectFromJson(StageData.class, object.toString());
                        realm.commitTransaction();
                    } catch (JSONException e) {
                        Log.d("JSONException", e.toString());
                    }
                },
                error -> {
                    Log.d("stageDataError", error.toString());
                }
        ));
    }

    public void fetchMusicData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    Log.d("getMusicListResponse", response.toString());
                    if (response == null) {
                        return;
                    }
                    try {
                        JSONArray array = response.getJSONArray("music_list");
                        Type collectionType = new TypeToken<Collection<MusicListEntity>>() {
                        }.getType();
                        List<MusicListEntity> list = gson.fromJson(array.toString(), collectionType);
                        int i = 0;

                        for (final MusicListEntity row : list) {
                            i++;
                            if (i > 10) break;
                            fetchMusicDetail(row, list.indexOf(row), 10 - 1); //実際はlist.size() -1

                        }

                    } catch (JSONException e) {
                        Log.d("JSONException", e.toString());
                    }
                },
                error -> {
                    Log.d("getMusicListError", error.toString());
                }
        ));
    }

    public void fetchMusicDetail(final MusicListEntity music, final int count, final int size) {
        final String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=";

        // TODO: ハンドラをdeferredにできそう
        handler.postDelayed(() -> {
            AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url + music.getMusic_id(),
                    response -> {

                        try {
                            JSONObject object = response.getJSONObject("music_detail");

                            if (object.length() <= 0) {
                                return;
                            }

                            resultDataJsonReplaceNull(object, "simple_result_data");
                            resultDataJsonReplaceNull(object, "normal_result_data");
                            resultDataJsonReplaceNull(object, "hard_result_data");
                            resultDataJsonReplaceNull(object, "extra_result_data");

                            userRankJsonReplaceNull(object.getJSONArray("user_rank"));

                            realm.beginTransaction();

                            MusicData data = realm.createObjectFromJson(MusicData.class, object.toString());
                            data.setLast_play_time(music.getLast_play_time());
                            data.setPlay_count(music.getPlay_count());

                            // 各難易度をMusicDataの子としてインサート
                            // 要素にNULLがあると挙動がおかしくなるので気をつける

                            ResultData resultSimple = realm.createObjectFromJson(ResultData.class, object.getJSONObject("simple_result_data").toString());
                            data.getResult_data().add(resultSimple);

                            ResultData resultNormal = realm.createObjectFromJson(ResultData.class, object.getJSONObject("normal_result_data").toString());
                            data.getResult_data().add(resultNormal);

                            ResultData resultHard = realm.createObjectFromJson(ResultData.class, object.getJSONObject("hard_result_data").toString());
                            data.getResult_data().add(resultHard);

                            ResultData resultExtra = realm.createObjectFromJson(ResultData.class, object.getJSONObject("extra_result_data").toString());
                            data.getResult_data().add(resultExtra);

                            // UserRankの整形
                            JSONArray userRankRaw = object.getJSONArray("user_rank");

                            for (int i = 0; i < userRankRaw.length(); i++) {
                                UserRank userRank = realm.createObjectFromJson(UserRank.class, userRankRaw.get(i).toString());
                                data.getUser_rank().add(userRank);
                            }

                            fetchMusicThumbnail(music.getMusic_id(), data, count, size);

                        } catch (JSONException e) {
                            realm.cancelTransaction();
                            Log.d("JSONException", e.toString());
                        }
                    },
                    error -> {
                        Log.d("getMusicDetailError", error.toString());
                    }
            ));
        }, 1500 * count);

    }

    public void fetchMusicThumbnail(int id, final MusicData musicData, final int count, final int maxSize) {
        final String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=";
        AppController.getInstance().addToRequestQueue(new ImageRequest(url + id,
                response -> {

                    if (response == null) {
                        realm.cancelTransaction();
                        return;
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    response.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();

                    musicData.setMusic_thumbnail(bytes);
                    realm.commitTransaction();
                }, 0, 0, Bitmap.Config.ARGB_8888,
                error -> {
                    Log.d("getMusicDetailError", error.toString());
                    realm.cancelTransaction();
                }
        ));

    }

    public void fetchScoreRanking(final String id, final int diff) {
        final String url = "https://mypage.groovecoaster.jp/sp/json/score_ranking_bymusic_bydifficulty.php?music_id=" + id + "&difficulty=" + diff;


        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    try {
                        final JSONArray object = response.getJSONArray("score_rank");
                        if (object.length() <= 0) {
                            return;
                        }

                        realm.executeTransaction(realm -> {
                            for (int i = 0; i < 5; i++) {
                                try {
                                    ScoreRankData item = realm.createObjectFromJson(ScoreRankData.class, object.getJSONObject(i));
                                    //item.setId(i + "_" + diff + id);
                                    item.setDiff(String.valueOf(diff));
                                } catch (JSONException e) {
                                    Log.d("JSONException", "at id:" + id + " diff:" + diff + " " + e.toString());
                                }
                            }
                        }, new Realm.Transaction.Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("ktr", e.toString());
                            }
                        });
                    } catch (JSONException e) {
                        Log.d("JSONException", "at id:" + id + " diff:" + diff + " " + e.toString());
                    }
                },
                error -> {
                    Log.d("MusicRankingError", "at id:" + id + " diff:" + diff + " " + error.toString());
                }
        ));
    }

    public interface LoginListener extends EventListener {
        public void onSuccess();

        public void onFailure();
    }

    // TODO: すごく汚いから治したい
    // nullで返ってくるデータをnull以外に整形する
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
                Log.d("JSONException", e.toString());
            }

        }
    }

    // TODO: すごく汚いから治したい
    // nullで返ってくるデータをnull以外に整形する
    private void userRankJsonReplaceNull(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            if (array.isNull(i)) {
                JSONObject rank = new JSONObject();
                try {
                    rank.put(Const.MUSIC_USER_RANK, 0);
                    array.put(i, rank);
                } catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                }

            }
        }
    }

}
