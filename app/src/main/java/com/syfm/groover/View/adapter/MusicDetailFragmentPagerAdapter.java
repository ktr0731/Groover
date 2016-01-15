package com.syfm.groover.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.syfm.groover.R;
import com.syfm.groover.view.fragments.MusicDetailFragment;
import com.syfm.groover.view.fragments.MusicDetailRankingFragment;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private static final int PAGE_COUNT = 2;

    public MusicDetailFragmentPagerAdapter(FragmentManager fragmentManager, Context mContext) {
        super(fragmentManager);
        context = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicDetailFragment();
            case 1:
                return new MusicDetailRankingFragment();
            default:
                return new MusicDetailFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = context.getResources().getStringArray(R.array.tab_name_music_detail);
        return title[position];
    }
}
