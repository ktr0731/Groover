package com.syfm.groover.data.storage.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;

/**
 * Created by lycoris on 2015/10/07.
 */
@Table(name = Const.TABLE_NAME_STAGE_DATA)
public class StageData extends Model {

    @Column(name = "_id", index = true)
    public int _id;
    @Column(name = Const.STAGE_DATA_ALL)
    public int all;
    @Column(name = Const.STAGE_DATA_CLEAR)
    public int clear;
    @Column(name = Const.STAGE_DATA_FULL_CHAIN)
    public int fullchain;
    @Column(name = Const.STAGE_DATA_NO_MISS)
    public int nomiss;
    @Column(name = Const.STAGE_DATA_S)
    public int s;
    @Column(name = Const.STAGE_DATA_SS)
    public int ss;
    @Column(name = Const.STAGE_DATA_SSS)
    public int sss;

}
