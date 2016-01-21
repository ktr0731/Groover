package com.syfm.groover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.LoginUseCase;
import com.syfm.groover.model.network.AppController;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.SharedPreferenceHelper;
import com.syfm.groover.view.adapter.MainFragmentPagerAdapter;
import com.syfm.groover.view.fragments.MusicListFragment;
import com.syfm.groover.view.fragments.MusicSortDialogFragment;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_pager)
    ViewPager pager;
    @Bind(R.id.tab_strip)
    PagerSlidingTabStrip tabStrip;
    @Bind(R.id.toolbar_main)
    Toolbar toolbar;

    private SearchView searchView;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        ButterKnife.bind(this);

        //SPにCookieがあったら
        SharedPreferenceHelper.create(getApplicationContext());
        Map<String, String> loginData = SharedPreferenceHelper.getLoginInfo();

        if (loginData.size() > 0 && !AppController.getInstance().checkLoginCookie()) {
            //入力させないでログイン
            LoginUseCase loginUseCase = new LoginUseCase();
            String serialNo = loginData.get(Const.SP_LOGIN_NESICA_ID);
            String password = loginData.get(Const.SP_LOGIN_PASSWORD);
            loginUseCase.checkLogin(serialNo, password);

        } else if (!AppController.getInstance().checkLoginCookie()) {
            //Go to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            int code = getResources().getInteger(R.integer.status_code_login);
            startActivityForResult(intent, code);
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(fragmentManager, this);
        pager.setAdapter(mainFragmentPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                toolbar.getMenu().clear();
                switch (position) {
                    case 0:
                        toolbar.inflateMenu(R.menu.menu_play_data);
                        break;
                    case 1:
                        toolbar.inflateMenu(R.menu.menu_music_list);

                        toolbar.setOnMenuItemClickListener(item -> {
                            switch (item.getItemId()) {
                                case R.id.menu_music_list_sort:
                                    MusicSortDialogFragment dialog = new MusicSortDialogFragment();
                                    dialog.show(getFragmentManager(), "dialog-music-sort");
                                    break;
                            }

                            return true;
                        });

                        // TODO: textColor等の変更
                        searchView = (SearchView) toolbar.getMenu().findItem(R.id.menu_music_list_search).getActionView();

                        searchView.setQueryHint("Music Name...");
                        searchView.setMinimumWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // TODO: マジックナンバーの定数化
                                MusicListFragment fragment = (MusicListFragment) mainFragmentPagerAdapter.instantiateItem(pager, 1);
                                fragment.searchMusic(newText);
                                return false;
                            }
                        });
                        break;
                    default:
                        toolbar.inflateMenu(R.menu.menu_play_data);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        tabStrip.setViewPager(pager);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
