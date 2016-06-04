package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.PlayDataUseCase;
import com.syfm.groover.model.storage.databases.AverageScore;
import com.syfm.groover.model.storage.databases.PlayerData;
import com.syfm.groover.model.storage.databases.ShopSalesData;
import com.syfm.groover.model.storage.databases.StageData;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/22.
 */
public class PlayDataFragment extends Fragment {

    @Bind(R.id.tv_player_data_name)
    TextView player_name;
    @Bind(R.id.tv_player_data_level)
    TextView player_level;
    @Bind(R.id.tv_player_data_avatar_title)
    TextView player_avatar_title;
    @Bind(R.id.tv_player_data_total_score)
    TextView player_total_score;
    @Bind(R.id.tv_player_data_rank)
    TextView player_rank;
    @Bind(R.id.tv_player_data_coin)
    TextView player_coin;
    @Bind(R.id.tv_player_data_trophy)
    TextView player_trophy;

    @Bind(R.id.tv_music_result_play_music)
    TextView music_play_music;
    @Bind(R.id.tv_music_result_play_music_per)
    TextView music_play_music_per;
    @Bind(R.id.tv_music_result_clear_stage)
    TextView music_clear_stage;
    @Bind(R.id.tv_music_result_clear_stage_per)
    TextView music_clear_stage_per;
    @Bind(R.id.tv_music_result_average_score)
    TextView music_average_score;
    @Bind(R.id.tv_music_result_average_score_per)
    TextView music_average_score_per;
    @Bind(R.id.tv_music_result_no_miss)
    TextView music_no_miss;
    @Bind(R.id.tv_music_result_no_miss_per)
    TextView music_no_miss_per;
    @Bind(R.id.tv_music_result_full_chain)
    TextView music_full_chain;
    @Bind(R.id.tv_music_result_full_chain_per)
    TextView music_full_chain_per;
    @Bind(R.id.tv_music_result_s)
    TextView music_s;
    @Bind(R.id.tv_music_result_s_per)
    TextView music_s_per;
    @Bind(R.id.tv_music_result_ss)
    TextView music_ss;
    @Bind(R.id.tv_music_result_ss_per)
    TextView music_ss_per;
    @Bind(R.id.tv_music_result_sss)
    TextView music_sss;
    @Bind(R.id.tv_music_result_sss_per)
    TextView music_sss_per;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_data, group, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        // EventBusの登録より前に呼び出すとこっちのが早くて受け取れないので注意する
        new PlayDataUseCase().getPlayData();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(JSONObject playerDataJson) {
        if (playerDataJson == null) {
            Log.d("ktr", "event data is null");
            return;
        }

        try {
            JSONObject pd = playerDataJson.getJSONObject("player_data");
            JSONObject sd = playerDataJson.getJSONObject("stage_data");

            player_name.setText(pd.getString("player_name"));
            player_level.setText("Lv." + pd.getString("level"));
            player_avatar_title.setText(pd.getString("avatar") + "/" + pd.getString("title"));
            player_total_score.setText(String.valueOf(pd.getString("total_score")));
            player_rank.setText(String.valueOf(pd.getString("rank")));
//            player_coin.setText(String.valueOf(ssd.getCurrent_coin()));
            player_trophy.setText(String.valueOf(pd.getString("total_trophy")));

            int total_music = pd.getInt("total_music");
            int total_play_music = pd.getInt("total_play_music");
            int all = sd.getInt("all");
            int clear = sd.getInt("clear");
            int average_score = pd.getInt("average_score");
            int nomiss = sd.getInt("nomiss");
            int fullchain = sd.getInt("fullchain");
            int s = sd.getInt("s");
            int ss = sd.getInt("ss");
            int sss = sd.getInt("sss");


            music_play_music.setText(String.valueOf(total_play_music) + "/" + String.valueOf(total_music));
            music_play_music_per.setText(String.format("%.2f%%", calcPercentage(total_play_music, total_music)));

            music_clear_stage.setText(String.valueOf(clear) + "/" + String.valueOf(all));
            music_clear_stage_per.setText(String.format(("%.2f%%"), calcPercentage(clear, all)));

            music_average_score.setText(String.valueOf(average_score));
            music_average_score_per.setText(String.format("%.2f%%", calcPercentage(average_score, 100000)));

            music_no_miss.setText(String.valueOf(nomiss));
            music_no_miss_per.setText(String.format("%.2f%%", calcPercentage(nomiss, all)));

            music_full_chain.setText(String.valueOf(fullchain));
            music_full_chain_per.setText(String.format("%.2f%%", calcPercentage(fullchain, all)));

            music_s.setText(String.valueOf(s));
            music_s_per.setText(String.format("%.2f%%", calcPercentage(s, all)));

            music_ss.setText(String.valueOf(ss));
            music_ss_per.setText(String.format("%.2f%%", calcPercentage(ss, all)));

            music_sss.setText(String.valueOf(sss));
            music_sss_per.setText(String.format("%.2f%%", calcPercentage(sss, all)));

        } catch (JSONException e) {
            // データが異常の場合だが、SPからの取得の時点で補足するのでなにもしない
            e.printStackTrace();
        }
    }

    private double calcPercentage(int p1, int p2) {
        return (double) p1 / p2 * 100;
    }

}
