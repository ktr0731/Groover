package com.syfm.groover.presenters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.syfm.groover.R;
import com.syfm.groover.business.usecases.LoginUseCase;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.SharedPreferenceHelper;
import com.syfm.groover.presenters.adapter.MainFragmentPagerAdapter;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_pager)
    ViewPager pager;
    @Bind(R.id.tab_strip)
    PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        ButterKnife.bind(this);

        //SPにCookieがあったら
        SharedPreferenceHelper.create(getApplicationContext());
        Map<String, String> loginData = SharedPreferenceHelper.getLoginInfo();

        if(loginData.size() != 0 && AppController.getInstance().checkLoginCookie()) {
            //入力させないでログイン
            LoginUseCase loginUseCase = new LoginUseCase();
            String serialNo = loginData.get(getResources().getString(R.string.pref_serial_no));
            String password = loginData.get(getResources().getString(R.string.pref_password));
            loginUseCase.checkLogin(serialNo, password);

        } else if (AppController.getInstance().checkLoginCookie()) {
            //Go to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            int code = getResources().getInteger(R.integer.status_code_login);
            startActivityForResult(intent, code);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(fragmentManager, this);
        pager.setAdapter(mainFragmentPagerAdapter);
        tabStrip.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    public void onEvent(PlayDataUseCase.SetPlayData event) {
        if (event.success) {
            Log.d("Unko", "SetPlayDataSuccess");
        } else {
            Log.d("Unko", "SetPlayDataError");
        }
    }*/
}
