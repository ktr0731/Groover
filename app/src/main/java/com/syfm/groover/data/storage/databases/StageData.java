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
    @Column(name = Const.ALL)
    public int all;
    @Column(name = Const.CLEAR)
    public int clear;
    @Column(name = Const.FULL_CHAIN)
    public int fullchain;
    @Column(name = Const.NO_MISS)
    public int nomiss;
    @Column(name = Const.S)
    public int s;
    @Column(name = Const.SS)
    public int ss;
    @Column(name = Const.SSS)
    public int sss;

}
