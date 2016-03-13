package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.RankingDataUseCase;
import com.syfm.groover.model.storage.Const;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2016/03/12.
 */
public class RankingListFragment extends Fragment {

    @Bind(R.id.lv_ranking)
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_list, group, false);
        ButterKnife.bind(this, view);

        switch (getArguments().getInt("type")) {
            case 0:
                RankingDataUseCase useCase = new RankingDataUseCase();

                useCase.getRankingData(Const.SP_LEVEL_ALL_RANKING);
                Log.d("ktr", "getRankingData was called");
                break;

            case 1:
                break;

            case 2:
                break;

            default:
                break;
        }

        return view;
    }
}
