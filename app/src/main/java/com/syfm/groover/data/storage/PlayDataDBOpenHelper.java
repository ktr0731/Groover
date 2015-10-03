package com.syfm.groover.data.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lycoris on 2015/09/28.
 */
public class PlayDataDBOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "database.db";
    private final static int DB_VERSION = 1;

    public PlayDataDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + PlayDataDBController.TABLE_NAME_PLAYER_DATA + " ("
                        + "_id integer primary key autoincrement not null, "
                        + PlayDataDBController.NAME             + " text not null, "
                        + PlayDataDBController.AVATAR           + " text not null, "
                        + PlayDataDBController.LEVEL            + " text not null, "
                        + PlayDataDBController.TITLE            + " text not null, "
                        + PlayDataDBController.TOTAL_SCORE      + " integer not null, "
                        + PlayDataDBController.TOTAL_PLAY_MUSIC + " text not null, "
                        + PlayDataDBController.TOTAL_MUSIC      + " text not null, "
                        + PlayDataDBController.TOTAL_TROPHY     + " text not null, "
                        + PlayDataDBController.RANK             + " integer not null, "
                        + PlayDataDBController.DATE             + " text not null)"
        );

        db.execSQL(
                "create table " + PlayDataDBController.TABLE_NAME_SHOP_DATA + " ("
                        + "_id integer primary key autoincrement not null, "
                        + PlayDataDBController.COIN + " integer not null)"
        );

        db.execSQL(
                "create table " + PlayDataDBController.TABLE_NAME_AVERAGE_SCORE + " ("
                        + "_id integer primary key autoincrement not null, "
                        + PlayDataDBController.AVERAGE_SCORE + " integer not null)"
        );

        db.execSQL(
                "create table " + PlayDataDBController.TABLE_NAME_STAGE_DATA + " ("
                        + "_id integer primary key autoincrement not null, "
                        + PlayDataDBController.ALL        + " integer not null, "
                        + PlayDataDBController.CLEAR      + " integer not null, "
                        + PlayDataDBController.FULL_CHAIN + " integer not null, "
                        + PlayDataDBController.NO_MISS    + " integer not null, "
                        + PlayDataDBController.S          + " integer not null, "
                        + PlayDataDBController.SS         + " integer not null, "
                        + PlayDataDBController.SSS        + " integer not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
