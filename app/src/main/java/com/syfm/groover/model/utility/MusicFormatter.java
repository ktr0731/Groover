package com.syfm.groover.model.utility;

import com.syfm.groover.model.databases.Music;
import com.syfm.groover.model.databases.ResultData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lycoris on 2016/06/07.
 */

public class MusicFormatter {

    public Music getFormattedMusicRecord(int musicId, String lastPlayTime, JSONObject detail, byte[] thumbnail) throws JSONException {
        Music music = new Music();

        JSONObject simpleResultObject;
        JSONObject normalResultObject;
        JSONObject hardResultObject;
        JSONObject extraResultObject;
        JSONArray userRank;

        ResultData simpleResult;
        ResultData normalResult;
        ResultData hardResult;
        ResultData extraResult;

        music.setId(musicId);
        music.setTitle(detail.getString("music_title"));
        music.setArtist(detail.getString("artist"));
        music.setSkin(detail.getString("skin_name"));
        music.setFavorite(detail.getInt("fav_flg") == 1);
        music.setExFlag(detail.getInt("ex_flag") == 1);
        music.setThumbnail(thumbnail);

        userRank = detail.getJSONArray("user_rank");

        // TODO: リファクタリングできないか?
        // １つのJSONObjectにまとめる
        if (!detail.isNull("simple_result_data")) {
            simpleResultObject = detail.getJSONObject("simple_result_data")
                    .accumulate("lastDate", lastPlayTime)
                    .accumulate("rank", userRank.getJSONObject(0).getInt("rank"));
            simpleResult = new ResultData(simpleResultObject);
            music.setSimpleResult(simpleResult);
        }

        if (!detail.isNull("normal_result_data")) {
            normalResultObject = detail.getJSONObject("normal_result_data")
                    .accumulate("lastDate", lastPlayTime)
                    .accumulate("rank", userRank.getJSONObject(1).getInt("rank"));
            normalResult = new ResultData(normalResultObject);
            music.setNormalResult(normalResult);
        }

        if (!detail.isNull("hard_result_data")) {
            hardResultObject = detail.getJSONObject("hard_result_data")
                    .accumulate("lastDate", lastPlayTime)
                    .accumulate("rank", userRank.getJSONObject(2).getInt("rank"));
            hardResult = new ResultData(hardResultObject);
            music.setHardResult(hardResult);
        }

        if (!detail.isNull("extra_result_data")) {
            extraResultObject = detail.getJSONObject("extra_result_data")
                    .accumulate("lastDate", lastPlayTime)
                    .accumulate("rank", userRank.getJSONObject(3).getInt("rank"));
            extraResult = new ResultData(extraResultObject);
            music.setExtraResult(extraResult);
        }

        return music;
    }
}
