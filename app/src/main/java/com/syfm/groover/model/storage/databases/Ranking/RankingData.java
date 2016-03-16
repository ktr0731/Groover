package com.syfm.groover.model.storage.databases.Ranking;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/03/11.
 */
public class RankingData {
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank2() {
        return rank2;
    }

    public void setRank2(int rank2) {
        this.rank2 = rank2;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getScore_bi1() {
        return score_bi1;
    }

    public void setScore_bi1(int score_bi1) {
        this.score_bi1 = score_bi1;
    }

    public int getFcol1() {
        return fcol1;
    }

    public void setFcol1(int fcol1) {
        this.fcol1 = fcol1;
    }

    public int getLast_play_tenpo_id() {
        return last_play_tenpo_id;
    }

    public void setLast_play_tenpo_id(int last_play_tenpo_id) {
        this.last_play_tenpo_id = last_play_tenpo_id;
    }

    public String getTenpo_name() {
        return tenpo_name;
    }

    public void setTenpo_name(String tenpo_name) {
        this.tenpo_name = tenpo_name;
    }

    public int getPref_id() {
        return pref_id;
    }

    public void setPref_id(int pref_id) {
        this.pref_id = pref_id;
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
