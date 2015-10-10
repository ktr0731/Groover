package com.syfm.groover.data.storage.databases;

import android.content.Context;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.MusicData;

import java.util.List;

/**
 * Created by lycoris on 2015/10/10.
 */
@Table(name = Const.TABLE_NAME_MUSIC_RESULT)
public class ResultData extends Model {
    @Column(name = Const.MUSIC_RESULT_ADLIB)
    public int    adlib;
    @Column(name = Const.MUSIC_RESULT_FULL_CHAIN)
    public int    full_chain;
    @Column(name = Const.MUSIC_RESULT_IS_CLEAR_MARK)
    public String is_clear_mark;
    @Column(name = Const.MUSIC_RESULT_IS_FAILED_MARK)
    public String is_failed_mark;
    @Column(name = Const.MUSIC_RESULT_MAX_CHAIN)
    public int    max_chain;
    @Column(name = Const.MUSIC_RESULT_NO_MISS)
    public int    no_miss;
    @Column(name = Const.MUSIC_LIST_PLAY_COUNT)
    public int    play_count;
    @Column(name = Const.MUSIC_RESULT_RATING)
    public String rating;
    @Column(name = Const.MUSIC_RESULT_SCORE)
    public int    score;

    public List<MusicData> resultData() {
        Log.d("Unko", "getMany");
        return getMany(MusicData.class, Const.MUSIC_RELATION_RESULT_NORMAL);
    }

    public ResultData() {
        super();
    }
}
