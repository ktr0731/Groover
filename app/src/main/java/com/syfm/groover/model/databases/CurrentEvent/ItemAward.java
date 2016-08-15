package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class ItemAward extends RealmObject {
    private String item_name;
    private int item_num;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }
}
