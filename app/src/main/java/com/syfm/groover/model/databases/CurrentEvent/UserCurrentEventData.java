package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class UserCurrentEventData extends RealmObject {
    private AwardData awardData;
    private int bp;
    private int highScoreBp;
    private int rank;
    private int trophyNum;

    public AwardData getAwardData() {
        return awardData;
    }

    public void setAwardData(AwardData awardData) {
        this.awardData = awardData;
    }

    public int getBp() {
        return bp;
    }

    public void setBp(int bp) {
        this.bp = bp;
    }

    public int getHighScoreBp() {
        return highScoreBp;
    }

    public void setHighScoreBp(int highScoreBp) {
        this.highScoreBp = highScoreBp;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTrophyNum() {
        return trophyNum;
    }

    public void setTrophyNum(int trophyNum) {
        this.trophyNum = trophyNum;
    }
}
