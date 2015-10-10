package com.syfm.groover.presenters.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.data.storage.databases.MusicData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2015/10/09.
 */
public class MusicListAdapter extends ArrayAdapter<MusicData> {
    private LayoutInflater inflater = null;

    public MusicListAdapter(Context context, int resource, List<MusicData> list) {
        super(context, resource, list);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.music_row, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        return view;
    }

    public static class ViewHolder {

        @Bind(R.id.music_row_thumb)
        ImageView iv_thumb;
        @Bind(R.id.music_row_title)
        TextView tv_title;
        @Bind(R.id.music_row_simple_rate)
        TextView tv_simple_rate;
        @Bind(R.id.music_row_simple_score)
        TextView tv_simple_score;
        @Bind(R.id.music_row_normal_rate)
        TextView tv_normal_rate;
        @Bind(R.id.music_row_normal_score)
        TextView tv_normal_score;
        @Bind(R.id.music_row_hard_rate)
        TextView tv_hard_rate;
        @Bind(R.id.music_row_hard_score)
        TextView tv_hard_score;
        @Bind(R.id.music_row_extra_rate)
        TextView tv_extra_rate;
        @Bind(R.id.music_row_extra_score)
        TextView tv_extra_score;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
