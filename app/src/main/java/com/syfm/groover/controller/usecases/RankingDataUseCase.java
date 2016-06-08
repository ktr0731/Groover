package com.syfm.groover.controller.usecases;

import android.util.Log;

import com.syfm.groover.controller.entities.event.EventNameEntity;
import com.syfm.groover.model.api.ApiClient;
import com.syfm.groover.model.Constants.Const;
import com.syfm.groover.model.Constants.PlayerDataConst;
import com.syfm.groover.model.databases.SharedPreferenceHelper;
import com.syfm.groover.controller.entities.ranking.RankingDataEntity;

import org.jdeferred.android.AndroidDeferredManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class RankingDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    // ランキングデータが入ったリスト
    public class RankingList {
        public final boolean isSuccess;
        public final ArrayList<RankingDataEntity> list;
        public final String message;

        public RankingList(boolean isSuccess, ArrayList<RankingDataEntity> list, String message) {
            this.isSuccess = isSuccess;
            this.list = list;
            this.message = message;
        }
    }

    // イベント名が入ったリスト
    public class EventNameList {
        public final boolean isSuccess;
        public final ArrayList<EventNameEntity> list;
        public final String message;

        public EventNameList(boolean isSuccess, ArrayList<EventNameEntity> list, String message) {
            this.isSuccess = isSuccess;
            this.list = list;
            this.message = message;
        }
    }

    public void setRankingData(String RANKING_TYPE) {
        ApiClient client = new ApiClient();

        try {
            client.fetchRankingData(RANKING_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRankingData(final String RANKING_TYPE) {
        Log.d("ktr", "getRankingData");
        String value = SharedPreferenceHelper.getRankingData(RANKING_TYPE);
        if (value == "") {

            Log.d("ktr", "getRankingData value is ''");

            // TODO: ここの書き方がキモい
            deferred.when(() -> {
                setRankingData(RANKING_TYPE);
                // 無駄なリクエストを送信するのを防ぐ
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 例外が発生しても問題はないので無視する
                }
            }).done(callback -> {
                // 再帰
                getRankingData(RANKING_TYPE);
            }).fail(callback -> {
                callback.printStackTrace();
                EventBus.getDefault().post(new RankingList(false, null, "ランキングデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
                Log.d("ktr", "getRankingData failed");
            });

        } else {
            Log.d("ktr", "getRankingData value is not empty");

            EventBus.getDefault().post(new RankingList(true, parseRankingData(value, RANKING_TYPE), null));
        }
    }

    public void setEventRankingData(final String SP_NAME, final int NUMBER) {
        ApiClient client = new ApiClient();

        try {
            client.fetchEventRankingData(SP_NAME, NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getEventRankingData(int number) {
        Log.d("ktr", "getEventRankingData");

        String SP_NAME = String.format("event_%03d_ranking", number);

        String value = SharedPreferenceHelper.getRankingData(SP_NAME);
        if (value == "") {

            Log.d("ktr", "getEventRankingData value is ''");

            // TODO: ここの書き方がキモい
            deferred.when(() -> {
                setEventRankingData(SP_NAME, number);
                // 無駄なリクエストを送信するのを防ぐ
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).done(callback -> {
                // 再帰
                getEventRankingData(number);
            }).fail(callback -> {
                callback.printStackTrace();
                EventBus.getDefault().post(new RankingList(false, null, "ランキングデータの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
                Log.d("ktr", "getRankingData failed");
            });

        } else {
            Log.d("ktr", "getRankingData value is not empty");

            EventBus.getDefault().post(new RankingList(true, parseRankingData(value, null), null));
        }
    }

    public ArrayList<RankingDataEntity> parseRankingData(String value, final String RANKING_TYPE) {
        ArrayList<RankingDataEntity> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(value));
            int eventType = xpp.getEventType();

            String tagName = null;
            RankingDataEntity row = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (xpp.getName().equals(Const.RANKING_DATA_ROW_TAG)) {
                        row = new RankingDataEntity();
                    }
                } else if (eventType == XmlPullParser.TEXT) {

                    String e = xpp.getText().trim();

                    if (e.trim().equals('\n') || e.trim().isEmpty()) {
                        eventType = xpp.next();
                        continue;
                    }

                    switch (tagName) {
                        case Const.RANKING_DATA_RANK:
                            row.setRank(Integer.parseInt(e));
                            break;
                        case PlayerDataConst.NAME:
                            row.setPlayer_name(URLDecoder.decode(e, "UTF-8"));
                            break;
                        case Const.RANKING_DATA_SCORE_BI1:
                            row.setScore_bi1(Integer.parseInt(e));
                            break;
                        case Const.RANKING_DATA_TENPO_NAME:
                            row.setTenpo_name(e);
                            break;
                        case Const.RANKING_DATA_PREF:
                            row.setPref(e);
                            break;
                        case Const.RANKING_DATA_AREA:
                            row.setArea(e);
                            break;
                        case Const.RANKING_DATA_TITLE:
                            row.setTitle(e);
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals(Const.RANKING_DATA_ROW_TAG)) {
                        list.add(row);
                    }
                }

                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.d("ktr", "Invalid XML");
            // 取得したXMLが不正なのでリセットする
            SharedPreferenceHelper.setRankingData(RANKING_TYPE, "");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void setEventNameList() {
        ApiClient client = new ApiClient();

        try {
            client.fetchEventNameList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getEventNameList() {
        Log.d("ktr", "getEventNameList");
        String value = SharedPreferenceHelper.getEventNameList();
        if (value == "") {

            Log.d("ktr", "getEventNameList value is ''");

            // TODO: ここの書き方がキモい
            deferred.when(() -> {
                setEventNameList();
                // 無駄なリクエストを送信するのを防ぐ
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).done(callback -> {
                // 再帰
                getEventNameList();
            }).fail(callback -> {
                callback.printStackTrace();
                EventBus.getDefault().post(new EventNameList(false, null, "ランキング名データの取得に失敗しました。通信環境の良い場所で再取得して下さい。"));
                Log.d("ktr", "getEventNameList failed");
            });

        } else {
            Log.d("ktr", "getRankingData value is not empty");

            EventBus.getDefault().post(new EventNameList(true, parseEventNameList(value), null));
        }
    }

    public ArrayList<EventNameEntity> parseEventNameList(String value) {
        ArrayList<EventNameEntity> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(value));
            int eventType = xpp.getEventType();

            String tagName = null;
            EventNameEntity row = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (xpp.getName().equals(Const.EVENT_NAME_ROW_TAG)) {
                        row = new EventNameEntity();
                    }
                } else if (eventType == XmlPullParser.TEXT) {

                    String e = xpp.getText().trim();

                    if (e.trim().equals('\n') || e.trim().isEmpty()) {
                        eventType = xpp.next();
                        continue;
                    }

                    switch (tagName) {
                        case Const.EVENT_NAME_ID:
                            row.setEvent_id(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_TITLE:
                            if (!e.isEmpty()) {
                                row.setTitle(URLDecoder.decode(e, "UTF-8"));
                            }
                            break;
                        case Const.EVENT_NAME_COMMENT:
                            if (!e.isEmpty()) {
                                row.setComment(URLDecoder.decode(e, "UTF-8"));
                            }
                            break;
                        case Const.EVENT_NAME_OPEN_DATE:
                            row.setOpen_date(e);
                            break;
                        case Const.EVENT_NAME_CLOSE_DATE:
                            row.setClose_date(e);
                            break;
                        case Const.EVENT_NAME_OPEN_TIME:
                            row.setOpen_time(e);
                            break;
                        case Const.EVENT_NAME_CLOSE_TIME:
                            row.setClose_time(e);
                            break;
                        case Const.EVENT_NAME_USE_FLAG:
                            row.setUse_flag(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_VERSION:
                            row.setVersion(e);
                            break;
                        case Const.EVENT_NAME_REGION:
                            row.setRegion(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_SCORE_TYPE:
                            row.setScore_type(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_SPECIFIED_NUM:
                            row.setSpecified_num(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_CHALLENGE_NUM:
                            row.setChallenge_num(Integer.parseInt(e));
                            break;
                        case Const.EVENT_NAME_REACHED_SCORE:
                            row.setReached_score(Integer.parseInt(e));
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals(Const.EVENT_NAME_ROW_TAG)) {
                        list.add(row);
                    }
                }

                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.d("ktr", "Invalid XML");
            // 取得したXMLが不正なのでリセットする
            SharedPreferenceHelper.setEventNameList("");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
