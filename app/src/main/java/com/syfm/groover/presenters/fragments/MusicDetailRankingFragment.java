package com.syfm.groover.presenters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;

import butterknife.ButterKnife;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailRankingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_detail_ranking, group, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
