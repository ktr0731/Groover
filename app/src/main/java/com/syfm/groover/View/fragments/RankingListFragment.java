package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.MusicDataUseCase;
import com.syfm.groover.controller.usecases.RankingDataUseCase;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.databases.Ranking.LevelRanking;
import com.syfm.groover.view.adapter.RankingAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2016/03/12.
 */
public class RankingListFragment extends Fragment {

    @Bind(R.id.lv_ranking)
    ListView listView;
    @Bind(R.id.sp_ranking)
    Spinner spinner;

    private String[] arraySpinner;
    private ArrayAdapter<String> adapterSpinner;

    private RankingAdapter adapter;

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

        switch (getArguments().getInt("type")) {
            case 0:
                RankingDataUseCase useCase = new RankingDataUseCase();
                useCase.getRankingData(Const.SP_LEVEL_ALL_RANKING);
                arraySpinner = getResources().getStringArray(R.array.ranking_level);
                adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
                spinner.setAdapter(adapterSpinner);
                break;

            case 1:
                arraySpinner = getResources().getStringArray(R.array.ranking_genre);
                adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
                spinner.setAdapter(adapterSpinner);
                break;

            case 2:
                break;

            default:
                break;
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(String value) {
        if (value == "") {
            Log.d("ktr", "value is empty");
            return;
        } else {
            adapter = new RankingAdapter(getActivity(), new ArrayList<LevelRanking>());
        }
    }
}
