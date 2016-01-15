package com.syfm.groover.model.storage.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/10.
 */
@RealmClass
public class ResultData extends RealmObject {

    private int    adlib;
    private String music_level;
    private int    full_chain;
    private String is_clear_mark;
    private String is_failed_mark;
    private int    max_chain;
    private int    no_miss;
    private int    play_count;
    private String rating;
    private int    score;

    public int getAdlib() {
        return adlib;
    }

    public void setAdlib(int adlib) {
        this.adlib = adlib;
    }

    public String getMusic_level() {
        return music_level;
    }

    public void setMusic_level(String music_level) {
        this.music_level = music_level;
    }

    public int getFull_chain() {
        return full_chain;
    }

    public void setFull_chain(int full_chain) {
        this.full_chain = full_chain;
    }

    public String getIs_clear_mark() {
        return is_clear_mark;
    }

    public void setIs_clear_mark(String is_clear_mark) {
        this.is_clear_mark = is_clear_mark;
    }

    public String getIs_failed_mark() {
        return is_failed_mark;
    }

    public void setIs_failed_mark(String is_failed_mark) {
        this.is_failed_mark = is_failed_mark;
    }

    public int getMax_chain() {
        return max_chain;
    }

    public void setMax_chain(int max_chain) {
        this.max_chain = max_chain;
    }

    public int getNo_miss() {
        return no_miss;
    }

    public void setNo_miss(int no_miss) {
        this.no_miss = no_miss;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
