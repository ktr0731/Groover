package com.syfm.groover.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.MusicDataUseCase;
import com.syfm.groover.model.storage.Constants.Const;
import com.syfm.groover.model.storage.databases.MusicData;
import com.syfm.groover.model.storage.databases.ScoreRankData;
import com.syfm.groover.view.adapter.MusicScoreRankingAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailRankingFragment extends Fragment {

    @Bind(R.id.lv_music_detail_ranking)
    ListView listView;

    private Realm realm;
    private int id;
    private int ex_flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getActivity().getIntent();
        this.id = i.getIntExtra(Const.INTENT_MUSIC_ID, 0);
        this.ex_flag = i.getIntExtra(Const.INTENT_EX_FLAG, 0);

        if (id == 0) {
            getActivity().finish();
        }

        // スコアランキングを取得する
        Log.d("fetchMusicData", "MusicDataUseCase.getScoreRanking Start");
        MusicDataUseCase useCase = new MusicDataUseCase();
        // まだ正規化していないため、一度停止
        //useCase.getScoreRanking(String.valueOf(id), String.valueOf(ex_flag));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_detail_ranking, group, false);
        ButterKnife.bind(this, view);

        realm = Realm.getInstance(getActivity());
        MusicData item = realm.where(MusicData.class).equalTo(Const.MUSIC_LIST_MUSIC_ID, id).findFirst();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    public void onEventMainThread(Boolean ready) {
        Log.d("ktr", "fetchScoreRanking: " + ready.toString());
        if(ready) {
            RealmResults<ScoreRankData> ranks = realm.where(ScoreRankData.class).contains(Const.MUSIC_SCORE_DATA_DIFF, "0").findAll();
            MusicScoreRankingAdapter adapter = new MusicScoreRankingAdapter(getActivity(), 0, ranks, true);
            listView.setAdapter(adapter);
        }
    }
}
