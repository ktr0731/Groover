package com.syfm.groover.data.storage.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/07.
 */
@RealmClass
public class AverageScore extends RealmObject{

    private int average_score;

    public int getAverage_score() {
        return average_score;
    }

    public void setAverage_score(int average_score) {
        this.average_score = average_score;
    }
}
