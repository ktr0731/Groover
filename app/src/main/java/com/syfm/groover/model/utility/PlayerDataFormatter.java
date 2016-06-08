package com.syfm.groover.model.utility;

import com.syfm.groover.model.databases.PlayerData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lycoris on 2016/06/08.
 */

public class PlayerDataFormatter {
    public PlayerData getFormattedPlayerDataObject(JSONObject json) throws JSONException {
        PlayerData data = new PlayerData();

        JSONObject pd = json.getJSONObject("player_data");
        JSONObject sd = json.getJSONObject("stage");

        data.setName(pd.getString("player_name"));
        data.setTotalScore(pd.getString("total_music"));
        data.setTotalPlayMusic(pd.getInt("total_play_music"));
        data.setTotalMusic(pd.getInt("total_music"));
        data.setRank(pd.getInt("rank"));
        data.setLevel(pd.getInt("level"));
        data.setAvatar(pd.getString("avatar"));
        data.setTitle(pd.getString("title"));
        data.setTotalTrophy(pd.getString("total_trophy"));
        data.setTrophyRank(pd.getString("trophy_rank"));
        data.setAverageScore(pd.getString("average_score"));
        data.setVersion(pd.getString("version"));
        data.setFriendApplication(pd.getBoolean("friendApplication"));
        data.setAll(sd.getInt("all"));
        data.setClear(sd.getInt("clear"));
        data.setNoMiss(sd.getInt("nomiss"));
        data.setFullChain(sd.getInt("fullchain"));
        data.setPerfect(sd.getInt("perfect"));
        data.setS(sd.getInt("s"));
        data.setSs(sd.getInt("ss"));
        data.setSss(sd.getInt("sss"));

        return data;
    }
}
