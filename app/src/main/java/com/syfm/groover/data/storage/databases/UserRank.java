package com.syfm.groover.data.storage.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;

import java.util.List;

/**
 * Created by lycoris on 2015/10/10.
 */
@Table(name = Const.TABLE_NAME_MUSIC_RANK)
public class UserRank extends Model {
    @Column(name = Const.MUSIC_USER_RANK)
    public int rank;

    @Column(name = Const.TABLE_NAME_MUSIC_DATA)
    public MusicData musicData;
}
