package com.syfm.groover.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListAdapter;

import com.syfm.groover.R;
import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.databinding.RowMusicDetailRankingBinding;
import com.syfm.groover.model.databases.ScoreRankData;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicScoreRankingAdapter extends RealmBaseAdapter<ScoreRankData> implements ListAdapter {
    private Realm realm;
    
    private RowMusicDetailRankingBinding binding;

    public MusicScoreRankingAdapter(Context context, int resource, RealmResults<ScoreRankData> realmResults, Boolean autoUpdate) {
        super(context, realmResults, autoUpdate);
        realm = Realm.getInstance(AppController.getInstance());
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

        ScoreRankData row = realmResults.get(position);

        binding.tvMusicDetailRankingName.setText(row.getPlayer_name());
        binding.tvMusicDetailRankingRank.setText(row.getRank() + getDaySuffix(row.getRank()));
        binding.tvMusicDetailRankingTitle.setText(row.getTitle());
        binding.tvMusicDetailRankingScore.setText(String.valueOf(row.getEvent_point()));
        binding.tvMusicDetailRankingArea.setText(row.getPref());
        binding.tvMusicDetailRankingArcade.setText(row.getLast_play_tenpo_name());

        return view;
    }

    private String getDaySuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    // 検索用にオーバーライド
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                //ArrayList<List<ResultData>> list = new ArrayList<>();

                // FIXME: whereでPlace Holderが１つしか使えないので直す
                // FIXME: UseCaseを経由するようにする
                // 呼ばれてない?
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
//
//                realm.beginTransaction();
//
//                RealmQuery<MusicData> query = realm.where(MusicData.class).contains(Const.MUSIC_LIST_MUSIC_TITLE, constraint.toString(), Case.INSENSITIVE);
//                RealmResults<MusicData> items = query.findAll();
//                realmResults = items;
//                realm.commitTransaction();
//                notifyDataSetChanged();
            }

        };
    }

    public RealmResults<ScoreRankData> getRealmResults() {
        return realmResults;
    }
}
