package com.syfm.groover.controller.usecases;

import android.util.Log;
import android.widget.Toast;

import com.syfm.groover.model.network.ApiClient;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.SharedPreferenceHelper;
import com.syfm.groover.model.storage.databases.Ranking.RankingData;

import org.jdeferred.android.AndroidDeferredManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/26.
 */
public class RankingDataUseCase {

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    public class RankingList {
        public final boolean isSuccess;
        public final ArrayList<RankingData> list;

        public RankingList(boolean isSuccess, ArrayList<RankingData> list) {
            this.isSuccess = isSuccess;
            this.list = list;
        }
    }

    public void setRankingData(String RANKING_TYPE) {
        ApiClient client = new ApiClient();

        client.fetchRankingData(RANKING_TYPE);
    }

    public void getRankingData(final String RANKING_TYPE) {
        Log.d("ktr", "getRankingData");
        String value = SharedPreferenceHelper.getLevelRanking(RANKING_TYPE);
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
                }
            }).done(callback -> {
                // 再帰
                getRankingData(RANKING_TYPE);
            }).fail(callback -> {
                callback.printStackTrace();
                EventBus.getDefault().post(new RankingList(false, null));
                Log.d("ktr", "getRankingData failed");
            });

        } else {
            Log.d("ktr", "getRankingData value is not empty");

            EventBus.getDefault().post(new RankingList(true, parseRankingData(value, RANKING_TYPE)));
        }
    }

    public ArrayList<RankingData> parseRankingData(String value, final String RANKING_TYPE) {
        ArrayList<RankingData> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(value));
            int eventType = xpp.getEventType();

            String tagName = null;
            RankingData row = null;

            // For debug
            int i=0;

            while (eventType != XmlPullParser.END_DOCUMENT && i<10) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (xpp.getName().equals(Const.RANKING_DATA_ROW_TAG)) {
                        row = new RankingData();
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
                        case Const.PLAYER_DATA_NAME:
                            row.setPlayer_name(e);
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
                        i++;
                        list.add(row);
                    }
                }

                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.d("ktr", "Invalid XML");
            // 取得したXMLが不正なのでリセットする
            SharedPreferenceHelper.setLevelRanking(RANKING_TYPE, "");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
