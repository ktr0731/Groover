package com.syfm.groover.presenters.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.syfm.groover.R;
import com.syfm.groover.business.usecases.LoginUseCase;
import com.syfm.groover.business.usecases.PlayDataUseCase;
import com.syfm.groover.presenters.fragments.CommonDialogFragment;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by lycoris on 2015/09/24.
 */
public class LoginActivity extends Activity {

    private SmoothProgressBar smoothProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.activity_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        smoothProgressBar = (SmoothProgressBar)findViewById(R.id.progress_bar);

        final Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText serialNo = (EditText) findViewById(R.id.editTextSerialNo);
                final EditText password = (EditText) findViewById(R.id.editTextPassword);

                smoothProgressBar.setVisibility(View.VISIBLE);
                smoothProgressBar.progressiveStart();

                LoginUseCase loginUseCase = new LoginUseCase();
                loginUseCase.checkLogin(serialNo.getText().toString(), password.getText().toString());
            }
        });
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

    public void onEvent(LoginUseCase.LoginEvent event) {
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);

        if(event.success) {
            Toast.makeText(getApplicationContext(), "ログインに成功しました", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putInt(CommonDialogFragment.FIELD_TITLE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_LAYOUT, R.layout.dialog_progress);
            //bundle.putBoolean(CommonDialogFragment.FIELD_PROGRESS_BAR, true);

            final CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), "getDataDialog");

            //Get all data and set db.
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PlayDataUseCase playDataUseCase = new PlayDataUseCase();
                    playDataUseCase.setPlayData();
                }
            }, 1000);/*
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //eventDataUseCase.setEventData();
                }
            }, 2500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //musicDataUseCase.setMusicData();
                }
            }, 4000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //rankingDataUseCase.setRankingData();
                }
            }, 5500);*/

        } else {
            Toast.makeText(getApplicationContext(), "ログインに失敗しました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }

        return false;
    }

    public void onEvent(PlayDataUseCase.SetPlayData event) {
        if (event.success) {
            Log.d("Unko", "SetPlayDataSuccess");
            setResult(RESULT_OK);
            finish();
        } else {
            Log.d("Unko", "SetPlayDataError");
        }
    }
}
