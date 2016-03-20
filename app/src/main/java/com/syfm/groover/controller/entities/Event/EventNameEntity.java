package com.syfm.groover.controller.entities.Event;

/**
 * Created by lycoris on 2016/03/20.
 */
public class EventNameEntity {
    private int event_id;
    private String title;
    private String comment;
    private String open_date;
    private String close_date;
    private String open_time;
    private String close_time;
    private int use_flag;
    private String version;
    private int region;
    private int score_type;
    private int specified_num;
    private int challenge_num;
    private int reached_score;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public int getUse_flag() {
        return use_flag;
    }

    public void setUse_flag(int use_flag) {
        this.use_flag = use_flag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }

    public int getSpecified_num() {
        return specified_num;
    }

    public void setSpecified_num(int specified_num) {
        this.specified_num = specified_num;
    }

    public int getChallenge_num() {
        return challenge_num;
    }

    public void setChallenge_num(int challenge_num) {
        this.challenge_num = challenge_num;
    }

    public int getReached_score() {
        return reached_score;
    }

    public void setReached_score(int reached_score) {
        this.reached_score = reached_score;
    }
}
