package com.syfm.groover.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.syfm.groover.R;
import com.syfm.groover.view.fragments.CurrentEventDataFragment;
import com.syfm.groover.view.fragments.MusicListFragment;
import com.syfm.groover.view.fragments.PlayDataFragment;
import com.syfm.groover.view.fragments.RankingFragment;

/**
 * Created by lycoris on 2015/09/22.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private static final int PAGE_COUNT = 4;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager, Context mContext) {
        super(fragmentManager);
        context = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PlayDataFragment();
            case 1:
                return new MusicListFragment();
            case 2:
                return new RankingFragment();
            case 3:
                return new CurrentEventDataFragment();
            default:
                return new PlayDataFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = context.getResources().getStringArray(R.array.tab_name);
        return title[position];
    }

}
