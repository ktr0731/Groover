package com.syfm.groover.data.storage.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/09/26.
 */
@RealmClass
public class PlayerData extends RealmObject {

    private String avatar;
    private String level;
    private String player_name;
    private int    rank;
    private String title;
    private int    total_music;
    private int    total_play_music;
    private int    total_score;
    private int    total_trophy;
    private String date;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal_music() {
        return total_music;
    }

    public void setTotal_music(int total_music) {
        this.total_music = total_music;
    }

    public int getTotal_play_music() {
        return total_play_music;
    }

    public void setTotal_play_music(int total_play_music) {
        this.total_play_music = total_play_music;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getTotal_trophy() {
        return total_trophy;
    }

    public void setTotal_trophy(int total_trophy) {
        this.total_trophy = total_trophy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
