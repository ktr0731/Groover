package com.syfm.groover.data.network;

import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;

import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syfm.groover.business.entities.MusicListEntity;
import com.syfm.groover.data.storage.databases.AverageScore;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.PlayerData;
import com.syfm.groover.data.storage.databases.ShopSalesData;
import com.syfm.groover.data.storage.databases.StageData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lycoris on 2015/09/27.
 */
public class ApiClient {

    private PlayDataCallback playDataCallback;
    private MusicDataCallback musicDataCallback;
    private Gson gson = new Gson();
    Handler handler = new Handler();

    public void tryLogin(final String serial, final String pass, final LoginListener listener) {

        final String url = "https://mypage.groovecoaster.jp/sp/login/auth_con.php";
        final String serialNoKey = "nesicaCardId";
        final String passwordKey = "password";


        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Unko", response.toString());
                        listener.onFailure();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Unko", error.networkResponse.toString());
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
            Log.d("UnkoException", e.toString());
        } finally {
        }
    }

    public void fetchPlayerData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("Unko", response.getJSONObject("player_data").toString());
                                    JSONObject object = response.getJSONObject("player_data");
                                    PlayerData playerData = gson.fromJson(object.toString(), PlayerData.class);
                                    if (playerData != null) {
                                        playerData.date = DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString();
                                        // Active AndroidでSQLiteに保存
                                        playerData.save();
                                    }
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
                                ShopSalesData shopData = gson.fromJson(response.toString(), ShopSalesData.class);
                                if (shopData != null) {
                                    // Active AndroidでSQLiteに保存
                                    shopData.save();
                                }
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
                                    AverageScore averageScore = gson.fromJson(object.toString(), AverageScore.class);
                                    if (averageScore != null) {
                                        // Active AndroidでSQLiteに保存
                                        averageScore.save();
                                    }
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
                                    StageData stageData = gson.fromJson(object.toString(), StageData.class);
                                    if (stageData != null) {
                                        // Active AndroidでSQLiteに保存
                                        stageData.save();
                                        playDataCallback.isSuccess(true);
                                    }
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

    public void fetchAllMusicData() {
        fetchMusicData();
    }

    public void fetchMusicData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getMusicListResponse", response.toString());
                                if(response == null) {
                                    return;
                                }
                                try {
                                    JSONArray array = response.getJSONArray("music_list");
                                    Type collectionType = new TypeToken<Collection<MusicListEntity>>(){}.getType();
                                    List<MusicListEntity> list = gson.fromJson(array.toString(), collectionType);
                                    /*for (MusicListEntity row : list) {
                                        fetchMusicDetail(row, list.indexOf(row));
                                    }*/
                                    fetchMusicDetail(list.get(0), 0);
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

    public void fetchMusicDetail(final MusicListEntity music, int count) {
        final String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=";
        Log.d("Unko", count + "番目 delay:" + 1500 * count + ", URL:" + url + music.music_id);
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
                                            MusicData data = gson.fromJson(object.toString(), MusicData.class);
                                            data.save();
                                            MusicData data1 = new Select().from(MusicData.class).orderBy("_id desc").executeSingle();
                                            if(data1 != null) {
                                                Log.d("Unko", data1.music_title);
                                                Log.d("Unko", "rank: " + data1.normal_result_data.score);
                                            }
                                        } catch (JSONException e) {
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
        }, 2000 * count);

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

}
