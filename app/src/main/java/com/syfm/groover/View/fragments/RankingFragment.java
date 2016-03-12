package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lycoris on 2015/10/09.
 */
public class RankingFragment extends Fragment {

    @Bind(R.id.ranking_tablayout)
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, group, false);
        ButterKnife.bind(this, view);

        tabLayout.addTab(tabLayout.newTab().setText("Level"));
        tabLayout.addTab(tabLayout.newTab().setText("Genre"));
        tabLayout.addTab(tabLayout.newTab().setText("Event"));


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
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
