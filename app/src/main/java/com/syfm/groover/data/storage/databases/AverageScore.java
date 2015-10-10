package com.syfm.groover.data.storage.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.syfm.groover.data.storage.Const;

/**
 * Created by lycoris on 2015/10/07.
 */
@Table(name = Const.TABLE_NAME_AVERAGE_SCORE)
public class AverageScore extends Model{

    @Column(name = "_id", index = true)
    public int _id;
    @Column(name = Const.AVERAGE_SCORE_AVERAGE_SCORE)
    public int average_score;
}
