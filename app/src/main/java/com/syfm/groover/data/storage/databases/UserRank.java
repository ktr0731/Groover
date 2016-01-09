package com.syfm.groover.data.storage.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/10.
 */
@RealmClass
public class UserRank extends RealmObject {
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
