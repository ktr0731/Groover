package com.syfm.groover.data.network;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syfm.groover.business.entities.MusicListEntity;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.AverageScore;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.PlayerData;
import com.syfm.groover.data.storage.databases.ResultData;
import com.syfm.groover.data.storage.databases.ShopSalesData;
import com.syfm.groover.data.storage.databases.StageData;
import com.syfm.groover.data.storage.databases.UserRank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

    private PlayDataCallback playDataCallback;
    private MusicDataCallback musicDataCallback;
    private Gson gson = new Gson();
    private Realm realm = Realm.getInstance(AppController.getInstance());
    Handler handler = new Handler();

    public void tryLogin(final String serial, final String pass, final LoginListener listener) {

        final String url = "https://mypage.groovecoaster.jp/sp/login/auth_con.php";
        final String serialNoKey = "nesicaCardId";
        final String passwordKey = "password";


        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onFailure();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onSuccess();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(serialNoKey, serial);
                params.put(passwordKey, pass);
                return params;
            }

        });
    }


    public void fetchAllPlayData(PlayDataCallback callback) {
        this.playDataCallback = callback;

        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchPlayerData();
                }
            }, 0);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchShopSalesData();
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchAverageScore();
                }
            }, 2000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchStageData();
                }
            }, 3000);

        } catch (Exception e) {
            //エラー処理
            playDataCallback.isSuccess(false);
        }
    }

    public void fetchPlayerData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject object = response.getJSONObject("player_data");
                                    //PlayerData playerData = gson.fromJson(object.toString(), PlayerData.class);
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
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getPlayerDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchShopSalesData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getShopDataResponse", response.toString());
                                if (response.toString() == null) {
                                    return;
                                }
                                realm.beginTransaction();
                                realm.createObjectFromJson(ShopSalesData.class, response.toString());
                                realm.commitTransaction();
                                //ShopSalesData shopData = gson.fromJson(response.toString(), ShopSalesData.class);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getShopDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchAverageScore() {
        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getAverageScoreResponse", response.toString());
                                try {
                                    JSONObject object = response.getJSONObject("average");
                                    //AverageScore averageScore = gson.fromJson(object.toString(), AverageScore.class);
                                    if (object.toString() == null) {
                                        return;
                                    }
                                    realm.beginTransaction();
                                    realm.createObjectFromJson(AverageScore.class, object.toString());
                                    realm.commitTransaction();

                                } catch (JSONException e) {
                                    Log.d("JSONException", e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getAverageScoreError", error.toString());
                            }
                        })
        );
    }

    public void fetchStageData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getStageDataResponse", response.toString());
                                try {
                                    JSONObject object = response.getJSONObject("stage");
                                    //StageData stageData = gson.fromJson(object.toString(), StageData.class);
                                    if (object.toString() == null) {
                                        return;
                                    }
                                    realm.beginTransaction();
                                    realm.createObjectFromJson(StageData.class, object.toString());
                                    realm.commitTransaction();
                                    playDataCallback.isSuccess(true);

                                } catch (JSONException e) {
                                    Log.d("JSONException", e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("stageDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchAllMusicData(MusicDataCallback callback) {
        this.musicDataCallback = callback;
        fetchMusicData();

    }

    public void fetchMusicData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
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

                                    for (MusicListEntity row : list) {
                                        i++;
                                        if (i > 10) break;
                                        fetchMusicDetail(row, list.indexOf(row), 10 - 1); //実際はlist.size() -1
                                    }

                                } catch (JSONException e) {
                                    Log.d("JSONException", e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getMusicListError", error.toString());
                            }
                        })
        );
    }

    public void fetchMusicDetail(final MusicListEntity music, final int count, final int size) {
        final String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=";
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url + music.music_id,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("getMusicDetailResponse", response.toString());

                                        try {
                                            JSONObject object = response.getJSONObject("music_detail");

                                            //MusicData data = gson.fromJson(object.toString(), MusicData.class);

                                            if(object.toString()==null) {
                                                return;
                                            }

                                            resultDataJsonReplaceNull(object, "simple_result_data");
                                            resultDataJsonReplaceNull(object, "normal_result_data");
                                            resultDataJsonReplaceNull(object, "hard_result_data");
                                            resultDataJsonReplaceNull(object, "extra_result_data");

                                            userRankJsonReplaceNull(object.getJSONArray("user_rank"));

                                            realm.beginTransaction();
                                            MusicData data = realm.createObjectFromJson(MusicData.class, object.toString());
                                            data.setLast_play_time(music.last_play_time);
                                            data.setPlay_count(music.play_count);

                                            // 各難易度をMusicDataの子としてインサート
                                            // 要素にNULLがあると挙動がおかしくなるので気をつける

                                            //ResultData resultSimple = gson.fromJson(object.getJSONObject("simple_result_data").toString(), ResultData.class);

                                            ResultData resultSimple = realm.createObjectFromJson(ResultData.class, object.getJSONObject("simple_result_data").toString());
                                            data.getResult_data().add(resultSimple);

                                            //ResultData resultNormal = gson.fromJson(object.getJSONObject("normal_result_data").toString(), ResultData.class);
                                            ResultData resultNormal = realm.createObjectFromJson(ResultData.class, object.getJSONObject("normal_result_data").toString());
                                            data.getResult_data().add(resultNormal);

                                            //ResultData resultHard = gson.fromJson(object.getJSONObject("hard_result_data").toString(), ResultData.class);
                                            ResultData resultHard = realm.createObjectFromJson(ResultData.class, object.getJSONObject("hard_result_data").toString());
                                            data.getResult_data().add(resultHard);

                                            //ResultData resultExtra = gson.fromJson(object.getJSONObject("extra_result_data").toString(), ResultData.class);
                                            ResultData resultExtra = realm.createObjectFromJson(ResultData.class, object.getJSONObject("extra_result_data").toString());
                                            data.getResult_data().add(resultExtra);

                                            // UserRankの整形
                                            JSONArray userRankRaw = object.getJSONArray("user_rank");

                                            for (int i = 0; i < userRankRaw.length(); i++) {
                                                UserRank userRank = realm.createObjectFromJson(UserRank.class, userRankRaw.get(i).toString());
                                                data.getUser_rank().add(userRank);
                                            }


                                            fetchMusicThumbnail(music.music_id, data, count, size);


                                        } catch (JSONException e) {
                                            realm.cancelTransaction();
                                            Log.d("JSONException", e.toString());
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("getMusicDetailError", error.toString());
                                    }
                                })
                );
            }
        }, 1500 * count);

    }

    public void fetchMusicThumbnail(String id, final MusicData musicData, final int count, final int maxSize) {
        final String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=";
        AppController.getInstance().addToRequestQueue(new ImageRequest(url + id,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {

                                if(response == null) {
                                    realm.cancelTransaction();
                                    return;
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                response.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] bytes = baos.toByteArray();

                                musicData.setMusic_thumbnail(bytes);
                                realm.commitTransaction();

                                if (maxSize == count) {
                                    musicDataCallback.isSuccess(true);
                                }

                            }
                        }, 0, 0, Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getMusicDetailError", error.toString());
                            }
                        })
        );

    }

    public interface LoginListener extends EventListener {
        public void onSuccess();

        public void onFailure();
    }

    public interface PlayDataCallback {
        public void isSuccess(Boolean isSuccess);
    }

    public interface MusicDataCallback {
        public void isSuccess(Boolean isSuccess);
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
