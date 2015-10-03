package com.syfm.groover.data.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import com.syfm.groover.business.entities.PlayData;
import com.syfm.groover.data.network.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by lycoris on 2015/09/28.
 */
public class PlayDataDBController {

    public final static String TABLE_NAME_PLAYER_DATA = "player_data";
    public final static String TABLE_NAME_SHOP_DATA = "shop_sales_data";
    public final static String TABLE_NAME_AVERAGE_SCORE = "average_score";
    public final static String TABLE_NAME_STAGE_DATA = "stage_data";

    public final static String NAME = "player_name";
    public final static String AVATAR = "avatar";
    public final static String LEVEL = "level";
    public final static String TITLE = "title";
    public final static String TOTAL_SCORE = "total_score";
    public final static String TOTAL_PLAY_MUSIC = "total_play_music";
    public final static String TOTAL_MUSIC = "total_music";
    public final static String TOTAL_TROPHY = "total_trophy";
    public final static String RANK = "rank";
    public final static String DATE = "date";

    public final static String COIN = "current_coin";

    public final static String AVERAGE_SCORE = "average_score";

    public final static String ALL = "all_stage";
    public final static String CLEAR = "clear";
    public final static String FULL_CHAIN = "fullchain";
    public final static String NO_MISS = "nomiss";
    public final static String S = "s";
    public final static String SS = "ss";
    public final static String SSS = "sss";

    private static PlayDataDBOpenHelper openHelper;
    private static SQLiteDatabase db;

    public PlayDataDBController() {
        openHelper = new PlayDataDBOpenHelper(AppController.getInstance().getApplicationContext());
    }

    public boolean insertToPlayerData(JSONObject response) {
        try {
            JSONObject object = response.getJSONObject("player_data");
            db = openHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, object.getString(NAME));
            values.put(AVATAR, object.getString(AVATAR));
            values.put(LEVEL, object.getString(LEVEL));
            values.put(RANK, object.getString(RANK));
            values.put(TITLE, object.getString(TITLE));
            values.put(TOTAL_MUSIC, object.getString(TOTAL_MUSIC));
            values.put(TOTAL_PLAY_MUSIC, object.getString(TOTAL_PLAY_MUSIC));
            values.put(TOTAL_SCORE, object.getString(TOTAL_SCORE));
            values.put(TOTAL_TROPHY, object.getString(TOTAL_TROPHY));
            values.put(DATE, DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString());

            try {
                db.insert(TABLE_NAME_PLAYER_DATA, null, values);
            } finally {
                db.close();
                return true;
            }
        } catch (JSONException e) {
            Log.d("JSONExceptionPlayerData", e.toString());
            return false;
        }
    }

    public boolean insertToShopSalesData(JSONObject response) {
        try {
            db = openHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COIN, response.getString(COIN));

            try {
                db.insert(TABLE_NAME_SHOP_DATA, null, values);
            } finally {
                db.close();
                return true;
            }
        } catch (JSONException e) {
            Log.d("JSONExceptionShopData", e.toString());
            return false;
        }
    }

    public boolean insertToAverageScore(JSONObject response) {
        try {
            JSONObject object = response.getJSONObject("average");
            db = openHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(AVERAGE_SCORE, object.getString(AVERAGE_SCORE));

            try {
                db.insert(TABLE_NAME_AVERAGE_SCORE, null, values);
            } finally {
                db.close();
                return true;
            }
        } catch (JSONException e) {
            Log.d("JSONExceptionAverage", e.toString());
            return false;
        }
    }

    public boolean insertToStageData(JSONObject response) {
        try {
            JSONObject object = response.getJSONObject("stage");
            db = openHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ALL, object.getString("all"));  //定数はSQLでも使っているが、ALLがキーワードに引っかかるためここだけ文字列に変更
            values.put(CLEAR, object.getString(CLEAR));
            values.put(FULL_CHAIN, object.getString(FULL_CHAIN));
            values.put(NO_MISS, object.getString(NO_MISS));
            values.put(S, object.getString(S));
            values.put(SS, object.getString(SS));
            values.put(SSS, object.getString(SSS));

            try {
                db.insert(TABLE_NAME_STAGE_DATA, null, values);
            } finally {
                db.close();
                return true;
            }
        } catch (JSONException e) {
            Log.d("JSONExceptionStageData", e.toString());
            return false;
        }
    }

    public boolean isExistsRecord() {
        db = openHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME_PLAYER_DATA + ";";
        Cursor c = db.rawQuery(query, null);
        if (c == null) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public PlayData getLatest() {

        Log.d("Unko", "getLatest");

        PlayData data = new PlayData();

        db = openHelper.getReadableDatabase();

        String queryPlayerData = "select * from " + TABLE_NAME_PLAYER_DATA + " order by _id desc limit 1;";
        Cursor c = db.rawQuery(queryPlayerData, null);
        boolean isEof = c.moveToFirst();
        if (isEof) {
            data.avatar           = c.getString(c.getColumnIndex(AVATAR));
            data.level            = c.getInt(c.getColumnIndex(LEVEL));
            data.player_name      = c.getString(c.getColumnIndex(NAME));
            data.rank             = c.getInt(c.getColumnIndex(RANK));
            data.title            = c.getString(c.getColumnIndex(TITLE));
            data.total_music      = c.getInt(c.getColumnIndex(TOTAL_MUSIC));
            data.total_play_music = c.getInt(c.getColumnIndex(TOTAL_PLAY_MUSIC));
            data.total_score      = c.getInt(c.getColumnIndex(TOTAL_SCORE));
            data.total_trophy     = c.getInt(c.getColumnIndex(TOTAL_TROPHY));
            data.date             = c.getString(c.getColumnIndex(DATE));
        }

        String queryShopData = "select * from " + TABLE_NAME_SHOP_DATA + " order by _id desc limit 1;";
        c = db.rawQuery(queryShopData, null);
        isEof = c.moveToFirst();
        if (isEof) {
            data.coin = c.getInt(c.getColumnIndex(COIN));
        }

        String queryAverage = "select * from " + TABLE_NAME_AVERAGE_SCORE + " order by _id desc limit 1;";
        c = db.rawQuery(queryAverage, null);
        isEof = c.moveToFirst();
        if (isEof) {
            data.average_score = c.getInt(c.getColumnIndex(AVERAGE_SCORE));
        }

        String queryStageData = "select * from " + TABLE_NAME_STAGE_DATA + " order by _id desc limit 1;";
        c = db.rawQuery(queryStageData, null);
        isEof = c.moveToFirst();
        if (isEof) {
            data.all_stage   = c.getInt(c.getColumnIndex(ALL));
            data.clear_stage = c.getInt(c.getColumnIndex(CLEAR));
            data.fullchain   = c.getInt(c.getColumnIndex(FULL_CHAIN));
            data.nomiss      = c.getInt(c.getColumnIndex(NO_MISS));
            data.s           = c.getInt(c.getColumnIndex(S));
            data.ss          = c.getInt(c.getColumnIndex(SS));
            data.sss         = c.getInt(c.getColumnIndex(SSS));
        }

        return data;
    }
}
