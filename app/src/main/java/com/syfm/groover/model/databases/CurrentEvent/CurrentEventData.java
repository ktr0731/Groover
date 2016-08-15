package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class CurrentEventData extends RealmObject {
    private String title_name;
    private String open_date;
    private String close_date;
    private int score_type;
    private UserCurrentEventData user_event_data;

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }

    public UserCurrentEventData getUser_event_data() {
        return user_event_data;
    }

    public void setUser_event_data(UserCurrentEventData user_event_data) {
        this.user_event_data = user_event_data;
    }
}
