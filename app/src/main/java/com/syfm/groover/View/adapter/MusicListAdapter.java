package com.syfm.groover.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.databinding.FragmentMusicListBinding;
import com.syfm.groover.databinding.RowMusicListBinding;
import com.syfm.groover.model.constants.Const;
import com.syfm.groover.model.constants.SPConst;
import com.syfm.groover.model.databases.Music;
import com.syfm.groover.model.databases.ResultData;
import com.syfm.groover.model.databases.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by lycoris on 2015/10/09.
 */
public class MusicListAdapter extends RealmBaseAdapter<Music> implements ListAdapter {
    private Realm realm;
    private RowMusicListBinding binding;

    public MusicListAdapter(Context context, int resource, RealmResults<Music> realmResults, Boolean autoUpdate) {
        super(context, realmResults, autoUpdate);
        realm = Realm.getInstance(AppController.getInstance());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view != null) {
            binding = (RowMusicListBinding) view.getTag();

            // 使いまわすとデータが残ってしまうものがあるので毎回消す。
            clearSetData(binding);
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.row_music_list, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        }

        Music row = getItem(position);

        // コンストラクタでセットしたmusicsの実体がrealmResults
        binding.setMusic(row);
        binding.setSimple(row.getSimpleResult());
        binding.setNormal(row.getNormalResult());
        binding.setHard(row.getHardResult());
        binding.setExtra(row.getExtraResult());

        // FullChainを表示させるか (fullchainはFullChainした回数)
        // XMLだと見づらいのでこちらでチェックする
        // TODO: もう少し綺麗にしたい
        if (row.getSimpleResult() != null) {
            if (row.getSimpleResult().getPerfect() > 0) {
                // TODO: Perfect用の色を用意する
                binding.musicRowSimpleScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getSimpleResult().getFullChain() > 0) {
                binding.musicRowSimpleScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getSimpleResult().getNoMiss() > 0) {
                binding.musicRowSimpleScore.setBackgroundResource(R.drawable.no_miss_border);
            }
        }

        if (row.getNormalResult() != null) {
            if (row.getNormalResult().getPerfect() > 0) {
                binding.musicRowNormalScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getNormalResult().getFullChain() > 0) {
                binding.musicRowNormalScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getNormalResult().getNoMiss() > 0) {
                binding.musicRowNormalScore.setBackgroundResource(R.drawable.no_miss_border);
            }
        }

        if (row.getHardResult() != null) {
            if (row.getHardResult().getPerfect() > 0) {
                binding.musicRowHardScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getHardResult().getFullChain() > 0) {
                binding.musicRowHardScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getHardResult().getNoMiss() > 0) {
                binding.musicRowHardScore.setBackgroundResource(R.drawable.no_miss_border);
            }
        }

        if (row.getExtraResult() != null) {
            if (row.getExtraResult().getPerfect() > 0 && row.isExFlag()) {
                binding.musicRowExtraScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getExtraResult().getFullChain() > 0 && row.isExFlag()) {
                binding.musicRowExtraScore.setBackgroundResource(R.drawable.full_chain_border);
            } else if (row.getExtraResult().getNoMiss() > 0 && row.isExFlag()) {
                binding.musicRowExtraScore.setBackgroundResource(R.drawable.no_miss_border);
            }
        }


        // いっぱい読込すると落ちるかもしれない
        // BitmapはXMLで指定できないのでここで行う
        Bitmap bmp = BitmapFactory.decodeByteArray(
                row.getThumbnail(),
                0,
                row.getThumbnail().length
        );
        binding.musicRowThumb.setImageBitmap(bmp);

        return view;
    }

    private void clearSetData(RowMusicListBinding binding) {
        binding.musicRowSimpleRate.setText("-");
        binding.musicRowSimpleScore.setText("0");
        binding.musicRowSimpleScore.setBackgroundResource(0);
        binding.musicRowNormalRate.setText("-");
        binding.musicRowNormalScore.setText("0");
        binding.musicRowNormalScore.setBackgroundResource(0);
        binding.musicRowHardRate.setText("-");
        binding.musicRowHardScore.setText("0");
        binding.musicRowHardScore.setBackgroundResource(0);
        binding.musicRowExtraRate.setText("-");
        binding.musicRowExtraScore.setText("0");
        binding.musicRowExtraScore.setBackgroundResource(0);
        binding.llExtra.setVisibility(View.GONE);
        binding.musicRowThumb.setImageBitmap(null);
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

                realm.beginTransaction();

//                RealmQuery<MusicData> query = realm.where(MusicData.class).contains(Const.MUSIC_LIST_MUSIC_TITLE, constraint.toString(), Case.INSENSITIVE);
//                RealmResults<MusicData> items = query.findAll();
//                realmResults = items;
                realm.commitTransaction();
                notifyDataSetChanged();
            }

        };
    }

    public void sortList() {
        Map<String, String> info = SharedPreferenceHelper.getMusicSortInfo();
        if (!info.isEmpty()) {
            ArrayList<String> params = new ArrayList<>();
            params.add(info.get(SPConst.MUSIC_LIST_SORT_SORT_TYPE));
            params.add(info.get(SPConst.MUSIC_LIST_SORT_ORDER_BY));
            sortList(params);
        }
    }

    public void sortList(ArrayList<String> params) {
        String tmp = params.get(1);
        Sort sort = null;
        if (tmp == "asc") {
            sort = Sort.ASCENDING;
        } else {
            sort = Sort.DESCENDING;
        }

        realm.beginTransaction();
        switch (params.get(0).toString()) {
            case Const.MUSIC_SORT_MUSIC_NAME:
                realmResults.sort(Const.MUSIC_LIST_MUSIC_TITLE, sort);
                break;
            case Const.MUSIC_SORT_LAST_PLAYED:
                realmResults.sort(Const.MUSIC_LIST_LAST_PLAY_TIME, sort);
                break;
            case Const.MUSIC_SORT_PLAY_TIMES:
                realmResults.sort(Const.MUSIC_LIST_PLAY_COUNT, sort);
                break;
            case Const.MUSIC_SORT_CATEGORY:
                // TODO: カテゴリを入手する
                break;

        }

        realm.commitTransaction();
        notifyDataSetChanged();
    }

}
