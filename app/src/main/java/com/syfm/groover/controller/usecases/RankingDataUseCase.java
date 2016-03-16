package com.syfm.groover.controller.usecases;

import android.util.Log;

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

    public void setRankingData(String LEVEL_TYPE) {
        ApiClient client = new ApiClient();

        deferred.when(() -> {
            client.fetchLevelRankingData(LEVEL_TYPE);
        }).done(callback -> {
            return;
        }).fail(callback -> {
            Log.d("ktr", callback.getStackTrace().toString());
        });
    }

    public void getRankingData(final String LEVEL_TYPE) {
        String value = SharedPreferenceHelper.getLevelRanking(LEVEL_TYPE);
        if (value == "") {
            setRankingData(LEVEL_TYPE);
            value = SharedPreferenceHelper.getLevelRanking(LEVEL_TYPE);
        }

        parseRankingData(value);
        EventBus.getDefault().post(value);
    }

    public RankingData parseRankingData(String value) {
        ArrayList<RankingData> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(value));
            int eventType = xpp.getEventType();

            String tagName = null;
            RankingData row = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (xpp.getName().equals(Const.RANKING_DATA_ROW_TAG)) {
                        row = new RankingData();
                    }
                } else if (eventType == XmlPullParser.TEXT) {

                    String e = xpp.getText().trim();
                    Log.d("ktr", "tag:" + tagName + " element:" + e);

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
                        list.add(row);
                    }
                }

                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
