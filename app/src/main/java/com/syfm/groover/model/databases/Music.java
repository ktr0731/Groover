package com.syfm.groover.model.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/06/05.
 */

public class Music extends RealmObject {
    private int id;
    private String title;
    private String artist;
    private String skin;
    private byte[] thumbnail;
    private boolean favorite;
    private boolean exFlag;
    private ResultData simpleResult;
    private ResultData normalResult;
    private ResultData hardResult;
    private ResultData extraResult;
    private String lastPlayDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isExFlag() {
        return exFlag;
    }

    public void setExFlag(boolean exFlag) {
        this.exFlag = exFlag;
    }

    public ResultData getSimpleResult() {
        return simpleResult;
    }

    public void setSimpleResult(ResultData simpleResult) {
        this.simpleResult = simpleResult;
    }

    public ResultData getNormalResult() {
        return normalResult;
    }

    public void setNormalResult(ResultData normalResult) {
        this.normalResult = normalResult;
    }

    public ResultData getHardResult() {
        return hardResult;
    }

    public void setHardResult(ResultData hardResult) {
        this.hardResult = hardResult;
    }

    public ResultData getExtraResult() {
        return extraResult;
    }

    public void setExtraResult(ResultData extraResult) {
        this.extraResult = extraResult;
    }

    public String getLastPlayDate() {
        return lastPlayDate;
    }

    public void setLastPlayDate(String lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }
}
