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
import com.syfm.groover.business.usecases.PlayDataUseCase;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.presenters.fragments.CommonDialogFragment;
import com.syfm.groover.presenters.fragments.MainFragmentPagerAdapter;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        if (AppController.getInstance().checkLoginCookie()) {
            //Go to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            int code = getResources().getInteger(R.integer.status_code_login);
            startActivityForResult(intent, code);
        }

        ViewPager pager = (ViewPager) findViewById(R.id.main_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(fragmentManager, this);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getResources().getInteger(R.integer.status_code_login) == requestCode) {

            Bundle bundle = new Bundle();
            bundle.putInt(CommonDialogFragment.FIELD_TITLE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_LAYOUT, R.layout.dialog_progress);
            //bundle.putBoolean(CommonDialogFragment.FIELD_PROGRESS_BAR, true);

            final CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), "getDataDialog");

            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PlayDataUseCase useCase = new PlayDataUseCase();
                    useCase.getPlayData();
                    dialogFragment.dismiss();
                }
            }, 1000);
            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApiClient.getAverageScore();
                }
            }, 2000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApiClient.getMusicDetail();
                }
            }, 3000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApiClient.getShopData();
                }
            }, 4000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApiClient.getStageData();
                    dialogFragment.dismiss();
                }
            }, 5000);*/
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(PlayDataUseCase event) {

    }
}
