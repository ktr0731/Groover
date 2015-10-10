package com.syfm.groover.data.storage.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;

import java.util.List;

/**
 * Created by lycoris on 2015/10/09.
 */
@Table(name = Const.TABLE_NAME_MUSIC_DATA)
public class MusicData extends Model {

    @Column(name = "_id", index = true)
    public int _id;
    @Column(name = Const.MUSIC_DETAIL_ARTIST)
    public String artist;
    @Column(name = Const.MUSIC_DETAIL_EX_FLAG)
    public int ex_flag;
    @Column(name = Const.MUSIC_LIST_MUSIC_ID)
    public int music_id;
    @Column(name = Const.MUSIC_LIST_MUSIC_TITLE)
    public String music_title;
    @Column(name = Const.MUSIC_DETAIL_SKIN_NAME)
    public String skin_name;
    @Column(name = Const.MUSIC_LIST_LAST_PLAY_TIME)
    public String last_play_time;
    @Column(name = Const.MUSIC_RELATION_RESULT_SIMPLE)
    public ResultData simple_result_data;
    @Column(name = Const.MUSIC_RELATION_RESULT_NORMAL)
    public ResultData normal_result_data;
    @Column(name = Const.MUSIC_RELATION_RESULT_HARD)
    public ResultData hard_result_data;
    @Column(name = Const.MUSIC_RELATION_RESULT_EXTRA)
    public ResultData extra_result_data;
    @Column(name = Const.MUSIC_USER_RANK)
    public List<UserRank> user_rank;

    public MusicData() {
        super();
    }

}
