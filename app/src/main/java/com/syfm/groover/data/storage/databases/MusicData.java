package com.syfm.groover.data.storage.databases;

import android.content.ClipData;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.syfm.groover.data.storage.Const;

import java.util.List;

/**
 * Created by lycoris on 2015/10/09.
 */
@Table(name = Const.TABLE_NAME_MUSIC_DATA)
public class MusicData extends Model {

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

    public List<ResultData> result() {
        return getMany(ResultData.class, Const.TABLE_NAME_MUSIC_DATA);
    }

    public static List<ResultData> getAll(MusicData musicData) {
        return new Select()
                .from(ResultData.class)
                .where(Const.TABLE_NAME_MUSIC_DATA + " = ?", musicData.getId())
                .orderBy("Id asc")
                .execute();
    }


    public MusicData() {
        super();
    }

}
