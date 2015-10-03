package com.syfm.groover.presenters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.business.entities.PlayData;
import com.syfm.groover.business.usecases.PlayDataUseCase;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/22.
 */
public class PlayDataFragment extends Fragment {

    private TextView player_name;
    private TextView player_level;
    private TextView player_avatar_title;
    private TextView player_total_score;
    private TextView player_rank;
    private TextView player_coin;
    private TextView player_trophy;

    private TextView music_play_music;
    private TextView music_play_music_per;
    private TextView music_clear_stage;
    private TextView music_clear_stage_per;
    private TextView music_average_score;
    private TextView music_average_score_per;
    private TextView music_no_miss;
    private TextView music_no_miss_per;
    private TextView music_full_chain;
    private TextView music_full_chain_per;
    private TextView music_s;
    private TextView music_s_per;
    private TextView music_ss;
    private TextView music_ss_per;
    private TextView music_sss;
    private TextView music_sss_per;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = 0;
        String[] tab_name = getResources().getStringArray(R.array.tab_name);

        if (bundle != null) {
            position = bundle.getInt("position");
        }

        View view = inflater.inflate(R.layout.fragment_play_data, group, false);

        player_name = (TextView)view.findViewById(R.id.tv_player_data_name);
        player_level = (TextView)view.findViewById(R.id.tv_player_data_level);
        player_avatar_title = (TextView)view.findViewById(R.id.tv_player_data_avatar_title);
        player_total_score = (TextView)view.findViewById(R.id.tv_player_data_total_score);
        player_rank = (TextView)view.findViewById(R.id.tv_player_data_rank);
        player_coin = (TextView)view.findViewById(R.id.tv_player_data_coin);
        player_trophy = (TextView)view.findViewById(R.id.tv_player_data_trophy);

        music_play_music = (TextView)view.findViewById(R.id.tv_music_result_play_music);
        music_play_music_per = (TextView)view.findViewById(R.id.tv_music_result_play_music_per);
        music_clear_stage = (TextView)view.findViewById(R.id.tv_music_result_clear_stage);
        music_clear_stage_per = (TextView)view.findViewById(R.id.tv_music_result_clear_stage_per);
        music_average_score = (TextView)view.findViewById(R.id.tv_music_result_average_score);
        music_average_score_per = (TextView)view.findViewById(R.id.tv_music_result_average_score_per);
        music_no_miss = (TextView)view.findViewById(R.id.tv_music_result_no_miss);
        music_no_miss_per = (TextView)view.findViewById(R.id.tv_music_result_no_miss_per);
        music_full_chain = (TextView)view.findViewById(R.id.tv_music_result_full_chain);
        music_full_chain_per = (TextView)view.findViewById(R.id.tv_music_result_full_chain_per);
        music_s = (TextView)view.findViewById(R.id.tv_music_result_s);
        music_s_per = (TextView)view.findViewById(R.id.tv_music_result_s_per);
        music_ss = (TextView)view.findViewById(R.id.tv_music_result_ss);
        music_ss_per = (TextView)view.findViewById(R.id.tv_music_result_ss_per);
        music_sss = (TextView)view.findViewById(R.id.tv_music_result_sss);
        music_sss_per = (TextView)view.findViewById(R.id.tv_music_result_sss_per);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        new PlayDataUseCase().getPlayerData();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(PlayDataUseCase.PlayDataEvent event) {
        if (event.playData != null) {
            PlayData d = event.playData;
            player_name.setText(d.player_name);
            player_level.setText("Lv." + d.level);
            player_avatar_title.setText(d.avatar + "/" + d.title);
            player_total_score.setText(String.valueOf(d.total_score));
            player_rank.setText(String.valueOf(d.rank));
            player_coin.setText(String.valueOf(d.coin));
            player_trophy.setText(String.valueOf(d.total_trophy));

            music_play_music.setText(String.valueOf(d.total_play_music) + "/" + String.valueOf(d.total_music));
            music_play_music_per.setText(String.format("%.2f%%", (float)d.total_play_music / d.total_music * 100));
            music_clear_stage.setText(String.valueOf(d.clear_stage) + "/" + String.valueOf(d.all_stage));
            music_clear_stage_per.setText(String.format(("%.2f%%"), (float)d.clear_stage / d.all_stage * 100));
            music_average_score.setText(String.valueOf(d.average_score));
            music_average_score_per.setText(String.format("%.2f%%", (float)d.average_score / 1000000 * 100));
            music_no_miss.setText(String.valueOf(d.nomiss));
            music_no_miss_per.setText(String.format("%.2f%%", (float)d.nomiss / d.all_stage * 100));
            music_full_chain.setText(String.valueOf(d.fullchain));
            music_full_chain_per.setText(String.format("%.2f%%", (float)d.fullchain / d.all_stage * 100));
            music_s.setText(String.valueOf(d.s));
            music_s_per.setText(String.format("%.2f%%", (float)d.s / d.all_stage * 100));
            music_ss.setText(String.valueOf(d.ss));
            music_ss_per.setText(String.format("%.2f%%", (float)d.ss / d.all_stage * 100));
            music_sss.setText(String.valueOf(d.sss));
            music_sss_per.setText(String.format("%.2f%%", (float)d.sss / d.all_stage * 100));


        }
    }



}
