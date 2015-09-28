package com.syfm.groover.presenters.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.syfm.groover.R;
import com.syfm.groover.presenters.fragments.PlayDataFragment;

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
        return PlayDataFragment.newInstance(position);
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
