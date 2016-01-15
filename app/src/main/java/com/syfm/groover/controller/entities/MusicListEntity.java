package com.syfm.groover.controller.entities;

/**
 * Created by lycoris on 2015/09/26.
 */

//一時的にmusic_idを持ちたいのでクラスを作成。
public class MusicListEntity {
    private int music_id;
    private String last_play_time;
    private int play_count;

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getLast_play_time() {
        return last_play_time;
    }

    public void setLast_play_time(String last_play_time) {
        this.last_play_time = last_play_time;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }
}
