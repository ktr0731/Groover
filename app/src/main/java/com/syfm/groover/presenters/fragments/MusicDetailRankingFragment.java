package com.syfm.groover.presenters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syfm.groover.R;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.MusicData;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailRankingFragment extends Fragment {

    @Bind(R.id.lv_music_detail_ranking)
    ListView listView;

    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_detail_ranking, group, false);
        ButterKnife.bind(this, view);

        Intent i = getActivity().getIntent();
        int id = i.getIntExtra(Const.INTENT_MUSIC_ID, 0);

        if (id == 0) {
            getActivity().finish();
        }

        realm = Realm.getInstance(getActivity());
        MusicData item = realm.where(MusicData.class).equalTo(Const.MUSIC_LIST_MUSIC_ID, id).findFirst();
        Log.d("Unko", item.getScore_rank().size() + "個");
        // TODO: music_idをMusicScoreRankingに追加する
        //realm.where(ScoreRankData.class).equalTo()
        //MusicScoreRankingAdapter adapter = new MusicScoreRankingAdapter(getActivity(), 0, , true);
        //listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
