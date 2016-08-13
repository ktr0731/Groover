package com.syfm.groover.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.syfm.groover.R;
import com.syfm.groover.databinding.RowMusicDetailRankingBinding;
import com.syfm.groover.model.databases.ScoreRankData;

/**
 * Created by lycoris on 2016/01/10.
 */

/**
 * MusicScoreRankingAdapter is used by display score ranking of the music in MusicScoreRankingFragment.
 * MusicScoreRankAdapter receive context and music_id.
 * Get music ranking data from SharedPreferences based on music_id.
 *
 * SharedPreference key:
 * MUSIC_SCORE_RANKING + music_id
 */
public class MusicScoreRankingAdapter extends BaseAdapter {

    private RowMusicDetailRankingBinding binding;
    private LayoutInflater inflater;
    private Context context;

    public MusicScoreRankingAdapter(Context context, int music_id) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view != null) {
            binding = (RowMusicDetailRankingBinding) view.getTag();
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.row_music_detail_ranking, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        }

//        ScoreRankData row = realmResults.get(position);
//
//        binding.tvMusicDetailRankingName.setText(row.getPlayer_name());
//        binding.tvMusicDetailRankingRank.setText(row.getRank() + getDaySuffix(row.getRank()));
//        binding.tvMusicDetailRankingTitle.setText(row.getTitle());
//        binding.tvMusicDetailRankingScore.setText(String.valueOf(row.getEvent_point()));
//        binding.tvMusicDetailRankingArea.setText(row.getPref());
//        binding.tvMusicDetailRankingArcade.setText(row.getLast_play_tenpo_name());

        return view;
    }

    private String getDaySuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
