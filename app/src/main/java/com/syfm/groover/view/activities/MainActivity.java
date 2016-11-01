package com.syfm.groover.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.LoginUseCase;
import com.syfm.groover.databinding.ContainerBinding;
import com.syfm.groover.controller.entities.AppController;
import com.syfm.groover.model.constants.SPConst;
import com.syfm.groover.model.databases.SharedPreferenceHelper;
import com.syfm.groover.view.adapter.MainFragmentPagerAdapter;
import com.syfm.groover.view.fragments.MusicListFragment;
import com.syfm.groover.view.fragments.MusicSortDialogFragment;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    private ContainerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.container);
        binding.setActivity(this);

        //SPにCookieがあったら
        SharedPreferenceHelper.create(getApplicationContext());
        Map<String, String> loginData = SharedPreferenceHelper.getLoginInfo();

        if (loginData.size() > 0 && !AppController.getInstance().checkLoginCookie()) {
            //入力させないでログイン
            LoginUseCase loginUseCase = new LoginUseCase();
            String serialNo = loginData.get(SPConst.LOGIN_NESICA_ID);
            String password = loginData.get(SPConst.LOGIN_PASSWORD);
            loginUseCase.checkLogin(serialNo, password);

        } else if (!AppController.getInstance().checkLoginCookie()) {
            //Go to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            int code = getResources().getInteger(R.integer.status_code_login);
            startActivityForResult(intent, code);
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(fragmentManager, this);
        binding.pager.setAdapter(mainFragmentPagerAdapter);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                binding.toolbar.getMenu().clear();
                switch (position) {
                    case 0:
                        binding.toolbar.inflateMenu(R.menu.menu_play_data);
                        break;
                    case 1:
                        binding.toolbar.inflateMenu(R.menu.menu_music_list);

                        binding.toolbar.setOnMenuItemClickListener(item -> {
                            switch (item.getItemId()) {
                                case R.id.menu_music_list_sort:
                                    MusicSortDialogFragment dialog = new MusicSortDialogFragment();
                                    dialog.show(getFragmentManager(), "dialog-music-sort");
                                    break;
                            }

                            return true;
                        });

                        // TODO: textColor等の変更
                        searchView = (SearchView) binding.toolbar.getMenu().findItem(R.id.menu_music_list_search).getActionView();

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
                                MusicListFragment fragment = (MusicListFragment) mainFragmentPagerAdapter.instantiateItem(binding.pager, 1);
                                fragment.searchMusic(newText);
                                return false;
                            }
                        });
                        break;
                    default:
                        binding.toolbar.inflateMenu(R.menu.menu_play_data);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        binding.tabStrip.setViewPager(binding.pager);

        binding.toolbar.setTitle(getResources().getString(R.string.app_name));
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
