package com.syfm.groover.view.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.model.storage.databases.Ranking.RankingData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2016/03/13.
 */
public class RankingAdapter extends BaseAdapter {

    private ArrayList<RankingData> list;
    private LayoutInflater inflater;

    public RankingAdapter(Context context, ArrayList<RankingData> list) {
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

        holder.tv_rank.setText(list.get(position).getRank());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_score.setText(list.get(position).getScore_bi1());

        return view;
    }

    public static class ViewHolder {

        @Bind(R.id.tv_ranking_rank)
        TextView tv_rank;
        @Bind(R.id.tv_ranking_title)
        TextView tv_title;
        @Bind(R.id.tv_ranking_score)
        TextView tv_score;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
