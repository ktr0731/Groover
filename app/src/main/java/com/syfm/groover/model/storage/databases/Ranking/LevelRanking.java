package com.syfm.groover.model.storage.databases.Ranking;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/03/11.
 */
public class LevelRanking {
    private int rank;
    private int rank2;  // 何に使っているのかわからない
    private String player_name;
    private int score_bi1;
    private int fcol1;  // 何に使っているのかわからない
    private int last_play_tenpo_id;
    private String tenpo_name;
    private int pref_id;
    private String pref;
    private int area_id;
    private String area;
    private String title;
}
