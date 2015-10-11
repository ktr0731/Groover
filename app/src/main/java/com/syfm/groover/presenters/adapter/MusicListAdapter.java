package com.syfm.groover.presenters.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.syfm.groover.R;
import com.syfm.groover.data.network.CustomImageLoader;
import com.syfm.groover.data.storage.CustomLruCache;
import com.syfm.groover.data.storage.databases.ResultData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2015/10/09.
 */
public class MusicListAdapter extends ArrayAdapter<List<ResultData>> {
    private LayoutInflater inflater = null;
    private ArrayList<List<ResultData>> list;
    private Context context;

    private CustomImageLoader imageLoader;

    public MusicListAdapter(Context context, int resource, ArrayList<List<ResultData>> list, RequestQueue queue) {
        super(context, resource, list);
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        imageLoader = new CustomImageLoader(queue, new CustomLruCache());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=";
        ViewHolder holder;

        if(view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.music_row, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        /*
         * get(n)
         *   0: simple
         *   1: normal
         *   2: hard
         *   3: extra(Extraのない曲もあるのでex_flagで確認すること)
         *
         */


        List<ResultData> row = getItem(position);

        //使いまわすとデータが残ってしまうものがあるので毎回消す。
        clearSetData(holder);

        holder.tv_title.setText(row.get(0).musicData.music_title);
        holder.tv_simple_rate.setText(row.get(0).rating);
        holder.tv_simple_score.setText(String.valueOf(row.get(0).score));
        holder.tv_normal_rate.setText(row.get(1).rating);
        holder.tv_normal_score.setText(String.valueOf(row.get(1).score));
        holder.tv_hard_rate.setText(row.get(2).rating);
        holder.tv_hard_score.setText(String.valueOf(row.get(2).score));

        //Extraを表示させるか
        if(row.get(0).musicData.ex_flag == 1) {
            holder.ll_extra.setVisibility(View.VISIBLE);
            holder.tv_extra_rate.setText(row.get(3).rating);
            holder.tv_extra_score.setText(String.valueOf(row.get(3).score));
        }

        //FullChainを表示させるか (fullchainはFullChainした回数)
        if (row.get(0).full_chain > 0) {
            holder.tv_simple_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.get(0).no_miss > 0) {
            holder.tv_simple_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.get(1).full_chain > 0) {
            holder.tv_normal_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.get(1).no_miss > 0) {
            holder.tv_normal_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.get(2).full_chain > 0) {
            holder.tv_hard_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.get(2).no_miss > 0) {
            holder.tv_hard_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.get(3).full_chain > 0 && row.get(3).musicData.ex_flag == 1) {
            holder.tv_extra_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.get(3).no_miss > 0 && row.get(3).musicData.ex_flag == 1) {
            holder.tv_extra_score.setBackgroundResource(R.drawable.no_miss_border);
        }

        CustomImageLoader.ImageContainer imageContainer = (CustomImageLoader.ImageContainer)holder.iv_thumb.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }

        CustomImageLoader.ImageListener listener = CustomImageLoader.getImageListener(holder.iv_thumb, holder.image_progress_bar, 0);
        Log.d("Unko", "Access to ID:" + row.get(0).musicData.music_id + " name:" + row.get(0).musicData.music_title);
        holder.iv_thumb.setTag(imageLoader.get(url + row.get(0).musicData.music_id, listener));

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
        @Bind(R.id.ll_extra)
        LinearLayout ll_extra;

        @Bind(R.id.image_progress_bar)
        ProgressBar image_progress_bar;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void clearSetData(ViewHolder holder) {
        holder.tv_simple_rate.setText("-");
        holder.tv_simple_score.setText("0");
        holder.tv_simple_score.setBackgroundResource(0);
        holder.tv_normal_rate.setText("-");
        holder.tv_normal_score.setText("0");
        holder.tv_normal_score.setBackgroundResource(0);
        holder.tv_hard_rate.setText("-");
        holder.tv_hard_score.setText("0");
        holder.tv_hard_score.setBackgroundResource(0);
        holder.tv_extra_rate.setText("-");
        holder.tv_extra_score.setText("0");
        holder.tv_extra_score.setBackgroundResource(0);
        holder.ll_extra.setVisibility(View.GONE);
        holder.iv_thumb.setImageBitmap(null);
    }
}
