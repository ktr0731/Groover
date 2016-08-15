package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class CurrentEventData extends RealmObject {
    private String title;
    private String openDate;
    private String closeDate;
    private int scoreType;
    private UserCurrentEventData userCurrentEventData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public UserCurrentEventData getUserCurrentEventData() {
        return userCurrentEventData;
    }

    public void setUserCurrentEventData(UserCurrentEventData userCurrentEventData) {
        this.userCurrentEventData = userCurrentEventData;
    }
}
