package com.syfm.groover.model.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/09/26.
 */
@RealmClass
public class PlayerData extends RealmObject {
    private String name;
    private String totalScore;
    private int totalPlayMusic;
    private int totalMusic;
    private int rank;
    private int level;
    private String avatar;
    private String title;
    private String totalTrophy;
    private String trophyRank;
    private String averageScore;
    private String version;
    private boolean friendApplication;
    private int all;
    private int clear;
    private int noMiss;
    private int fullChain;
    private int perfect;
    private int s;
    private int ss;
    private int sss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalPlayMusic() {
        return totalPlayMusic;
    }

    public void setTotalPlayMusic(int totalPlayMusic) {
        this.totalPlayMusic = totalPlayMusic;
    }

    public int getTotalMusic() {
        return totalMusic;
    }

    public void setTotalMusic(int totalMusic) {
        this.totalMusic = totalMusic;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalTrophy() {
        return totalTrophy;
    }

    public void setTotalTrophy(String totalTrophy) {
        this.totalTrophy = totalTrophy;
    }

    public String getTrophyRank() {
        return trophyRank;
    }

    public void setTrophyRank(String trophyRank) {
        this.trophyRank = trophyRank;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isFriendApplication() {
        return friendApplication;
    }

    public void setFriendApplication(boolean friendApplication) {
        this.friendApplication = friendApplication;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getClear() {
        return clear;
    }

    public void setClear(int clear) {
        this.clear = clear;
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

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }

    public int getSss() {
        return sss;
    }

    public void setSss(int sss) {
        this.sss = sss;
    }
}
