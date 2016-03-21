package com.syfm.groover.view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.controller.entities.ranking.RankingDataEntity;

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

    public RankingAdapter(Context context, ArrayList<RankingDataEntity> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
            //使いまわすとデータが残ってしまうものがあるので毎回消す。
            //clearSetData(holder);
        } else {
            view = inflater.inflate(R.layout.row_ranking, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.tv_rank.setText(String.valueOf(list.get(position).getRank()));
        holder.tv_player_name.setText(list.get(position).getPlayer_name());
        holder.tv_score.setText(String.valueOf(list.get(position).getScore_bi1()));

        switch (list.get(position).getRank()) {
            case 1:
                if (Build.VERSION.SDK_INT >= 23) {
                    holder.tv_rank.setTextColor(context.getResources().getColor(R.color.ranking_1st));
                } else {
                    holder.tv_rank.setTextColor(ContextCompat.getColor(context, R.color.ranking_1st));
                }
                break;

            case 2:
                if (Build.VERSION.SDK_INT >= 23) {
                    holder.tv_rank.setTextColor(context.getResources().getColor(R.color.ranking_2nd));
                } else {
                    holder.tv_rank.setTextColor(ContextCompat.getColor(context, R.color.ranking_2nd));
                }
                break;

            case 3:
                if (Build.VERSION.SDK_INT >= 23) {
                    holder.tv_rank.setTextColor(context.getResources().getColor(R.color.ranking_3rd));
                } else {
                    holder.tv_rank.setTextColor(ContextCompat.getColor(context, R.color.ranking_3rd));
                }
                break;
        }

        return view;
    }

    public static class ViewHolder {

        @Bind(R.id.tv_ranking_rank)
        TextView tv_rank;
        @Bind(R.id.tv_ranking_player_name)
        TextView tv_player_name;
        @Bind(R.id.tv_ranking_score)
        TextView tv_score;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
