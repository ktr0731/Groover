package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.syfm.groover.R;
import com.syfm.groover.controller.entities.event.EventNameEntity;
import com.syfm.groover.controller.usecases.RankingDataUseCase;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.view.adapter.RankingAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2016/03/12.
 */
public class RankingListFragment extends Fragment implements AppCompatSpinner.OnItemSelectedListener {

    @Bind(R.id.lv_ranking)
    ListView listView;
    @Bind(R.id.sp_ranking)
    AppCompatSpinner spinner;
    @Bind(R.id.ll_ranking_loading)
    LinearLayout loadingLayout;

    private String[] arraySpinner;
    private ArrayAdapter<String> adapterSpinner;

    private RankingAdapter adapter = null;
    private int TYPE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_list, group, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        RankingDataUseCase useCase = new RankingDataUseCase();

        this.TYPE = getArguments().getInt("type");

        switch (this.TYPE) {
            case 0:
                useCase.getRankingData(Const.SP_LEVEL_ALL_RANKING);
                arraySpinner = getResources().getStringArray(R.array.ranking_level);
                break;

            case 1:
                //useCase.getRankingData(Const.SP_);
                arraySpinner = getResources().getStringArray(R.array.ranking_genre);
                break;

            case 2:
                useCase.getEventNameList();
                break;

            default:
                break;
        }

        if (arraySpinner != null) {
            adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
            spinner.setAdapter(adapterSpinner);
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        RankingDataUseCase useCase = new RankingDataUseCase();

        loadingLayout.setVisibility(View.VISIBLE);

        // どのデータが表示されたかを解決し、UseCaseにgetする
        switch (this.TYPE) {

            case 0:
                //Level

                switch (position) {
                    case 0:
                        useCase.getRankingData(Const.SP_LEVEL_ALL_RANKING);
                        break;
                    case 1:
                        useCase.getRankingData(Const.SP_LEVEL_SIMPLE_RANKING);
                        break;
                    case 2:
                        useCase.getRankingData(Const.SP_LEVEL_NORMAL_RANKING);
                        break;
                    case 3:
                        useCase.getRankingData(Const.SP_LEVEL_HARD_RANKING);
                        break;
                    case 4:
                        useCase.getRankingData(Const.SP_LEVEL_EXTRA_RANKING);
                        break;
                }
                break;

            case 1:
                // Genre
                AppCompatSpinner spinner = (AppCompatSpinner) parent;

                switch (spinner.getSelectedItem().toString()) {
                    case Const.RANKING_GENRE_JPOP:
                        // Genreのgetメソッドの実装
                        useCase.getRankingData(Const.SP_GENRE_JPOP_RANKING);
                        break;
                    case Const.RANKING_GENRE_ANIME:
                        useCase.getRankingData(Const.SP_GENRE_ANIME_RANKING);
                        break;
                    case Const.RANKING_GENRE_VOCALOID:
                        useCase.getRankingData(Const.SP_GENRE_VOCALOID_RANKING);
                        break;
                    case Const.RANKING_GENRE_TOHO:
                        useCase.getRankingData(Const.SP_GENRE_TOUHOU_RANKING);
                        break;
                    case Const.RANKING_GENRE_GAME:
                        useCase.getRankingData(Const.SP_GENRE_GAME_RANKING);
                        break;
                    case Const.RANKING_GENRE_VARIETY:
                        useCase.getRankingData(Const.SP_GENRE_VARIETY_RANKING);
                        break;
                    case Const.RANKING_GENRE_ORIGINAL:
                        useCase.getRankingData(Const.SP_GENRE_ORIGINAL_RANKING);
                        break;
                }
                break;

            case 2:
                // Event
                useCase.getEventRankingData(position);
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEventMainThread(RankingDataUseCase.RankingList value) {

        if (value.list.isEmpty() || !value.isSuccess) {
            Log.d("ktr", "value is empty");
            Toast.makeText(getActivity(), "ランキングデータの取得に失敗しました。", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adapter != null) {
            // Update
            Log.d("ktr", "list update");
            adapter.updateList(value.list);
        }
        adapter = new RankingAdapter(getActivity(), value.list);
        listView.setAdapter(adapter);
        loadingLayout.setVisibility(View.GONE);
    }

    public void onEventMainThread(RankingDataUseCase.EventNameList value) {

        if (value.list.isEmpty() || !value.isSuccess) {
            Log.d("ktr", "value is empty");
            Toast.makeText(getActivity(), "過去のイベント一覧の取得に失敗しました。", Toast.LENGTH_SHORT).show();
            return;
        }

        int i = 0;
        arraySpinner = new String[value.list.size()];

        for (EventNameEntity e : value.list) {
            arraySpinner[i] = e.getTitle();
            i++;
        }

        adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);

        loadingLayout.setVisibility(View.GONE);
    }
}
