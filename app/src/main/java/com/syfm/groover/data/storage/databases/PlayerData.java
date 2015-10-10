package com.syfm.groover.data.storage.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;

/**
 * Created by lycoris on 2015/09/26.
 */
@Table(name = Const.TABLE_NAME_PLAYER_DATA)
public class PlayerData extends Model {

    @Column(name = "_id", index = true)
    public int _id;
    @Column(name = Const.PLAYER_DATA_AVATAR)
    public String avatar;
    @Column(name = Const.PLAYER_DATA_LEVEL)
    public String level;
    @Column(name = Const.PLAYER_DATA_NAME)
    public String player_name;
    @Column(name = Const.PLAYER_DATA_RANK)
    public int    rank;
    @Column(name = Const.PLAYER_DATA_TITLE)
    public String title;
    @Column(name = Const.PLAYER_DATA_TOTAL_MUSIC)
    public int    total_music;
    @Column(name = Const.PLAYER_DATA_TOTAL_PLAY_MUSIC)
    public int    total_play_music;
    @Column(name = Const.PLAYER_DATA_TOTAL_SCORE)
    public int    total_score;
    @Column(name = Const.PLAYER_DATA_TOTAL_TROPHY)
    public int    total_trophy;
    @Column(name = Const.PLAYER_DATA_DATE)
    public String date;

}
