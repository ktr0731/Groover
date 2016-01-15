package com.syfm.groover.view.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.PlayDataUseCase;
import com.syfm.groover.view.adapter.MusicDetailFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lycoris on 2015/10/12.
 */
public class MusicDetailActivity extends AppCompatActivity {

    @Bind(R.id.pager_music_detail)
    ViewPager pager;
    @Bind(R.id.tab_strip_music_detail)
    PagerSlidingTabStrip tabStrip;
    @Bind(R.id.toolbar_music_detail)
    Toolbar toolbar;

    @Bind(R.id.bottom_close)
    android.support.design.widget.FloatingActionButton btn_close;

    @OnClick(R.id.bottom_close)
    public void onClickCloseBotton() {
        finish();
    }

    private MusicDetailFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        ButterKnife.bind(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MusicDetailFragmentPagerAdapter(fragmentManager, this);
        pager.setAdapter(adapter);

        tabStrip.setViewPager(pager);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
        new PlayDataUseCase().getPlayData();
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
