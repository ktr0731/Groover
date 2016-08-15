package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class UserCurrentEventData extends RealmObject {
    private AwardData award_data;
    private int event_point;
    private int high_score;
    private int rank;
    private int trophy_num;

    public AwardData getAward_data() {
        return award_data;
    }

    public void setAward_data(AwardData award_data) {
        this.award_data = award_data;
    }

    public int getEvent_point() {
        return event_point;
    }

    public void setEvent_point(int event_point) {
        this.event_point = event_point;
    }

    public int getHigh_score() {
        return high_score;
    }

    public void setHigh_score(int high_score) {
        this.high_score = high_score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTrophy_num() {
        return trophy_num;
    }

    public void setTrophy_num(int trophy_num) {
        this.trophy_num = trophy_num;
    }
}
