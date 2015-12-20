package com.syfm.groover.presenters.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.syfm.groover.R;
import com.syfm.groover.presenters.fragments.MusicListFragment;
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
        switch (position) {
            case 0:
                // FIXME: Fragmentが生成された際、値が表示されない。getPlayDataが呼ばれる前にsetPlayDataが終わっていないっぽい
                // FIXME: playDataがすべて空だった。
                return new PlayDataFragment();
            case 1:
                return new MusicListFragment();
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
