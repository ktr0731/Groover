package com.syfm.groover.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.LoginUseCase;
import com.syfm.groover.view.fragments.ProgressDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/24.
 */
public class LoginActivity extends Activity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.editTextSerialNo)
    EditText serialNo;
    @Bind(R.id.editTextPassword)
    EditText password;

    final ProgressDialogFragment dialogFragment =
            ProgressDialogFragment.newInstance(R.string.dialog_title_login);

    @OnClick(R.id.loginButton)
    public void onClickLoginButton() {
        dialogFragment.show(getFragmentManager(), "dialog_fragment");

        //LoginUseCase loginUseCase = new LoginUseCase();
        //loginUseCase.checkLogin(serialNo.getText().toString(), password.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.activity_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
        if(event.success) {
            dialogFragment.dismiss();

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_login_successful), Toast.LENGTH_SHORT).show();

            //Get all data and set db.

            // TODO: 取得パーセント表示
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //PlayDataUseCase playDataUseCase = new PlayDataUseCase();
                    //playDataUseCase.setPlayData();
                    finish();
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
                    //musicDataUseCase.setMusicData();
                }
            }, 5000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //eventDataUseCase.setEventData();
                }
            }, 2500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //rankingDataUseCase.setRankingData();
                }
            }, 5500);

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
}
