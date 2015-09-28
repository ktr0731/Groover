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

    public final static String TABLE_NAME = "play_data";

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

    private static PlayDataDBOpenHelper openHelper;
    private static SQLiteDatabase db;

    public PlayDataDBController() {
        openHelper = new PlayDataDBOpenHelper(AppController.getInstance().getApplicationContext());
    }

    public boolean insert(JSONObject response) {
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
                db.insert(TABLE_NAME, null, values);
            } finally {
                db.close();
                return true;
            }
        } catch (JSONException e) {
            Log.d("JSONExceptionPlayData", e.toString());
            return false;
        }
    }

    public boolean isExistsRecord() {
        db = openHelper.getReadableDatabase();
        String query = "select * from ?;";
        Cursor c = db.rawQuery(query, new String[]{TABLE_NAME});
        if (c == null) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public PlayData getLatest() {

        PlayData data = new PlayData();

        db = openHelper.getReadableDatabase();
        String query = "select * from ? order by _id desc limit 1;";
        Cursor c = db.rawQuery(query, new String[]{TABLE_NAME});
        boolean isEof = c.moveToFirst();
        if (isEof) {
            data.avatar = c.getString(c.getColumnIndex(AVATAR));
            data.level = c.getString(c.getColumnIndex(LEVEL));
            data.player_name = c.getString(c.getColumnIndex(NAME));
            data.rank = c.getInt(c.getColumnIndex(RANK));
            data.title = c.getString(c.getColumnIndex(TITLE));
            data.total_music = c.getString(c.getColumnIndex(TOTAL_MUSIC));
            data.total_play_music = c.getString(c.getColumnIndex(TOTAL_PLAY_MUSIC));
            data.total_score = c.getString(c.getColumnIndex(TOTAL_SCORE));
            data.total_trophy = c.getString(c.getColumnIndex(TOTAL_TROPHY));
        }

        return data;
    }
}
