package com.syfm.groover.data.storage.databases;

import io.realm.RealmObject;

/**
 * Created by lycoris on 2016/01/10.
 */
public class ScoreRankData extends RealmObject {
    private int rank;
    private String player_name;
    private int event_point;
    private String title;
    private String last_play_tenpo_name;
    private String pref;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getEvent_point() {
        return event_point;
    }

    public void setEvent_point(int event_point) {
        this.event_point = event_point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLast_play_tenpo_name() {
        return last_play_tenpo_name;
    }

    public void setLast_play_tenpo_name(String last_play_tenpo_name) {
        this.last_play_tenpo_name = last_play_tenpo_name;
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }
}
