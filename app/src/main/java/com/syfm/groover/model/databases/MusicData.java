package com.syfm.groover.model.databases;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/09.
 */
@RealmClass
public class MusicData extends RealmObject {

    private String artist;
    private int ex_flag;
    private int music_id;
    private String music_title;
    private String skin_name;
    private String last_play_time;
    private byte[] music_thumbnail;
    private int play_count;

    private RealmList<ResultData> result_data;
    private RealmList<UserRank> user_rank;
    private RealmList<ScoreRankData> score_rank;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getEx_flag() {
        return ex_flag;
    }

    public void setEx_flag(int ex_flag) {
        this.ex_flag = ex_flag;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getMusic_title() {
        return music_title;
    }

    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }

    public String getSkin_name() {
        return skin_name;
    }

    public void setSkin_name(String skin_name) {
        this.skin_name = skin_name;
    }

    public String getLast_play_time() {
        return last_play_time;
    }

    public void setLast_play_time(String last_play_time) {
        this.last_play_time = last_play_time;
    }

    public byte[] getMusic_thumbnail() {
        return music_thumbnail;
    }

    public void setMusic_thumbnail(byte[] music_thumbnail) {
        this.music_thumbnail = music_thumbnail;
    }

    public RealmList<ResultData> getResult_data() {
        return result_data;
    }

    public void setResult_data(RealmList<ResultData> result_data) {
        this.result_data = result_data;
    }

    public RealmList<UserRank> getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(RealmList<UserRank> user_rank) {
        this.user_rank = user_rank;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public RealmList<ScoreRankData> getScore_rank() {
        return score_rank;
    }

    public void setScore_rank(RealmList<ScoreRankData> score_rank) {
        this.score_rank = score_rank;
    }
}
