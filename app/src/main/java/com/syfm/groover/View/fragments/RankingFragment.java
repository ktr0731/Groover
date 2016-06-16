package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/10/09.
 */

/**
 * RankingFragment
 * This fragment is contains three fragments for display level/genre/event ranking list.
 */
public class RankingFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.ranking_tablayout)
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, group, false);
        ButterKnife.bind(this, view);

        tabLayout.addTab(tabLayout.newTab().setText("Level"));
        tabLayout.addTab(tabLayout.newTab().setText("Genre"));
        tabLayout.addTab(tabLayout.newTab().setText("Event"));

        tabLayout.setOnTabSelectedListener(this);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        // 初期画面の指定
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);

        RankingListFragment fragment = new RankingListFragment();
        fragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.ranking_container, fragment).commit();

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        FragmentManager fm = getActivity().getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putInt("type", tab.getPosition());

        RankingListFragment fragment = new RankingListFragment();
        fragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.ranking_container, fragment).commit();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

}
