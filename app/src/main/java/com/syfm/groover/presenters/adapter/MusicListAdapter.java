package com.syfm.groover.presenters.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.syfm.groover.R;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.MusicData;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2015/10/09.
 */
public class MusicListAdapter extends RealmBaseAdapter<MusicData> implements ListAdapter {
    private Realm realm;


    public MusicListAdapter(Context context, int resource, RealmResults<MusicData> realmResults, Boolean autoUpdate) {
        super(context, realmResults, autoUpdate);
        realm = Realm.getInstance(AppController.getInstance());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final String url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=";
        ViewHolder holder;

        if(view != null) {
            holder = (ViewHolder) view.getTag();
            //使いまわすとデータが残ってしまうものがあるので毎回消す。
            clearSetData(holder);
        } else {
            view = inflater.inflate(R.layout.row_music_list, parent, false);
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


        MusicData row = realmResults.get(position);

        holder.tv_title.setText(row.getMusic_title());
        holder.tv_simple_rate.setText(row.getResult_data().get(0).getRating());
        holder.tv_simple_score.setText(String.valueOf(row.getResult_data().get(0).getScore()));
        holder.tv_normal_rate.setText(row.getResult_data().get(1).getRating());
        holder.tv_normal_score.setText(String.valueOf(row.getResult_data().get(1).getScore()));
        holder.tv_hard_rate.setText(row.getResult_data().get(2).getRating());
        holder.tv_hard_score.setText(String.valueOf(row.getResult_data().get(2).getScore()));

        //Extraを表示させるか
        if(row.getEx_flag() == 1) {
            holder.ll_extra.setVisibility(View.VISIBLE);
            holder.tv_extra_rate.setText(row.getResult_data().get(3).getRating());
            holder.tv_extra_score.setText(String.valueOf(row.getResult_data().get(3).getScore()));
        }

        //FullChainを表示させるか (fullchainはFullChainした回数)
        if (row.getResult_data().get(0).getFull_chain() > 0) {
            holder.tv_simple_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.getResult_data().get(0).getNo_miss() > 0) {
            holder.tv_simple_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.getResult_data().get(1).getFull_chain() > 0) {
            holder.tv_normal_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.getResult_data().get(1).getNo_miss() > 0) {
            holder.tv_normal_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.getResult_data().get(2).getFull_chain() > 0) {
            holder.tv_hard_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.getResult_data().get(2).getNo_miss() > 0) {
            holder.tv_hard_score.setBackgroundResource(R.drawable.no_miss_border);
        }
        if (row.getResult_data().get(3).getFull_chain() > 0 && row.getEx_flag() == 1) {
            holder.tv_extra_score.setBackgroundResource(R.drawable.full_chain_border);
        } else if(row.getResult_data().get(3).getNo_miss() > 0 && row.getEx_flag() == 1) {
            holder.tv_extra_score.setBackgroundResource(R.drawable.no_miss_border);
        }


        //いっぱい読込すると落ちるかもしれない
        Bitmap bmp = BitmapFactory.decodeByteArray(row.getMusic_thumbnail(), 0, row.getMusic_thumbnail().length);
        holder.iv_thumb.setImageBitmap(bmp);

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

    // 検索用にオーバーライド
    public Filter getFilter()  {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                //ArrayList<List<ResultData>> list = new ArrayList<>();

                // FIXME: whereでPlace Holderが１つしか使えないので直す
                // FIXME: UseCaseを経由するようにする
//                realm.beginTransaction();
//                RealmQuery<MusicData> query = realm.where(MusicData.class);
//                RealmResults<MusicData> musicFilterData = query.equalTo(Const.MUSIC_LIST_MUSIC_TITLE, "聖者の息吹").findAll();
//                //List<MusicData> musicFilterData = new Select().from(MusicData.class).where(Const.MUSIC_LIST_MUSIC_TITLE + " like ?", "%" + constraint.toString() + "%").orderBy("Id desc").execute();
//                realm.commitTransaction();
//
//                results.count = musicFilterData.size();
//                results.values = musicFilterData;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                realm.beginTransaction();

                RealmQuery<MusicData> query = realm.where(MusicData.class).contains(Const.MUSIC_LIST_MUSIC_TITLE, constraint.toString());
                RealmResults<MusicData> items = query.findAll();
                realmResults = items;
                if(realmResults!=null&&!realmResults.isEmpty()) {
                    for(MusicData e : realmResults)
                    Log.d("Unko", e.getMusic_title());
                }
                realm.commitTransaction();
                notifyDataSetChanged();
            }

        };
    }

    public RealmResults<MusicData> getRealmResults() {
        return realmResults;
    }
}
