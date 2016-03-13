package com.syfm.groover.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.syfm.groover.model.storage.databases.Ranking.LevelRanking;

import java.util.ArrayList;

/**
 * Created by lycoris on 2016/03/13.
 */
public class RankingAdapter extends BaseAdapter {

    private ArrayList<LevelRanking> list;

    public RankingAdapter(Context context, ArrayList<LevelRanking> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return view;
    }

    @Override
    public int getCount() {
        return 5;
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
