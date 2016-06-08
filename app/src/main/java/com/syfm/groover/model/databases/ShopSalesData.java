package com.syfm.groover.model.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/07.
 */
@RealmClass
public class ShopSalesData extends RealmObject {

    private int current_coin;

    public int getCurrent_coin() {
        return current_coin;
    }

    public void setCurrent_coin(int current_coin) {
        this.current_coin = current_coin;
    }
}
