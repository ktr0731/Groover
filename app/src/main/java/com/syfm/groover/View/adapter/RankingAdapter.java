package com.syfm.groover.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.controller.entities.ranking.RankingDataEntity;
import com.syfm.groover.databinding.RowRankingBinding;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2016/03/13.
 */
public class RankingAdapter extends BaseAdapter {

    private ArrayList<RankingDataEntity> list;
    private LayoutInflater inflater;
    private Context context;
    
    private RowRankingBinding binding;

    public RankingAdapter(Context context, ArrayList<RankingDataEntity> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view != null) {
            binding = (RowRankingBinding) view.getTag();
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.row_ranking, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        }

        binding.tvRankingRank.setText(String.valueOf(list.get(position).getRank()));
        binding.tvRankingPlayerName.setText(list.get(position).getPlayer_name());
        binding.tvRankingScore.setText(String.valueOf(list.get(position).getScore_bi1()));

        switch (list.get(position).getRank()) {
            case 1:
                if (Build.VERSION.SDK_INT >= 23) {
                    binding.tvRankingRank.setTextColor(context.getResources().getColor(R.color.ranking_1st));
                } else {
                    binding.tvRankingRank.setTextColor(ContextCompat.getColor(context, R.color.ranking_1st));
                }
                break;

            case 2:
                if (Build.VERSION.SDK_INT >= 23) {
                    binding.tvRankingRank.setTextColor(context.getResources().getColor(R.color.ranking_2nd));
                } else {
                    binding.tvRankingRank.setTextColor(ContextCompat.getColor(context, R.color.ranking_2nd));
                }
                break;

            case 3:
                if (Build.VERSION.SDK_INT >= 23) {
                    binding.tvRankingRank.setTextColor(context.getResources().getColor(R.color.ranking_3rd));
                } else {
                    binding.tvRankingRank.setTextColor(ContextCompat.getColor(context, R.color.ranking_3rd));
                }
                break;

            default:
                if (Build.VERSION.SDK_INT >= 23) {
                    binding.tvRankingRank.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    binding.tvRankingRank.setTextColor(ContextCompat.getColor(context, R.color.black));
                }

        }

        return view;
    }

    public void updateList(ArrayList<RankingDataEntity> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
