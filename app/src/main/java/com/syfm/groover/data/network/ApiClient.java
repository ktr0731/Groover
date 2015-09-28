package com.syfm.groover.data.network;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.syfm.groover.business.entities.PlayData;
import com.syfm.groover.data.storage.PlayDataDBController;
import com.syfm.groover.data.storage.PlayDataDBOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lycoris on 2015/09/27.
 */
public class ApiClient {

    private PlayDataCallback playDataCallback;

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

    public void getPlayData(PlayDataCallback callback) {

        this.playDataCallback = callback;
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                PlayDataDBController db = new PlayDataDBController();
                                if(db.insert(response)) {
                                    playDataCallback.isSuccess(true);
                                } else {
                                    playDataCallback.isSuccess(false);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getPlayerDataError", error.toString());
                                playDataCallback.isSuccess(false);
                            }
                        })
        );
    }

    public void getShopData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";
        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("getShopDataResponse", response.toString());
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

    public void getAverageScore() {
        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("getAverageScoreResponse", response.toString());
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

    public void getStageData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("getStageDataResponse", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getStageDataError", error.toString());
                            }
                        })
        );
    }

    public void getMusicList() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("getMusicListResponse", response.toString());
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

    public void getMusicDetail() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php";
        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("getMusicDetailResponse", response.toString());
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

    public interface LoginListener extends EventListener {
        public void onSuccess();
        public void onFailure();
    }

    public interface PlayDataCallback {
        public void isSuccess(Boolean isSuccess);
    }

}
