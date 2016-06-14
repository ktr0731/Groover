package com.syfm.groover.model.databases;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/10.
 */
@RealmClass
public class ResultData extends RealmObject {
    private String rating;
    private int noMiss;
    private int fullChain;
    private int perfect;
    private int playCount;
    private boolean isClear;
    private int score;
    private int maxChain;
    private int adlib;
    private int rank;

    public ResultData() {
        super();
    }

    public ResultData(JSONObject json) throws JSONException {
        super();
        this.rating = json.getString("rating");
        this.noMiss = json.getInt("no_miss");
        this.fullChain = json.getInt("full_chain");
        this.perfect = json.getInt("perfect");
        this.playCount = json.getInt("play_count");
        this.isClear = json.getBoolean("is_clear_mark");
        this.score = json.getInt("score");
        this.maxChain = json.getInt("max_chain");
        this.adlib = json.getInt("adlib");
        this.rank = json.getInt("rank");
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getNoMiss() {
        return noMiss;
    }

    public void setNoMiss(int noMiss) {
        this.noMiss = noMiss;
    }

    public int getFullChain() {
        return fullChain;
    }

    public void setFullChain(int fullChain) {
        this.fullChain = fullChain;
    }

    public int getPerfect() {
        return perfect;
    }

    public void setPerfect(int perfect) {
        this.perfect = perfect;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxChain() {
        return maxChain;
    }

    public void setMaxChain(int maxChain) {
        this.maxChain = maxChain;
    }

    public int getAdlib() {
        return adlib;
    }

    public void setAdlib(int adlib) {
        this.adlib = adlib;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}